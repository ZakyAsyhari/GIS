package com.mapping.ahassmap.Services;

import com.mapping.ahassmap.Interfaces.ApiAhassInterface;
import com.mapping.ahassmap.Model.AhassApi;
import com.mapping.ahassmap.Rest.ApiConfig;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;

public class AhassServices {

    private static ApiAhassInterface apiAhassInterface;

    public static AhassApi getDataAhass(){
        AhassApi ahassApi = null;
        apiAhassInterface = ApiConfig.getClient().create(ApiAhassInterface.class);
        Call<AhassApi> call = apiAhassInterface.getAhass();
        try {
            ahassApi = call.execute().body();
        }
        catch (IOException e){

        }
        return ahassApi;
    }

    public static Call getAhass(Callback<AhassApi> callback){
        List<AhassApi> ahassApi = null;
        apiAhassInterface = ApiConfig.getClient().create(ApiAhassInterface.class);
        Call<AhassApi> call = apiAhassInterface.getAhass();
        call.enqueue(callback);
        return call;
    }

    public static okhttp3.Call reqAhassBody(okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://helloworldg3t.000webhostapp.com/Ahass2")
                .build();
        okhttp3.Call response = client.newCall(request);
        response.enqueue(callback);
        return response;
    }
}
