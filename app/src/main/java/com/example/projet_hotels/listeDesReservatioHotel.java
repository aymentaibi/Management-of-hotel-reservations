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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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

public class listeDesReservatioHotel extends AppCompatActivity {
    //todo: URL
    private static final String URL = Constant.addressIP +"listeReservationHotel.php";
    protected Spinner StatusReservation;
    private String StatusChamber;
    private RecyclerView recyclerView;
    private ArrayList<String> reservationID,checkin,checkOut,idChmaber,nomPrenom;
    private TextView listeVide;
    private String idHotel;
    private String id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_des_reservatio_hotel);
        listeVide = findViewById(R.id.textView104);
        idHotel = getIntent().getExtras().getString("idHotel");
        id_user = getIntent().getStringExtra("id_user");
        StatusReservation = findViewById(R.id.spinner2);


        ArrayList<String> StatusChamber = new ArrayList<>();
        StatusChamber.add("En Attente");
        StatusChamber.add("Refuser");
        StatusChamber.add("En Cours");
        StatusChamber.add("Terminer");
        ArrayAdapter<String> typesAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                StatusChamber
        );
        StatusReservation.setAdapter(typesAdapter);
        StatusReservation.setSelection(0);
        //updateRecycleView(StatusChamber.get(0));
        StatusReservation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                listeDesReservatioHotel.this.StatusChamber = StatusChamber.get(i);
                updateRecycleView(StatusChamber.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        BottomNavigationView btm_NavigatioView;
        btm_NavigatioView = findViewById(R.id.bottom_navigation_hotelListeDesChamber);
        btm_NavigatioView.setSelectedItemId(R.id.dashboard_hotel);
        btm_NavigatioView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.add_Chamber:
                        Intent intent = new Intent(listeDesReservatioHotel.this,ListeDesChambreHotel.class);
                        intent.putExtra("idHotel",idHotel);
                        intent.putExtra("id_user",id_user);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.status_hotel:
                        Intent intent1 = new Intent(listeDesReservatioHotel.this,ProfileHotelAccount.class);
                        intent1.putExtra("idHotel",idHotel);
                        intent1.putExtra("id_user",id_user);
                        startActivity(intent1);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }

    public void updateRecycleView(String statutR){
        reservationID = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerViewListeDesR);
        checkin= new ArrayList<>();
        checkOut= new ArrayList<>();
        idChmaber= new ArrayList<>();
        nomPrenom= new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray obj = new JSONArray(response);
                    if(obj.length()==0){
                        listeVide.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.INVISIBLE);
                    }
                    else {listeVide.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);}
                    for (int i = 0; i < obj.length(); i++) {
                        JSONObject obj_2 = obj.getJSONObject(i);
                        reservationID.add(obj_2.getString("id_reservation"));
                        checkin.add(obj_2.getString("d_check_in"));
                        checkOut.add(obj_2.getString("d_check_out"));
                        idChmaber.add(obj_2.getString("id_chambre"));
                        nomPrenom.add(obj_2.getString("nom") + " " + obj_2.getString("prenom"));
                    }
                        LinearLayoutManager llm = new LinearLayoutManager(listeDesReservatioHotel.this);
                        llm.setOrientation(LinearLayoutManager.VERTICAL);
                        MyadapterReservationHotel myadapter = new MyadapterReservationHotel(listeDesReservatioHotel.this,reservationID,checkin,checkOut,idChmaber,nomPrenom,statutR);
                        recyclerView.setLayoutManager(llm);
                        recyclerView.setAdapter(myadapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                    listeVide.setVisibility(View.VISIBLE);
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
                data.put("idHotel", idHotel);
                data.put("statut", statutR);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}