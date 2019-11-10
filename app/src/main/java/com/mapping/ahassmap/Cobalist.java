package com.mapping.ahassmap;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.mapping.ahassmap.Adapter.ListRuteAdapter;
import com.mapping.ahassmap.Adapter.ListRutecoba;
import com.mapping.ahassmap.Model.Ahass;
import com.mapping.ahassmap.Model.AhassApi;
import com.mapping.ahassmap.Services.AhassServices;

import org.w3c.dom.Text;

import java.lang.annotation.Native;
import java.math.BigDecimal;
import java.security.PublicKey;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.RandomAccess;

import retrofit2.Call;
import retrofit2.Callback;

public class Cobalist extends AppCompatActivity {
    ListView listView;
    ArrayList<Ahass> ahassArrayList;
    ArrayList<Ahass> ahassArrayList2;
    ArrayAdapter<Ahass> arrayAdapter;
    ArrayList<String> mylist = new ArrayList<String>();
    ArrayList<Double> subl = new ArrayList<Double>();
    ArrayList<Double> sublong = new ArrayList<Double>();
    EditText editText;
    SearchView searchView;
    TextView text;
    Button dkt;
    private ListRutecoba listRutecoba;
    public double lat,xlong;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobalist);
        text = (TextView) findViewById(R.id.coba1);
        text.setMovementMethod(new ScrollingMovementMethod());
        listView = (ListView) findViewById(R.id.listcoba);
        dkt = (Button) findViewById(R.id.dkt);
        Bundle ex = getIntent().getExtras();
        lat = ex.getDouble("latitude");
        xlong = ex.getDouble("longitude");

        AhassServices.getAhass(new Callback<AhassApi>() {
            @Override
            public void onResponse(Call<AhassApi> call, retrofit2.Response<AhassApi> response) {
                Log.d("latlong", "onCreate: "+lat+"-"+xlong);
                double eartrad = 2958.75;
                double myjr = 0;
                int jml = 0;
                double fil = 2.90;
                double jmlrute= 0;
                double mark = 10;
                List<Ahass> ahassList;
                ahassArrayList = new ArrayList<Ahass>();
                ahassList = response.body().getListDataAhass();
                //ahassArrayList.addAll(ahassList);
                for (int k=0;k < ahassList.size(); k+=2) {
                    for (int i = 0; i < ahassList.size(); i++) {
                        //ahassArrayList.add(ahassList.get(i));
                        ahassArrayList.add(ahassList.get(i));

                        double dlat = Math.toRadians(lat - ahassList.get(i).getLatitude());
                        double dlong = Math.toRadians(xlong - ahassList.get(i).getLongitude());
                        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) +
                                Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(ahassList.get(i).getLatitude()))
                                        * Math.sin(dlong / 2) * Math.sin(dlong / 2);
                        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                        double dist = eartrad * c;
                        int meter = 1609;
                        int Decimalplace = 2;
                        myjr = dist * meter;
                        double gg = myjr / 1000;
                        jmlrute = jmlrute + gg;
                        BigDecimal hc = new BigDecimal(jmlrute);
                        BigDecimal bd = new BigDecimal(gg);
                        bd = bd.setScale(Decimalplace, BigDecimal.ROUND_UP);
                        hc = hc.setScale(Decimalplace, BigDecimal.ROUND_UP);
                        jmlrute = hc.doubleValue();
                        gg = bd.doubleValue();
                        //BigDecimal hc = new BigDecimal(jmlrute);
                        //hc = hc.setScale(Decimalplace, BigDecimal.ROUND_UP);
                        //jmlrute = hc.doubleValue();
                        Log.d("jarak conver", "conversi jarak: " + ahassList.get(i).getNama());
                        if (jml <= 5) {
                            if (gg <= fil) {
                                mylist.add(ahassList.get(i).getNama());
                                subl.add(ahassList.get(i).getLatitude());
                                sublong.add(ahassList.get(i).getLongitude());
                                jml++;
                            }

                        }
                        if (gg < mark){
                            mark = gg;
                            int get = ahassList.get(i).getId()-1;
                            dkt.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Cobalist.this, NavigationAcitvity.class);
                                    intent.putExtra("Latitude", ahassList.get(get).getLatitude());
                                    intent.putExtra("Longitude", ahassList.get(get).getLongitude());
                                    startActivity(intent);
                                }
                            });

                        }

                    }
                }
                for (int l = 0; l < mylist.size(); l++) {
                    int k=1;
                    int x=0;
                    while(x<5) {
                        Collections.swap(mylist, x, k);
                        Collections.swap(subl, x, k);
                        Collections.swap(sublong, x, k);
                        int hitl =1;
                        int sumhit=0;
                        double jmlruter= 0;
                        while (sumhit < 5){
                            double dlat = Math.toRadians(subl.get(sumhit) - subl.get(hitl));
                            double dlong = Math.toRadians(sublong.get(sumhit) - sublong.get(hitl));
                            double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) +
                                    Math.cos(Math.toRadians(subl.get(sumhit))) * Math.cos(Math.toRadians(subl.get(hitl)))
                                            * Math.sin(dlong / 2) * Math.sin(dlong / 2);
                            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                            double dist = eartrad * c;
                            int meter = 1609;
                            int Decimalplace = 2;
                            myjr = dist * meter;
                            double gg = myjr / 1000;
                            jmlruter = jmlruter + gg;
                            BigDecimal hc = new BigDecimal(jmlruter);
                            BigDecimal bd = new BigDecimal(gg);
                            bd = bd.setScale(Decimalplace, BigDecimal.ROUND_UP);
                            hc = hc.setScale(Decimalplace, BigDecimal.ROUND_UP);
                            jmlruter = hc.doubleValue();
                            gg = bd.doubleValue();
                            sumhit++;
                            hitl++;
                        }
                        text.append("Pos-" + mylist + "-Pos " + jmlruter + "Km \n");
                        text.append("\n");
                        k++;
                        x++;
                    }
                }

                //ArrayAdapter adapter = new ArrayAdapter(Cobalist.this,R.layout.activity_cobalist,mylist);
                //listRutecoba = new ListRutecoba(Cobalist.this, R.layout.activity_cobalist, ahassArrayList);
                //listView.setAdapter(listRutecoba);
            }
            @Override
            public void onFailure(Call<AhassApi> call, Throwable t) {

            }
        });

    }



}
