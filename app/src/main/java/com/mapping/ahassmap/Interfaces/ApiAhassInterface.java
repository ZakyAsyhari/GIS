package com.mapping.ahassmap.Interfaces;

import com.mapping.ahassmap.Model.AhassApi;
import com.mapping.ahassmap.Model.BikeApi;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiAhassInterface {
    @GET("Ahass2")
    Call<AhassApi> getAhass();

    @GET("Ahass2/Api_bike")
    Call<BikeApi> getBike();

    //@GET("Ahass/api/ahassbyid")
    //Call<AhassApi> getAhassByid();
}
