package com.nandi.coffeeapp;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nandi.coffeeapp.adapter.CoffeeAdapter;
import com.nandi.coffeeapp.model.Coffee;
import com.nandi.coffeeapp.model.CoffeeList;
import com.nandi.coffeeapp.network.CoffeeListRequest;
import com.nandi.coffeeapp.service.CoffeeSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SpiceManager spiceManager = new SpiceManager(CoffeeSpiceService.class);
    CoffeeListRequest request;
    private ListView listView;
    private CoffeeAdapter adapter;
    private List<Coffee> coffees;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // custom action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.custom_title_view);
        // initialize the views
        bindViews();
    }

    private void bindViews() {

        listView = (ListView) findViewById(R.id.listView);
        progressDialog = new ProgressDialog(this, R.style.dialogTheme);

        coffees = new ArrayList<Coffee>();
        adapter = new CoffeeAdapter(this, R.layout.coffee_row_item, coffees);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(adapter);

        // create a api request
        request = new CoffeeListRequest();
        // display a loading bar
        showProgressDialog();
        // execute the request and tie up a request listener - CoffeeListRequestListener
        spiceManager.execute(request, "Coffee List", DurationInMillis.ONE_MINUTE, new CoffeeListRequestListener());
    }

    private void showProgressDialog() {
        if(!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    private class CoffeeListRequestListener implements RequestListener<CoffeeList> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            closeProgressDialog();
            Toast.makeText(MainActivity.this, "Please check your data connection!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestSuccess(CoffeeList coffees) {
            closeProgressDialog();
            // update the list view
            updateCoffeeList(coffees);
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

    private void updateCoffeeList(CoffeeList coffeeList) {
        coffees.clear();
        for(Coffee coffee: coffeeList) {
            coffees.add(coffee);
        }
        adapter.notifyDataSetChanged();
    }
}
