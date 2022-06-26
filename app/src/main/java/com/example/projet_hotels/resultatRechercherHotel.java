package com.example.projet_hotels;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class resultatRechercherHotel extends AppCompatActivity {
    private static final String URL = Constant.addressIP +"listeDesHoterReservation.php";
    private RecyclerView recyclerView;
    private ArrayList<String> nom_Hotels;
    private ArrayList<String> adderss_Hotels;
    private ArrayList<String> etoile_Hotels;
    private ArrayList<String> nbr_chambre;
    private String ville;
    private TextView listeVide;
    private ArrayList<String> longitude;
    private ArrayList<String> latitude;
    private boolean[] checkSpec;
    private ArrayList<String> id_Hotel;
    private String place;
    private String dateDebut;
    private String dateFin;
    private String user_id;
    private String prixMax;
    private boolean[] checkSpecification;
    private String typeChamber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat_rechercher_hotel);
        ville = getIntent().getStringExtra("place");
        listeVide = findViewById(R.id.textView110);
        place = getIntent().getStringExtra("place");
        dateDebut = getIntent().getStringExtra("dateDebut");
        dateFin = getIntent().getStringExtra("dateFin");
        user_id = getIntent().getStringExtra("id_user");
        prixMax = getIntent().getStringExtra("prixMax");
        typeChamber = getIntent().getStringExtra("typeChamber");
        checkSpecification = getIntent().getBooleanArrayExtra("checkSpecif");
        recyclerView = findViewById(R.id.recyclerViewHotelsDispo);
        nom_Hotels=     new ArrayList<>();
        adderss_Hotels= new ArrayList<>();
        etoile_Hotels=  new ArrayList<>();
        nbr_chambre=    new ArrayList<>();
        longitude =     new ArrayList<>();
        latitude  =     new ArrayList<>();
        id_Hotel =      new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray obj = new JSONArray(response);
                    if(obj.length() != 0) listeVide.setVisibility(View.VISIBLE);
                    else{
                        listeVide.setVisibility(View.INVISIBLE);
                    }
                    for(int i= 0; i<obj.length();i++){
                        listeVide.setVisibility(View.INVISIBLE);
                        JSONObject obj_2 = obj.getJSONObject(i);
                        id_Hotel.add(obj_2.getString("IdHotel"));
                        nom_Hotels.add(obj_2.getString("nom_hotel"));
                        adderss_Hotels.add(obj_2.getString("addresse_hotel"));
                        etoile_Hotels.add(obj_2.getString("nb_etoile"));
                        //nbr_chambre.add(obj_2.getString("nbrChambre"));
                        //longitude.add(obj_2.getString("longitude"));
                        //latitude.add(obj_2.getString("latitude"));
                    }
                    LinearLayoutManager llm = new LinearLayoutManager(resultatRechercherHotel.this);
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    Myadapter myadapter = new Myadapter(resultatRechercherHotel.this,user_id,id_Hotel,nom_Hotels,adderss_Hotels,etoile_Hotels,dateDebut,dateFin,prixMax,typeChamber);
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
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("place", place);
                data.put("dateDebut",dateDebut);
                data.put("dateFin",dateFin);
                data.put("typeChamber",typeChamber);
                if(checkSpecification[0]) data.put("Piscine","1");
                if(checkSpecification[1]) data.put("Sport","1");
                if(checkSpecification[2]) data.put("Parking","1");
                if(checkSpecification[3]) data.put("wifi","1");
                //if(Float.parseFloat(prixMax) > 0){ data.put("prixMax",prixMax);}
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}