package com.nandi.coffeeapp;

import android.app.ProgressDialog;
import android.content.Intent;
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
    private ProgressDialog progressDialog;
    private String coffeeName;
    private boolean isShareEnabled;
    private String imgUrl;
    private String coffeeDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        // initialize views
        bindViews();

        // fetch the values shared through intent
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            String coffeeId = bundle.getString("coffeeId");
            // create a api request to fetch coffee details using the coffee id
            detailsApiRequest = new CoffeeDetailsApiRequest(coffeeId);
            // show a loading dialog when request is made
            showProgressDialog();
            // execute api request and tie up a request listener - DetailsRequestListener
            spiceManager.execute(detailsApiRequest, coffeeId, DurationInMillis.ONE_MINUTE, new DetailsRequestListener());
        }
    }

    /*
    * display a loading bar
     */
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

        // customizing the actionbar
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
        // share feature
        final TextView share = (TextView) customView.findViewById(R.id.shareView);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // enable share feature only upon successful api call
                if(isShareEnabled) {
                    shareIntent();
                } else {
                    return;
                }
            }
        });
    }

    /*
    * share the coffee details
     */
    private void shareIntent() {
        String shareText = coffeeName + "\n" + coffeeDesc + "\n" + imgUrl;
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        shareIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
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
                coffeeName = coffeeDetails.name;
                coffeeDesc = coffeeDetails.desc;
                imgUrl = coffeeDetails.image_url;
                // set the fetched values to views
                coffeeTitleView.setText(coffeeName);
                coffeeDescView.setText(coffeeDesc);

                imgUrl = imgUrl.replace("http://", "https://");
                Picasso.with(DetailsActivity.this).load(Uri.parse(imgUrl)).into(coffeeImgView);
                isShareEnabled = true;
                // find the last update status from last_updated_at value
                String updateStatus = Constants.checkUpdateStatus(coffeeDetails.last_updated_at);
                if(updateStatus != null) {
                    coffeeStatusText.setText(updateStatus);
                }
            }
        }
    }

    /*
    * dismiss the loading bar
     */
    private void closeProgressDialog() {
        if(progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
