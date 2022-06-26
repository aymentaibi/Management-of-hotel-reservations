package com.example.projet_hotels;

import androidx.annotation.NonNull;
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

public class ValidationDesHotels extends AppCompatActivity {

    private ArrayList<String> nom_Hotels,adderss_Hotels,etoile_Hotels,IDhotel;
    RecyclerView recyclerView;
    protected String URL = Constant.addressIP +"listeDesHotelsAttente.php";
    private TextView listeVide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation_des_hotels);
        listeVide = findViewById(R.id.textView95);
        nom_Hotels=new ArrayList<>();
        adderss_Hotels=new ArrayList<>();
        etoile_Hotels=new ArrayList<>();
        IDhotel = new ArrayList<>();
        recyclerView  =findViewById(R.id.ListDesHotelsEnAttente);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray obj = new JSONArray(response);
                    for(int i= 0; i<obj.length();i++){
                        JSONObject obj_2 = obj.getJSONObject(i);
                        nom_Hotels.add(obj_2.getString("nom"));
                        adderss_Hotels.add(obj_2.getString("addresse"));
                        etoile_Hotels.add(obj_2.getString("nb_etoile"));
                        IDhotel.add(obj_2.getString("idHotel"));
                    }
                    if(nom_Hotels.size() == 0){
                        listeVide.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                    else {
                        listeVide.setVisibility(View.GONE);
                        LinearLayoutManager llm = new LinearLayoutManager(ValidationDesHotels.this);
                        llm.setOrientation(LinearLayoutManager.VERTICAL);
                        MyadapterAttente myadapter = new MyadapterAttente(ValidationDesHotels.this,getIntent().getStringExtra("id_user"), nom_Hotels, adderss_Hotels, etoile_Hotels, IDhotel);
                        recyclerView.setLayoutManager(llm);
                        recyclerView.setAdapter(myadapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    listeVide.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ValidationDesHotels.this,"Erreur de connexion réessayer dans quelque second",Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        BottomNavigationView btm_NavigatioView;
        btm_NavigatioView = findViewById(R.id.brn_navig_profile);
        btm_NavigatioView.setSelectedItemId(R.id.add_hotel);
        btm_NavigatioView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard_admin:
                        Intent intent = new Intent(ValidationDesHotels.this,ListeDesCommetaire.class);
                        intent.putExtra("id_user",getIntent().getStringExtra("id_user"));
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.profile_admin:
                        Intent intent1 = new Intent(ValidationDesHotels.this,profileADMIN.class);
                        intent1.putExtra("id_user",getIntent().getStringExtra("id_user"));
                        startActivity(intent1);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }
}