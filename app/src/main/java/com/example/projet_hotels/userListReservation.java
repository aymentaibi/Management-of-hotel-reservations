package com.example.projet_hotels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class userListReservation extends AppCompatActivity {
    private static final String URL = Constant.addressIP +"ListeDesReservation.php";
    private BottomNavigationView barre_bottom;
    private String user_id;
    private  ArrayList<String> nResarvation;
    private  ArrayList<String> hotelName;
    private  ArrayList<String> typeChamber;
    private  ArrayList<String> addressHotel;
    private  ArrayList<String> idChamber;
    private  ArrayList<String> checkIn;
    private  ArrayList<String> checkOut;
    private ArrayList<String> statut;
    private  ArrayList<String> longLatitude,lalitude;
    protected RecyclerView liste_reservation;
    private TextView listeVides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_reservation);
        listeVides = findViewById(R.id.textView105);
        user_id = getIntent().getStringExtra("id_user");
        nResarvation = new ArrayList<>();
        hotelName = new ArrayList<>();
        typeChamber = new ArrayList<>();
        addressHotel = new ArrayList<>();
        idChamber = new ArrayList<>();
        checkIn = new ArrayList<>();
        checkOut = new ArrayList<>();
        longLatitude = new ArrayList<>();
        lalitude = new ArrayList<>();
        statut = new ArrayList<>();
        liste_reservation = findViewById(R.id.listeReservation);
        BottomNavigationView btm_NavigatioView;
        btm_NavigatioView = findViewById(R.id.navigation_bar_ulr);
        btm_NavigatioView.setSelectedItemId(R.id.dashboard);
        btm_NavigatioView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.favoris:
                        Intent intent = new Intent(userListReservation.this,Favoris_client.class);
                        intent.putExtra("id_user",user_id);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.profile:
                        Intent intent1 = new Intent(userListReservation.this,profile.class);
                        intent1.putExtra("id_user",user_id);
                        startActivity(intent1);
                        finish();
                        return true;
                    case R.id.reserver:
                        Intent intent2 = new Intent(userListReservation.this,reservation.class);
                        intent2.putExtra("id_user", user_id);
                        startActivity(intent2);
                        finish();
                        return true;
                }
                return false;
            }
        });

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray obj = new JSONArray(response);
                    if(obj.length() != 0) listeVides.setVisibility(View.INVISIBLE);
                    else listeVides.setVisibility(View.VISIBLE);
                    for(int i= 0; i<obj.length();i++){
                        JSONObject obj_2 = obj.getJSONObject(i);
                        nResarvation.add(obj_2.getString("id_reservation"));
                        typeChamber.add(obj_2.getString("type_chambre"));
                        hotelName.add(obj_2.getString("nom_hotel"));
                        idChamber.add(obj_2.getString("id_chambre"));
                        addressHotel.add(obj_2.getString("addresse_hotel"));
                        checkIn.add(obj_2.getString("d_check_in"));
                        checkOut.add(obj_2.getString("d_check_out"));
                        longLatitude.add(obj_2.getString("cord_longitude"));
                        lalitude.add(obj_2.getString("cord_latitude"));
                        statut.add(obj_2.getString("statut_reservation"));
                    }
                    LinearLayoutManager llm = new LinearLayoutManager(userListReservation.this);
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    MyAdapterReservation myadapter = new MyAdapterReservation(userListReservation.this,nResarvation,hotelName,typeChamber,addressHotel,idChamber,checkIn,checkOut,longLatitude,lalitude,statut);
                    liste_reservation.setLayoutManager(llm);
                    liste_reservation.setAdapter(myadapter);

                } catch (JSONException e) {
                    listeVides.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(userListReservation.this,error.toString().trim(),Toast.LENGTH_LONG).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("id_user", user_id);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}