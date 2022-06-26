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

public class ListeDesCommetaire extends AppCompatActivity {
    private static final String URL = Constant.addressIP +"ListeCommentAtenn.php";
    private RecyclerView recyclerView;
    private ArrayList<String> idUser,commentaires,idComment, hotelID;
    private TextView listeVide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_des_commetaire);
        idUser = new ArrayList<>();
        listeVide = findViewById(R.id.textView25);
        commentaires = new ArrayList<>();
        idComment = new ArrayList<>();
        hotelID = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray obj = new JSONArray(response);
                    if(obj.length() == 0){ listeVide.setVisibility(View.VISIBLE);}
                    else{listeVide.setVisibility(View.INVISIBLE);}
                    for (int i = 0; i < obj.length(); i++) {
                        JSONObject obj_2 = obj.getJSONObject(i);
                        idComment.add(obj_2.getString("idComment"));
                        idUser.add(obj_2.getString("idUser"));
                        commentaires.add(obj_2.getString("contenu"));
                        hotelID.add(obj_2.getString("idHotel"));
                    }
                        recyclerView = findViewById(R.id.rcycleviewcommetaire);
                        recyclerView = findViewById(R.id.rcycleviewcommetaire);
                        LinearLayoutManager llm = new LinearLayoutManager(ListeDesCommetaire.this);
                        llm.setOrientation(LinearLayoutManager.VERTICAL);
                        MyadapterCommetValider myadapter = new MyadapterCommetValider(ListeDesCommetaire.this, idUser, commentaires, idComment, hotelID);
                        recyclerView.setLayoutManager(llm);
                        recyclerView.setAdapter(myadapter);


                } catch (JSONException e) {
                    listeVide.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListeDesCommetaire.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
            }
        }
    );
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

        BottomNavigationView btm_NavigatioView;
        btm_NavigatioView = findViewById(R.id.brn_navig_liste_descommentaires);
        btm_NavigatioView.setSelectedItemId(R.id.dashboard_admin);
        btm_NavigatioView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.add_hotel:
                        Intent intent = new Intent(ListeDesCommetaire.this,ValidationDesHotels.class);
                        intent.putExtra("id_user",getIntent().getStringExtra("id_user"));
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.profile_admin:
                        Intent intent1 = new Intent(ListeDesCommetaire.this,profileADMIN.class);
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