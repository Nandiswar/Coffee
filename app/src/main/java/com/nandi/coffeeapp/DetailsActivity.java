package com.nandi.coffeeapp;

import android.app.ProgressDialog;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nandi.coffeeapp.model.CoffeeDetails;
import com.nandi.coffeeapp.network.CoffeeDetailsApiRequest;
import com.nandi.coffeeapp.service.CoffeeSpiceService;
import com.nandi.coffeeapp.utility.Constants;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

/**
 * Created by nandi_000 on 10-11-2015.
 */
public class DetailsActivity extends AppCompatActivity {

    SpiceManager spiceManager = new SpiceManager(CoffeeSpiceService.class);
    CoffeeDetailsApiRequest detailsApiRequest;
    private TextView coffeeTitleView;
    private TextView coffeeDescView;
    private ImageView coffeeImgView;
    private TextView coffeeStatusText;
    private String url;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        bindViews();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            String coffeeId = bundle.getString("coffeeId");
            url = bundle.getString("coffeeImgUrl");
            url = url.replace("http://", "https://");
            detailsApiRequest = new CoffeeDetailsApiRequest(coffeeId);
            showProgressDialog();
            spiceManager.execute(detailsApiRequest, coffeeId, DurationInMillis.ONE_MINUTE, new DetailsRequestListener());
        }
    }

    private void showProgressDialog() {
        if(!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private void bindViews() {
        coffeeTitleView = (TextView) findViewById(R.id.titleText);
        coffeeDescView = (TextView) findViewById(R.id.descText);
        coffeeImgView = (ImageView) findViewById(R.id.imgView);
        coffeeStatusText = (TextView) findViewById(R.id.statusText);
        progressDialog = new ProgressDialog(this, R.style.dialogTheme);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.custom_details_title_view);
        View customView = actionBar.getCustomView();
        ImageView backClick = (ImageView) customView.findViewById(R.id.backImgView);
        backClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        spiceManager.shouldStop();
    }

    private class DetailsRequestListener implements RequestListener<CoffeeDetails> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            closeProgressDialog();
            Toast.makeText(DetailsActivity.this, "Please check your data connection!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestSuccess(CoffeeDetails coffeeDetails) {
            closeProgressDialog();
            if(coffeeDetails != null) {
                coffeeTitleView.setText(coffeeDetails.name);
                coffeeDescView.setText(coffeeDetails.desc);

                String updateStatus = Constants.checkUpdateStatus(coffeeDetails.last_updated_at);
                if(updateStatus != null) {
                    coffeeStatusText.setText(updateStatus);
                }
                Picasso.with(DetailsActivity.this).load(Uri.parse(url)).into(coffeeImgView);
            }
        }
    }

    private void closeProgressDialog() {
        if(progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
