package com.mapping.ahassmap.Model;

import com.google.gson.annotations.SerializedName;

public class Ahass {

    @SerializedName("Id")
    private int Id;
    @SerializedName("Nama")
    private String Nama;
    @SerializedName("Alamat")
    private String Alamat;
    @SerializedName("Latitude")
    private double Latitude;
    @SerializedName("Longitude")
    private double Longitude;
    @SerializedName("Deskripsi")
    private String Deskripsi;
    @SerializedName("Telepon")
    private String Telepon;
    @SerializedName("Gambar")
    private String Gambar;

    public Ahass(){}
    public Ahass(int Id, String Nama, String Alamat, double Latitute, double Longitude, String Deskripsi, String Telepon, String Gambar){
        this.Id = Id;
        this.Nama = Nama;
        this.Alamat =  Alamat;
        this.Latitude = Latitute;
        this.Longitude = Longitude;
        this.Deskripsi =  Deskripsi;
        this.Telepon = Telepon;
        this.Gambar = Gambar;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getDeskripsi() {
        return Deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        Deskripsi = deskripsi;
    }

    public String getTelepon() {
        return Telepon;
    }

    public void setTelepon(String telepon) {
        Telepon = telepon;
    }

    public String getGambar() {
        return Gambar;
    }

    public void setGambar(String gambar) {
        Gambar = gambar;
    }
}
