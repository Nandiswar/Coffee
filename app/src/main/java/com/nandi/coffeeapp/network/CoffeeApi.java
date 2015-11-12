package com.nandi.coffeeapp.network;

import com.nandi.coffeeapp.model.CoffeeDetails;
import com.nandi.coffeeapp.model.CoffeeList;
import com.nandi.coffeeapp.utility.Constants;

import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;

/**
 * Created by nandi_000 on 10-11-2015.
 * List of api's made by the app and show return type for each api call
 */
public interface CoffeeApi {

    @Headers("Authorization:"+ Constants.API_KEY)
    @GET("/api/coffee/")
    CoffeeList getCoffeeList();

    @Headers("Authorization:"+ Constants.API_KEY)
    @GET("/api/coffee/{id}")
    CoffeeDetails getCoffeeDetails(@Path("id") String id);
}
