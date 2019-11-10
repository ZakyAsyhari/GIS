package com.mapping.ahassmap.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mapping.ahassmap.Model.Bike;
import com.mapping.ahassmap.R;
import com.squareup.picasso.Picasso;

import java.net.HttpRetryException;
import java.util.ArrayList;
import java.util.List;

public class BikeListAdapter2 extends ArrayAdapter<Bike> {

    private Context context;
    int mResource;
    //private ArrayList<Ahass> ahassList;


    public BikeListAdapter2(@NonNull Context context, int resource, @NonNull ArrayList<Bike> objects) {
        super(context, resource, objects);
        this.context = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int Id =  getItem(position).getId();
        String Nama_spm = getItem(position).getNama_spm();
        String Jenis = getItem(position).getJenis();
        String Harga = getItem(position).getHarga();
        String Gambar = getItem(position).getGambar();

        Bike bike = new Bike(Id, Nama_spm, Jenis, Harga, Gambar);
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvNama = (TextView) convertView.findViewById(R.id.Nama_spm);
        TextView tvJenis = (TextView) convertView.findViewById(R.id.Jenis);
        TextView tvHarga = (TextView) convertView.findViewById(R.id.harga);
        ImageView  ivIcon = (ImageView) convertView.findViewById(R.id.icon);

        tvNama.setText(Nama_spm);
        tvJenis.setText(Jenis);
        tvHarga.setText(Harga);
        if (Gambar==null){
            Picasso.with(getContext())
                    .load(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(ivIcon);
        }else{
            Picasso.with(getContext())
                    .load(Gambar)
                    .error(R.mipmap.ic_launcher)
                    .into(ivIcon);
        }

        //ivIcon.setImageURI();

        return convertView;
        //return super.getView(position, convertView, parent);
    }
}
