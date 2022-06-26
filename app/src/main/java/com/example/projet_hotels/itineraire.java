package com.example.projet_hotels;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class itineraire extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback{
    private MapView mapView;
    private GoogleMap mMap;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private double latitudePosition;
    private double longTitude;
    private String hotelName;
    private FusedLocationProviderClient client;
    private Polyline currentpolyline;
    private String url;

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itineraires_current_location_to_hotel);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mapView = findViewById(R.id.mapView);
        client = LocationServices.getFusedLocationProviderClient(this);
        //check permission
        if(ActivityCompat.checkSelfPermission(itineraire.this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            getCuttentLocation();
        }
        else{
            ActivityCompat.requestPermissions(itineraire.this
                    ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                    ,44);

        }
        mapView.onCreate(mapViewBundle);
        latitudePosition = Double.parseDouble(getIntent().getStringExtra("latitude"));
        longTitude = Double.parseDouble(getIntent().getStringExtra("longtitude"));
        hotelName = getIntent().getStringExtra("hotalName");
    }

    @RequiresPermission(anyOf = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION})
    private void getCuttentLocation() {
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    mapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            mMap = googleMap;
                            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                            MarkerOptions home = new MarkerOptions().position(latLng).title("You");
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                            googleMap.addMarker(home);
                            LatLng hotelCordiation = new LatLng(latitudePosition,longTitude);
                            googleMap.addMarker(new MarkerOptions().position(hotelCordiation).title(hotelName));
                            url = getUrl(home.getPosition(),hotelCordiation, "driving");
                            new FetchURL(itineraire.this).execute(url,"driving");
                        }
                    });
                }
                else{
                    mapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            LatLng hotelCordiation = new LatLng(latitudePosition,longTitude);
                            googleMap.addMarker(new MarkerOptions().position(hotelCordiation).title(hotelName));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(hotelCordiation));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(hotelCordiation,10));
                        }
                    });

                }
            }
        });
        BottomNavigationView btm_NavigatioView;
        btm_NavigatioView = findViewById(R.id.bottomAppBar_backIteneraire);
        btm_NavigatioView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.backHome){
                    finish();
                    return true;
                }
                return false;
            }
        });
    }

    private String getUrl(LatLng debut, LatLng fin, String directionMode) {
        String str_origin = "origin=" + debut.latitude + "," + debut.longitude;
        String str_dest = "destination=" + fin.latitude + "," + fin.longitude;
        String mode = "mode=" + directionMode;
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.API_MAP);
        return url;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();

    }


    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }



    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @SuppressLint("MissingSuperCall")
    @RequiresPermission(anyOf = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION})
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 44 ){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCuttentLocation();
            }
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }

    @Override
    public void onTaskDone(Object... values) {
        if(currentpolyline != null){
            currentpolyline.remove();
        }
        currentpolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

}