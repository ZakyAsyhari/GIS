package com.mapping.ahassmap.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mapping.ahassmap.Model.Ahass;
import com.mapping.ahassmap.R;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ListRutecoba extends ArrayAdapter<Ahass>{
    private Context context;
    int mResource;
    ArrayList<String> mylist = new ArrayList<String>();
    double eartrad = 2958.75;
    double myjr = 0;
    double lat =-7.7899183;
    double xlong = 110.398498;
    int jml = 0;
    double jk = 0;
    double jmlrute= 0;

    public ListRutecoba(@NonNull Context context, int resource, @NonNull ArrayList<Ahass> objects) {
        super(context, resource, objects);
        this.context = context;
        this.mResource = resource;
        //Collections.shuffle(mylist, new Random());
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int k =0;
        int j =1;
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

        double dlat = Math.toRadians(lat - Latitude);
        double dlong = Math.toRadians(xlong -Longitude);
        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) +
                Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(Latitude))
                        * Math.sin(dlong / 2) * Math.sin(dlong / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = eartrad * c;
        int meter = 1609;
        int Decimalplace = 2;
        myjr = dist * meter;
        double gg = myjr / 1000;
        BigDecimal bd = new BigDecimal(gg);
        bd = bd.setScale(Decimalplace, BigDecimal.ROUND_UP);
        gg = bd.doubleValue();

        TextView tvName = (TextView) convertView.findViewById(R.id.coba1);


        if(mylist.size() <= 5) {
            mylist.add(Nama);
            jk += gg;
        }else{
            //Collections.shuffle(mylist, new Random());
            //String temp = mylist.get(0);
            //for (int j =0; j< mylist.size(); j++) {
            //tvName.append(""+mylist);
            if (j <= mylist.size()) {
                Collections.swap(mylist, k, j);
                tvName.append("" + mylist + "-" + gg);
            }else{
                tvName.append("" + mylist + "-" + "real");
            }
                //mylist. = temp;
            //}
        }
        k++;
        j++;
        return convertView;

        //return super.getView(position, convertView, parent);
    }
}
