package com.nandi.coffeeapp.network;

import com.nandi.coffeeapp.model.CoffeeDetails;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

/**
 * Created by nandi_000 on 09-11-2015.
 */
public class CoffeeDetailsApiRequest extends RetrofitSpiceRequest<CoffeeDetails,CoffeeApi> {

    private String id;

    public CoffeeDetailsApiRequest(String id){
        super(CoffeeDetails.class, CoffeeApi.class);
        this.id = id;
    }

    @Override
    public CoffeeDetails loadDataFromNetwork() throws Exception {
        return getService().getCoffeeDetails(id);
    }
}
