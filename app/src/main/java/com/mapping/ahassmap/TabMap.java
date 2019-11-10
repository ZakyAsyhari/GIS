package com.mapping.ahassmap;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineListener;
import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerOptions;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode;
import com.mapbox.mapboxsdk.style.light.Position;
import com.mapbox.services.android.navigation.ui.v5.location.LocationEngineConductor;
import com.mapbox.services.android.navigation.ui.v5.location.LocationEngineConductorListener;
import com.mapping.ahassmap.Model.Ahass;
import com.mapping.ahassmap.Model.AhassApi;
import com.mapping.ahassmap.Services.AhassServices;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabMap extends Fragment implements PermissionsListener ,LocationEngineListener{
    private MainActivity mainActivity;
    private MapView mapView;
    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private LocationLayerPlugin locationLayerPlugin;
    private Location originLocation;
    private Location lastLocation;
    public retrofit2.Response<AhassApi> dataResponse;
    public List<Ahass> ahassList;
    private FragmentManager fragmentManager;

    private Point originPosition;
    private Point destinationPosition;
    private DirectionsRoute currentRoute;
    private Double srcLatitude;
    private Double srcLongitude;
    private double mark = 10;
    Button dekat;
    Button btnrute;
    TextView textView;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Mapbox.getInstance(mainActivity, getString(R.string.mapbox_access_token));
        View view = inflater.inflate(R.layout.tabmap, container, false);
        dekat = (Button) view.findViewById(R.id.dekat);
        textView = (TextView) view.findViewById(R.id.textView2);
        btnrute = (Button) view.findViewById(R.id.btnrute);
        return view;


    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnrute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Cobalist.class);
                intent.putExtra("latitude",originLocation.getLatitude());
                intent.putExtra("longitude", originLocation.getLongitude());
                getActivity().startActivity(intent);
            }
        });

        // MapView large
        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        //mapView.getMapAsync(this);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                TabMap.this.mapboxMap = mapboxMap;
                TabMap.this.enableLocation();
                addMarker();
            }
        });
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_reload) {
//            addMarker();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    private void addMarker() {

        List<Marker> markerList = mapboxMap.getMarkers();
        for(int i = 0 ; i < markerList.size(); i++)
        {
            mapboxMap.removeMarker(markerList.get(i));
        }

        AhassServices.getAhass(new Callback<AhassApi>() {
            @Override
            public void onResponse(Call<AhassApi> call, retrofit2.Response<AhassApi> response) {

                int cek=0;

                //List<MarkerOptions> markerOptionsList = createMarkerOptionList();
                dataResponse = response;
                ahassList = response.body().getListDataAhass();
                Log.d("Retrofit Get", "Jumlah data Ahass: " +
                        String.valueOf(ahassList.size()));
                double eartrad = 2958.75;
                double myjr =0;
                for(int i = 0 ; i < ahassList.size(); i++) {

                    double dlat = Math.toRadians(originLocation.getLatitude() - ahassList.get(i).getLatitude());
                    double dlong = Math.toRadians(originLocation.getLongitude() - ahassList.get(i).getLongitude());
                    double a = Math.sin(dlat/2)*Math.sin(dlat/2)+
                            Math.cos(Math.toRadians(originLocation.getLatitude())) *Math.cos(Math.toRadians(ahassList.get(i).getLatitude()))
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

                    Log.d("jarak conver", "conversi jarak: "+ahassList.get(i).getNama()+"--" +gg+"KM");

                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(new LatLng(ahassList.get(i).getLatitude(), ahassList.get(i).getLongitude()))
                            .title(ahassList.get(i).getNama())
                            .setSnippet(ahassList.get(i).getAlamat()+"\n"+ ahassList.get(i).getDeskripsi()+"\n"+gg);

                    mapboxMap.addMarker(markerOptions);

                    textView.setText(""+originLocation.getLatitude());
                    if (gg < mark) {
                        mark = gg;
                            Log.d("btnDekat", "onResponse: " + mark+"-"+ahassList.get(i).getId());
                            Integer get = ahassList.get(i).getId()-1;
                            dekat.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), NavigationAcitvity.class);
                                    intent.putExtra("Latitude", ahassList.get(get).getLatitude());
                                    intent.putExtra("Longitude", ahassList.get(get).getLongitude());
                                    startActivity(intent);

                                }
                            });
                        }
                }


                mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {

                        TabMapBottomSheet tabMapBottomSheet = TabMapBottomSheet.newInstance(marker);
                        tabMapBottomSheet.show(getFragmentManager(), tabMapBottomSheet.getTag());
                        return false;
                    }
                });


            }

            @Override
            public void onFailure(Call<AhassApi> call, Throwable t) {

            }
        });

    }

    @SuppressWarnings({"MissingPermission"})
    private void initializeLocationEngine(){
        LocationEngineProvider locationEngineProvider = new LocationEngineProvider(getContext());
        locationEngine = locationEngineProvider.obtainBestLocationEngineAvailable();
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        locationEngine.activate();
        lastLocation = locationEngine.getLastLocation();
        if (lastLocation != null){
            originLocation  = lastLocation;
            setCameraPosition(lastLocation);
        } else{
            locationEngine.setPriority(LocationEnginePriority.BALANCED_POWER_ACCURACY);
            locationEngine.activate();
            lastLocation = locationEngine.getLastLocation();
            originLocation  = lastLocation;
            setCameraPosition(lastLocation);
            locationEngine.addLocationEngineListener(this);
        }
    }

    @SuppressWarnings({"MissingPermission"})
    private void initializeLocationLayer(){
        locationLayerPlugin = new LocationLayerPlugin(mapView, mapboxMap, locationEngine);
        locationLayerPlugin.getLocationEngine().getLastLocation();
        locationLayerPlugin.setLocationLayerEnabled(true);
        locationLayerPlugin.setRenderMode(RenderMode.COMPASS);
        locationLayerPlugin.setCameraMode(CameraMode.TRACKING);
    }

    private void setCameraPosition(Location location){
        mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13.0));
    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    public void onConnected() {
        locationEngine.requestLocationUpdates();
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location != null){
            srcLatitude = location.getLatitude();
            srcLongitude = location.getLongitude();
            originLocation = location;
            setCameraPosition(location);
        }
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {
        if(granted){
            enableLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void enableLocation(){
        if(permissionsManager.areLocationPermissionsGranted(getContext())){
            initializeLocationEngine();
            initializeLocationLayer();
        }else{
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }

    // Add the mapView's own lifecycle methods to the activity's lifecycle methods
    @Override
    @SuppressWarnings( {"MissingPermission"})
    public void onStart() {
        super.onStart();
        if (locationEngine != null) {
            locationEngine.requestLocationUpdates();
        }
        if (locationLayerPlugin!= null) {
            locationLayerPlugin.onStart();
        }
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (locationEngine != null) {
            locationEngine.removeLocationUpdates();
        }
        if (locationLayerPlugin != null) {
            locationLayerPlugin.onStop();
        }
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (locationEngine != null) {
            locationEngine.deactivate();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}