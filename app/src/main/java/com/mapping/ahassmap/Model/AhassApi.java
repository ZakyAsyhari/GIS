package com.mapping.ahassmap.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AhassApi {
    @SerializedName("status")
    String status;
    @SerializedName("content")
    List<Ahass> listDataAhass;
    @SerializedName("message")
    String message;
    @SerializedName("result")
    Ahass mAhass;


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

    public Ahass getAhass() {
        return mAhass;
    }
    public void setAhass(Ahass Ahass) {
        mAhass = Ahass;
    }

    public List<Ahass> getListDataAhass() {
        return listDataAhass;
    }
    public void setListDataAhass(List<Ahass> listDataAhass) {
        this.listDataAhass = listDataAhass;
    }
}
