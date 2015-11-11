package com.nandi.coffeeapp.service;

import com.octo.android.robospice.retrofit.RetrofitJackson2SpiceService;

/**
 * Created by nandi_000 on 10-11-2015.
 */
public class CoffeeSpiceService extends RetrofitJackson2SpiceService {

    private String BASE_URL = "https://coffeeapi.percolate.com/";

    @Override
    protected String getServerUrl() {
        return BASE_URL;
    }
}
