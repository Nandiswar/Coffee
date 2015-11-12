package com.nandi.coffeeapp.network;

import com.nandi.coffeeapp.model.CoffeeList;
import com.nandi.coffeeapp.network.CoffeeApi;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

/**
 * Created by nandi_000 on 10-11-2015.
 * Api request to fetch list of coffees
 */
public class CoffeeListRequest extends RetrofitSpiceRequest<CoffeeList,CoffeeApi> {

    public CoffeeListRequest() {
        super(CoffeeList.class, CoffeeApi.class);
    }

    @Override
    public CoffeeList loadDataFromNetwork() throws Exception {
        return getService().getCoffeeList();
    }
}
