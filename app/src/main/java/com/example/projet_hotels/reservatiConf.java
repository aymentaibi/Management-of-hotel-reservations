package com.example.projet_hotels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class reservatiConf extends AppCompatActivity implements OnMapReadyCallback {

    private static final String URL = Constant.addressIP + "newReservation.php";
    private BottomNavigationView btnBottom;
    private TextView idChm,hotelName,prix,dateD,dateF;
    private String userId;
    private MapView mapView;
    private double longtitude,lalitude;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private final String url2 = Constant.addressIP +"listeDesChambresReservation.php";
    private final String url3 = Constant.addressIP +"listeDesChambresReservation2.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservati_conf);
        userId = getIntent().getStringExtra("idUser");
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mapView = findViewById(R.id.mapView2);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
        Button reserver = findViewById(R.id.arcReserver);
        Button annuler = findViewById(R.id.arcAnuler);
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        idChm = findViewById(R.id.arcID);
        hotelName = findViewById(R.id.arcHotel);
        prix = findViewById(R.id.arcPrix);
        dateD = findViewById(R.id.date_debut2);
        dateF = findViewById(R.id.date_fin2);
        dateD.setText(getIntent().getStringExtra("dateDebut"));
        dateF.setText(getIntent().getStringExtra("dateFin"));
        if(getIntent().getStringExtra("idchamber") == null){
            getData();
        }
        else{
            getData2();
        }
        reserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.getString("status").equals("succes")){
                                Toast.makeText(reservatiConf.this, "L'opération a été fait par succes", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(reservatiConf.this,userListReservation.class);
                                intent.putExtra("id_user",userId);
                                startActivity(intent);
                                finish();
                            }
                            else if (obj.getString("status").equals("failure")){
                                Toast.makeText(reservatiConf.this, "Erreur Inconue", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(reservatiConf.this,"Erreur Connect with Server",Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> data = new HashMap<>();
                        data.put("id_user",userId);
                        data.put("idHotel",getIntent().getStringExtra("idHotel"));
                        data.put("id_chamber",idChm.getText().toString());
                        data.put("dateDebut",dateD.getText().toString());
                        data.put("dateFin",dateF.getText().toString());
                        return data;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });
    }

    private void getData2() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url3, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray obj = new JSONArray(response);
                    for(int i= 0; i<obj.length();i++){
                        JSONObject obj_2 = obj.getJSONObject(i);
                        idChm.setText(obj_2.getString("idChambre"));
                        hotelName.setText(obj_2.getString("nomHotel"));
                        LocalDate dateBefore = LocalDate.parse(getIntent().getStringExtra("dateDebut"));
                        LocalDate dateAfter = LocalDate.parse(getIntent().getStringExtra("dateFin"));
                        long daysDiff = ChronoUnit.DAYS.between(dateBefore, dateAfter);
                        float prixTotal = Float.parseFloat(obj_2.getString("prix")) * daysDiff;
                        prix.setText(Float.toString(prixTotal));

                        lalitude = Double.parseDouble(obj_2.getString("lalitudeHotel"));
                        longtitude = Double.parseDouble(obj_2.getString("longtitudeHotel"));
                        mapView.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(@NonNull GoogleMap googleMap) {
                                LatLng hotelCordiation = new LatLng(lalitude, longtitude);
                                googleMap.addMarker(new MarkerOptions().position(hotelCordiation).title(hotelName.getText().toString()));
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(hotelCordiation,15));
                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(hotelCordiation));
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("idchamber",getIntent().getStringExtra("idchamber"));
                data.put("idHotel",getIntent().getStringExtra("idHotel"));
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void getData(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray obj = new JSONArray(response);
                    for(int i= 0; i<obj.length();i++){
                        JSONObject obj_2 = obj.getJSONObject(i);
                        idChm.setText(obj_2.getString("idChambre"));
                        hotelName.setText(obj_2.getString("nomHotel"));
                        LocalDate dateBefore = LocalDate.parse(getIntent().getStringExtra("dateDebut"));
                        LocalDate dateAfter = LocalDate.parse(getIntent().getStringExtra("dateFin"));
                        long daysDiff = ChronoUnit.DAYS.between(dateBefore, dateAfter);
                        float prixTotal = Float.parseFloat(obj_2.getString("prix")) * daysDiff;
                        prix.setText(Float.toString(prixTotal));

                        lalitude = Double.parseDouble(obj_2.getString("lalitudeHotel"));
                        longtitude = Double.parseDouble(obj_2.getString("longtitudeHotel"));
                        mapView.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(@NonNull GoogleMap googleMap) {
                                LatLng hotelCordiation = new LatLng(lalitude, longtitude);
                                googleMap.addMarker(new MarkerOptions().position(hotelCordiation).title(hotelName.getText().toString()));
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(hotelCordiation,15));
                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(hotelCordiation));
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("dateDebut",getIntent().getStringExtra("dateDebut"));
                data.put("dateFin",getIntent().getStringExtra("dateFin"));
                if(getIntent().getStringExtra("type") != null) data.put("typeChamber",getIntent().getStringExtra("type"));
                if(getIntent().getStringExtra("idchamber") != null) data.put("idchamber",getIntent().getStringExtra("idchamber"));
                data.put("idHotel",getIntent().getStringExtra("idHotel"));
                if(Float.parseFloat(getIntent().getStringExtra("prixMax")) > 0 ) data.put("prixMax",getIntent().getStringExtra("prixMax"));
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
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


}
