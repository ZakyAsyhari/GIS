package com.mapping.ahassmap.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mapping.ahassmap.Model.Ahass;
import com.mapping.ahassmap.R;

import java.util.ArrayList;
import java.util.List;

public class ListRuteAdapter extends ArrayAdapter<Ahass>{
    private Context context;
    int mResource;

    public ListRuteAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Ahass> objects) {
        super(context, resource, objects);
        this.context = context;
        this.mResource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int Id =  getItem(position).getId();
        String Nama = getItem(position).getNama();
        String Alamat = getItem(position).getAlamat();
        Double Latitude = getItem(position).getLatitude();
        Double Longitude = getItem(position).getLongitude();
        String Deskripsi = getItem(position).getDeskripsi();
        String Telepon = getItem(position).getTelepon();
        String Gambar = getItem(position).getGambar();

        Ahass ahass = new Ahass(Id, Nama, Alamat, Latitude, Longitude, Deskripsi, Telepon, Gambar);
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.namaD);

        tvName.setText(Nama);


        return convertView;
        //return super.getView(position, convertView, parent);
    }
}
