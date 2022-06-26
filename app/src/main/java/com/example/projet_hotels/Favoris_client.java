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

public class Favoris_client extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<String> nom_Hotels,idHotel,adderss_Hotels,nbr_chambre,etoile_Hotels;
    private String idUser;
    private TextView listeVide;
    private final String url=Constant.addressIP +"ListePavorisClient.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris_client);
        listeVide = findViewById(R.id.textView93);
        idUser =  getIntent().getStringExtra("id_user");
        nom_Hotels = new ArrayList<>();
        idHotel = new ArrayList<>();
        adderss_Hotels = new ArrayList<>();
        nbr_chambre = new ArrayList<>();
        etoile_Hotels = new ArrayList<>();
        recyclerView = findViewById(R.id.recyleViewFavoris);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                nom_Hotels = new ArrayList<>();
                idHotel = new ArrayList<>();
                adderss_Hotels = new ArrayList<>();
                nbr_chambre = new ArrayList<>();
                etoile_Hotels = new ArrayList<>();
                try {
                    JSONArray obj = new JSONArray(response);
                    if(obj.length() != 0 ){
                        listeVide.setVisibility(View.INVISIBLE);}
                    else{
                        listeVide.setVisibility(View.VISIBLE);
                    }
                    for(int i= 0; i<obj.length();i++) {
                        JSONObject obj_2 = obj.getJSONObject(i);
                        idHotel.add(obj_2.getString("id_hotel"));
                        nom_Hotels.add(obj_2.getString("nom_hotel"));
                        adderss_Hotels.add(obj_2.getString("addresse"));
                        nbr_chambre.add(obj_2.getString("nbrChambre"));
                        etoile_Hotels.add(obj_2.getString("nb_etoile"));
                    }
                        LinearLayoutManager llm = new LinearLayoutManager(Favoris_client.this);
                        llm.setOrientation(LinearLayoutManager.VERTICAL);
                        MyadapterFavoris myadapter = new MyadapterFavoris(Favoris_client.this,idUser,idHotel,nom_Hotels,adderss_Hotels,nbr_chambre,etoile_Hotels);
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
                data.put("id_user",idUser);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        //Navigation View Bottom
        BottomNavigationView btm_NavigatioView;
        btm_NavigatioView = findViewById(R.id.navigation_bar_res);
        btm_NavigatioView.setSelectedItemId(R.id.favoris);
        btm_NavigatioView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
                        Intent intent = new Intent(Favoris_client.this,userListReservation.class);
                        intent.putExtra("id_user",idUser);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.profile:
                        Intent intent1 = new Intent(Favoris_client.this,profile.class);
                        intent1.putExtra("id_user",idUser);
                        startActivity(intent1);
                        finish();
                        return true;
                    case R.id.reserver:
                        Intent intent2 = new Intent(Favoris_client.this,reservation.class);
                        intent2.putExtra("id_user", idUser);
                        startActivity(intent2);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }
}