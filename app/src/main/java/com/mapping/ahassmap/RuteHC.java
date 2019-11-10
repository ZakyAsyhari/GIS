package com.mapping.ahassmap;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.mapping.ahassmap.Adapter.ListRuteAdapter;
import com.mapping.ahassmap.Adapter.ListRutecoba;
import com.mapping.ahassmap.Model.Ahass;
import com.mapping.ahassmap.Model.AhassApi;
import com.mapping.ahassmap.Services.AhassServices;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.security.auth.Subject;

import retrofit2.Call;
import retrofit2.Callback;

public class RuteHC extends AppCompatActivity {
    ListView listView;
    ArrayList<Ahass> ahassArrayList;
    ArrayList<Ahass> ahassArrayList2;
    ArrayAdapter<Ahass> arrayAdapter;
    EditText editText;
    SearchView searchView;
    TextView km;
    private ListRuteAdapter listRuteAdapter;
    public double lat,xlong;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rute_hc);

        listView = (ListView) findViewById(R.id.listHc);
        km = (TextView) findViewById(R.id.Km);
        Bundle ex = getIntent().getExtras();
        lat = ex.getDouble("latitude");
        xlong = ex.getDouble("longitude");
        ArrayList<String> mylist = new ArrayList<String>();

        AhassServices.getAhass(new Callback<AhassApi>() {
            @Override
            public void onResponse(Call<AhassApi> call, retrofit2.Response<AhassApi> response) {
                Log.d("latlong", "onCreate: "+lat+"-"+xlong);
                double eartrad = 2958.75;
                double myjr = 0;
                List<Ahass> ahassList;
                ahassArrayList = new ArrayList<Ahass>();
                ahassList = response.body().getListDataAhass();
                //ahassArrayList.addAll(ahassList);
                for(int i = 0 ; i < ahassList.size(); i++)
                {
                    //ahassArrayList.add(ahassList.get(i));
                    ahassArrayList.add(ahassList.get(i));
                    double dlat = Math.toRadians(lat - ahassList.get(i).getLatitude());
                    double dlong = Math.toRadians(xlong - ahassList.get(i).getLongitude());
                    double a = Math.sin(dlat/2)*Math.sin(dlat/2)+
                            Math.cos(Math.toRadians(lat)) *Math.cos(Math.toRadians(ahassList.get(i).getLatitude()))
                                    * Math.sin(dlong/2) * Math.sin(dlong/2);
                    double c = 2* Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
                    double dist = eartrad *c;
                    int meter = 1609;
                    int Decimalplace = 2;
                    myjr = dist *meter;
                    double gg =  myjr/1000;
                    BigDecimal bd = new BigDecimal(gg);
                    bd = bd.setScale(Decimalplace,BigDecimal.ROUND_UP);
                    gg = bd.doubleValue();
                    km.setText(""+gg+"Km");

                    Log.d("jarak conver", "conversi jarak: "+ahassList.get(i).getNama()+  "-" +gg);

                    mylist.add(ahassList.get(i).getNama());
                }
                for (int l=0;l<3;l++) {
                    Collections.shuffle(mylist, new Random());
                    Log.d("coba random", "onResponse: " + mylist);
                }

                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(Cobalist.this,R.layout.activity_cobalist,mylist);
                listRuteAdapter = new ListRuteAdapter(RuteHC.this, R.layout.activity_rute_hc, ahassArrayList);
                listView.setAdapter(listRuteAdapter);


            }
            @Override
            public void onFailure(Call<AhassApi> call, Throwable t) {

            }
        });

    }



}

