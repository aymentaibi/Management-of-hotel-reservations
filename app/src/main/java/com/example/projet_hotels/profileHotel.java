package com.example.projet_hotels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class profileHotel extends AppCompatActivity implements OnMapReadyCallback {
    private static final String URL = Constant.addressIP +"infoHotel.php";
    private static final String URL2 = Constant.addressIP +"validerRefuserHotel.php";
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private String idHotel;
    private TextView nomHotel,addressHotel,telephoneHotel;
    private Button accpeter,refuser;
    private RatingBar etoileHotel;
    private CheckBox piscine,parking,salleSport,wifi;
    private String longitude,lantitude;
    private LatLng locationHotel;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_hotel);
        idHotel = getIntent().getStringExtra("hotelId");

        nomHotel = findViewById(R.id.textView40);
        addressHotel = findViewById(R.id.textView39);
        telephoneHotel = findViewById(R.id.textView42);
        etoileHotel = findViewById(R.id.ratingBarProfileHotel);
        piscine = findViewById(R.id.checkBox);
        parking =findViewById(R.id.checkBox2);
        salleSport = findViewById(R.id.checkBox3);
        wifi = findViewById(R.id.checkBox4);
        accpeter = findViewById(R.id.button12);
        refuser = findViewById(R.id.button13);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if(obj.getString("status").equals("success")){
                        nomHotel.setText(obj.getString("nom_hotel"));
                        addressHotel.setText(obj.getString("addresse_hotel"));
                        telephoneHotel.setText(obj.getString("phone"));
                        etoileHotel.setRating(Float.parseFloat(obj.getString("nb_etoile")));
                        if(obj.getString("piscine").equals("1")) piscine.setChecked(true);
                        if(obj.getString("sallesport").equals("1")) salleSport.setChecked(true);
                        if(obj.getString("parking").equals("1")) parking.setChecked(true);
                        if(obj.getString("wifi").equals("1")) wifi.setChecked(true);
                        longitude = obj.getString("cord_longitude");
                        lantitude = obj.getString("cord_latitude");
                        locationHotel = new LatLng(Double.parseDouble(lantitude),Double.parseDouble(longitude));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(profileHotel.this,"Erreur de connexion",Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("idHotel", idHotel);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

        accpeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(profileHotel.this)
                        .setTitle("Confirmer l'opération")
                        .setMessage("Vous-êtes sûr d'accepter ce Hôtel?")
                        .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        accpterRefuserHotel("hotel");
                                    }
                                }
                        )
                        .setNegativeButton("Non",null)
                        .show();
            }

        });
        refuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(profileHotel.this)
                        .setTitle("Confirmer l'opération")
                        .setMessage("Vous-êtes sûr de refuser ce Hôtel?")
                        .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        accpterRefuserHotel("refuser");
                                    }
                                }
                        )
                        .setNegativeButton("Non",null)
                        .show();
            }
        });
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mapView = findViewById(R.id.mapView4);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
        BottomNavigationView btm_NavigatioView;
        btm_NavigatioView = findViewById(R.id.bottomAppBar_profileHotelCheck);
        btm_NavigatioView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
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
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions().position(locationHotel).title(nomHotel.getText().toString()));
        map.moveCamera(CameraUpdateFactory.newLatLng(locationHotel));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(locationHotel,10));
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
    private void accpterRefuserHotel(String status){
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if(obj.getString("status").equals("succes")){
                        Intent intent = new Intent(profileHotel.this,ValidationDesHotels.class);
                        intent.putExtra("id_user",getIntent().getStringExtra("id_user"));
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(profileHotel.this,"Error de connexion",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(profileHotel.this,"Error de connexion",Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("idHotel", idHotel);
                data.put("status",status);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest1);
    }

}