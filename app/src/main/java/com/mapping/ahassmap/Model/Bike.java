package com.mapping.ahassmap.Model;

import com.google.gson.annotations.SerializedName;

public class Bike {

    @SerializedName("Id")
    private int Id;
    @SerializedName("Nama_spm")
    private String Nama_spm;
    @SerializedName("Jenis")
    private String Jenis;
    @SerializedName("Harga")
    private String Harga;
    @SerializedName("Gambar")
    private String Gambar;

    public Bike(){}
    public Bike(int Id, String Nama, String Jenis, String Harga, String Gambar){
        this.Id = Id;
        this.Nama_spm = Nama_spm;
        this.Jenis =  Jenis;
        this.Harga = Harga;
        this.Gambar = Gambar;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNama_spm() {
        return Nama_spm;
    }

    public void setNama_spm(String nama_spm) {
        Nama_spm = nama_spm;
    }

    public String getJenis() {
        return Jenis;
    }

    public void setJenis(String jenis) {
        Jenis = jenis;
    }

    public String getHarga() {
        return Harga;
    }

    public void setHarga(String harga) {
        Harga =  harga;
    }

    public String getGambar() {
        return Gambar;
    }

    public void setGambar(String gambar) {
        Gambar = gambar;
    }
}
