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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
//TODO: ModifierChambreETsUPPRIEMR
public class ListeDesChambreHotel extends AppCompatActivity {

    private static final String URL = Constant.addressIP +"ListeCambresHotel.php";
    private RecyclerView recyclerView;
    private TextView listeVide;
    private ArrayList<String> idChamber,typeChamber,prixChamber,statusChamber;
    private String idHotel;
    private Button ajouterCh;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_des_chambre_hotel);
        ajouterCh = findViewById(R.id.button20);
        recyclerView = findViewById(R.id.recycleViewListeDesChambres);
        listeVide = findViewById(R.id.textView103);
        idHotel = getIntent().getExtras().getString("idHotel");
        idUser = getIntent().getExtras().getString("id_user");
        idChamber = new ArrayList<>();
        typeChamber = new ArrayList<>();
        prixChamber = new ArrayList<>();
        statusChamber = new ArrayList<>();
        stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray obj = new JSONArray(response);
                for(int i= 0; i<obj.length();i++){
                    JSONObject obj_2 = obj.getJSONObject(i);
                    idChamber.add(obj_2.getString("id_chambre"));
                    typeChamber.add(obj_2.getString("type_chambre"));
                    prixChamber.add(obj_2.getString("prix"));
                    statusChamber.add(obj_2.getString("statut"));
                    LinearLayoutManager llm = new LinearLayoutManager(ListeDesChambreHotel.this);
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    MyadapterChamberHotel myadapter = new MyadapterChamberHotel(ListeDesChambreHotel.this,idUser,idHotel,idChamber,typeChamber,prixChamber,statusChamber);
                    recyclerView.setLayoutManager(llm);
                    recyclerView.setAdapter(myadapter);
                }
                } catch (JSONException e) {
                    e.printStackTrace();
                    listeVide.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListeDesChambreHotel.this, "Connexion Erreur Try Later", Toast.LENGTH_SHORT).show();
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
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        ajouterCh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListeDesChambreHotel.this,AjouterChambre.class);
                intent.putExtra("idHotel",idHotel);
                intent.putExtra("id_user",idUser);
                startActivity(intent);

                requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });

        BottomNavigationView btm_NavigatioView;
        btm_NavigatioView = findViewById(R.id.bottom_navigation_hotelListeDesChamber);
        btm_NavigatioView.setSelectedItemId(R.id.add_Chamber);
        btm_NavigatioView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard_hotel:
                        Intent intent = new Intent(ListeDesChambreHotel.this,listeDesReservatioHotel.class);
                        intent.putExtra("idHotel",idHotel);
                        intent.putExtra("id_user",idUser);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.status_hotel:
                        Intent intent1 = new Intent(ListeDesChambreHotel.this,ProfileHotelAccount.class);
                        intent1.putExtra("idHotel",idHotel);
                        intent1.putExtra("id_user",idUser);
                        startActivity(intent1);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }
}