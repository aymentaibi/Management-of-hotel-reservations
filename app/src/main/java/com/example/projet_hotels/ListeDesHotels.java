package com.example.projet_hotels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListeDesHotels extends AppCompatActivity {
    protected ArrayList<String> nom_Hotels,adderss_Hotels,etoile_Hotels,nbr_chambre;
    protected String URL = Constant.addressIP +"listeDesHotels.php";
    protected String URL_2 = Constant.addressIP +"info_admin.php";
    protected TextView nbrClient,nbrHotel,nbrChambre;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_des_hotels);
        recyclerView = findViewById(R.id.ListDesHotelsEnAttente);
        nom_Hotels=new ArrayList<>();
        adderss_Hotels=new ArrayList<>();
        etoile_Hotels=new ArrayList<>();
        nbr_chambre=new ArrayList<>();
        nbrClient = findViewById(R.id.nbrh);
        nbrHotel = findViewById(R.id.nbrchmabre);
        nbrChambre = findViewById(R.id.nbrclient);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray obj = new JSONArray(response);

                    for(int i= 0; i<obj.length();i++){
                        JSONObject obj_2 = obj.getJSONObject(i);
                        nom_Hotels.add(obj_2.getString("nom").toString());
                        adderss_Hotels.add(obj_2.getString("addresse").toString());
                        etoile_Hotels.add(obj_2.getString("nb_etoile").toString());
                        nbr_chambre.add(obj_2.getString("nbrChambre").toString());
                    }
                    LinearLayoutManager llm = new LinearLayoutManager(ListeDesHotels.this);
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    Myadapter myadapter = new Myadapter(ListeDesHotels.this,nom_Hotels,adderss_Hotels,nbr_chambre,etoile_Hotels);
                    recyclerView.setLayoutManager(llm);
                    recyclerView.setAdapter(myadapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        StringRequest stringRequest_2 = new StringRequest(Request.Method.POST, URL_2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    nbrClient.setText(obj.getString("nbrUsers"));
                    nbrHotel.setText(obj.getString("nbrHotels"));
                    nbrChambre.setText(obj.getString("nbrChambres"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest_2);
        BottomNavigationView btm_NavigatioView;
        btm_NavigatioView = findViewById(R.id.brn_navig_profile);
        btm_NavigatioView.setSelectedItemId(R.id.dashboard_admin);
        btm_NavigatioView.setOnNavigationItemSelectedListener(navListener);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // By using switch we can easily get
            // the selected fragment
            // by using there id.
            switch (item.getItemId()) {
                case R.id.profile:
                    //startActivity(new Intent(getApplicationContext(),profile.class));
                    //finish();
                   // return true;
                case R.id.add_hotel:
                    startActivity(new Intent(getApplicationContext(),CreateHotel.class));
                    finish();
                    return true;
            }
            return false;
        }
    };
}