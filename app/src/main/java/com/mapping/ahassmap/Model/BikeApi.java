package com.mapping.ahassmap.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BikeApi {
    @SerializedName("status")
    String status;
    @SerializedName("content")
    List<Bike> listDataBike;
    @SerializedName("message")
    String message;
    @SerializedName("result")
    Bike mBike;


    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Bike getBike() {
        return mBike;
    }
    public void setBike(Bike Bike) {
        mBike = Bike;
    }

    public List<Bike> getListDataBike() {
        return listDataBike;
    }
    public void setListDataBike(List<Bike> listDataBike) {
        this.listDataBike = listDataBike;
    }
}
