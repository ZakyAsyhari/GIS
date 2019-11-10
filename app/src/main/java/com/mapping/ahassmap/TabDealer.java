package com.mapping.ahassmap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.Date;
import java.util.HashMap;

import com.mapping.ahassmap.Adapter.AhassListAdapter;
import com.mapping.ahassmap.Model.Ahass;
import com.mapping.ahassmap.Model.AhassApi;
import com.mapping.ahassmap.Services.AhassServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.security.auth.Subject;

import retrofit2.Call;
import retrofit2.Callback;

public class TabDealer extends Fragment {
    ListView listView;
    ArrayList<Ahass> ahassArrayList;
    ArrayList<Ahass> ahassArrayList2;
    ArrayAdapter<Ahass> arrayAdapter;
    EditText editText;
    SearchView searchView;
    private AhassListAdapter ahassListAdapter;


    public TabDealer(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listviewadapter, container, false);
        listView = (ListView) view.findViewById(R.id.listDealer);
        listView.setTextFilterEnabled(true);
        //searchView = (SearchView) view.findViewById(R.id.search_view);
        editText = (EditText) view.findViewById(R.id.cari);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAhassList();
    }

    private void getAhassList(){

        AhassServices.getAhass(new Callback<AhassApi>() {
            @Override
            public void onResponse(Call<AhassApi> call, retrofit2.Response<AhassApi> response) {

                List<Ahass> ahassList;
                ahassArrayList = new ArrayList<Ahass>();
                ahassList = response.body().getListDataAhass();
                for(int i = 0 ; i < ahassList.size(); i++)
                {
                    ahassArrayList.add(ahassList.get(i));
                    Log.i("here", ahassList.get(i).getNama());
                }

                ahassListAdapter = new AhassListAdapter(getContext(), R.layout.tabdealer, ahassArrayList);
                listView.setAdapter(ahassListAdapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        //Updating Array Adapter ListView after typing inside EditText.

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        String searchString = editText.getText().toString().toLowerCase();
                        List<Ahass> ahassList;
                        ahassArrayList = new ArrayList<Ahass>();
                        ahassList = response.body().getListDataAhass();
                        for(int x = 0 ; x < ahassList.size(); x++)
                        {
                            String subject = ahassList.get(x).getNama();
                            if (subject.toLowerCase().contains(searchString)) {
                                //arrayTemplist.add(arrayTemplist.get(x));
                                ahassArrayList.add(ahassList.get(x));
                                Log.i("here", ahassList.get(x).getNama());
                            }
                        }
                        ahassListAdapter = new AhassListAdapter(getContext(), R.layout.tabdealer, ahassArrayList);
                        listView.setAdapter(ahassListAdapter);
                        ahassListAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void afterTextChanged (Editable editable){

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent get = new Intent(getActivity(), NavigationAcitvity.class);
                        get.putExtra("Latitude", ahassArrayList.get(position).getLatitude());
                        get.putExtra("Longitude", ahassArrayList.get(position).getLongitude());
                        startActivity(get);
                    }
                });
            }
            @Override
            public void onFailure(Call<AhassApi> call, Throwable t) {

            }
        });
    }
}

