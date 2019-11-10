package com.mapping.ahassmap.Services;

import com.mapping.ahassmap.Interfaces.ApiAhassInterface;
import com.mapping.ahassmap.Model.BikeApi;
import com.mapping.ahassmap.Rest.ApiConfig;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;

public class BikeService {

    private static ApiAhassInterface apiAhassInterface;

    public static BikeApi getDataBike(){
        BikeApi bikeApi = null;
        apiAhassInterface = ApiConfig.getClient().create(ApiAhassInterface.class);
        Call<BikeApi> call = apiAhassInterface.getBike();
        try {
            bikeApi = call.execute().body();
        }
        catch (IOException e){

        }
        return bikeApi;
    }

    public static Call getBike(Callback<BikeApi> callback){
        List<BikeApi> bikeApi = null;
        apiAhassInterface = ApiConfig.getClient().create(ApiAhassInterface.class);
        Call<BikeApi> call = apiAhassInterface.getBike();
        call.enqueue(callback);
        return call;
    }

    public static okhttp3.Call reqBikeBody(okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://helloworldg3t.000webhostapp.com/Ahass2/Api_bike")
                .build();
        okhttp3.Call response = client.newCall(request);
        response.enqueue(callback);
        return response;
    }
}
