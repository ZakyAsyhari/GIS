package com.mapping.ahassmap;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.mapping.ahassmap.Adapter.AhassListAdapter;
import com.mapping.ahassmap.Adapter.BikeListAdapter2;
import com.mapping.ahassmap.Model.Ahass;
import com.mapping.ahassmap.Model.Bike;
import com.mapping.ahassmap.Model.BikeApi;
import com.mapping.ahassmap.Services.BikeService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class TabBike extends Fragment{
    ListView listView;
    ArrayList<Bike> ahassArrayList;
    ArrayAdapter<Bike> arrayAdapter;
    EditText editText;
    private BikeListAdapter2 bikeListAdapter2;


    public TabBike(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listbikeadapter, container, false);
        listView = (ListView) view.findViewById(R.id.listbike);
        editText = (EditText) view.findViewById(R.id.carimotor);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getBikeList();
    }

    private void getBikeList(){

        BikeService.getBike(new Callback<BikeApi>() {
            @Override
            public void onResponse(Call<BikeApi> call, retrofit2.Response<BikeApi> response) {

                List<Bike> bikeList;
                ahassArrayList = new ArrayList<Bike>();
                bikeList = response.body().getListDataBike();
                for(int i = 0 ; i < bikeList.size(); i++)
                {
                    ahassArrayList.add(bikeList.get(i));
                    Log.i("here", bikeList.get(i).getNama_spm());
                }
                bikeListAdapter2 = new BikeListAdapter2(getContext(), R.layout.tabbike, ahassArrayList);
                listView.setAdapter(bikeListAdapter2);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        //Updating Array Adapter ListView after typing inside EditText.

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        String searchString = editText.getText().toString().toLowerCase();
                        List<Bike> bikeList;
                        ahassArrayList = new ArrayList<Bike>();
                        bikeList = response.body().getListDataBike();
                        for(int x = 0 ; x < bikeList.size(); x++)
                        {
                            String subject = bikeList.get(x).getNama_spm();
                            String sjenis = bikeList.get(x).getJenis();
                            if (subject.toLowerCase().contains(searchString) || sjenis.toLowerCase().contains(searchString)) {
                                //arrayTemplist.add(arrayTemplist.get(x));
                                ahassArrayList.add(bikeList.get(x));
                                Log.i("here", bikeList.get(x).getNama_spm());
                            }
                        }
                        bikeListAdapter2 = new BikeListAdapter2(getContext(), R.layout.tabbike, ahassArrayList);
                        listView.setAdapter(bikeListAdapter2);
                        bikeListAdapter2.notifyDataSetChanged();
                    }
                    @Override
                    public void afterTextChanged (Editable editable){

                    }
                });
            }

            @Override
            public void onFailure(Call<BikeApi> call, Throwable t) {

            }
        });
    }
}
