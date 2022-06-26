package com.example.projet_hotels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

public class listedeschambres extends AppCompatActivity {
    protected RecyclerView liste_chamber;
    private ArrayList<String> idChambre,typeChamber,nomHotel,prix,lalitudeHotel,longtitudeHotel;
    private ArrayList<Float> ratingChamber;
    private String dateDebut,dateFin;
    private BottomNavigationView btnBottom;
    protected String URL = Constant.addressIP +"listeDesChamberdispoibleFavoris.php";
    private String user_id;
    private String prixMax;
    private String idhotel;
    private String type_chamber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listedeschambres);
        idChambre = new ArrayList<>();
        typeChamber = new ArrayList<>();
        nomHotel = new ArrayList<>();
        prix = new ArrayList<>();
        lalitudeHotel = new ArrayList<>();
        longtitudeHotel = new ArrayList<>();
        ratingChamber = new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        dateDebut = extras.getString("dateDebut");
        dateFin = extras.getString("dateFin");
        user_id = extras.getString("id_user");
        prixMax = extras.getString("prixMax");
        idhotel = extras.getString("idhotel");
        type_chamber = extras.getString("typeChamber");
        liste_chamber = findViewById(R.id.listechambers);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray obj = new JSONArray(response);
                    for(int i= 0; i<obj.length();i++){
                        JSONObject obj_2 = obj.getJSONObject(i);
                        idChambre.add(obj_2.getString("idChambre"));
                        typeChamber.add(obj_2.getString("typChambre"));
                        nomHotel.add(obj_2.getString("nomHotel"));
                        prix.add(obj_2.getString("prix"));
                        ratingChamber.add(Float.parseFloat(obj_2.getString("nbEtoile")));
                        lalitudeHotel.add(obj_2.getString("lalitudeHotel"));
                        longtitudeHotel.add(obj_2.getString("longtitudeHotel"));
                    }
                    LinearLayoutManager llm = new LinearLayoutManager(listedeschambres.this);
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    MyadapterChamber myadapter = new MyadapterChamber(listedeschambres.this,idhotel,idChambre,typeChamber,nomHotel,prix,ratingChamber,user_id,dateDebut,dateFin, lalitudeHotel, longtitudeHotel);
                    liste_chamber.setLayoutManager(llm);
                    liste_chamber.setAdapter(myadapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("idHotel", idhotel);
                data.put("dateDebut", dateDebut);
                data.put("dateFin",dateFin);
                data.put("typeChamber",type_chamber);
                if(!prixMax.equals("0")){
                    data.put("prixMax", prixMax);
                }
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        BottomNavigationView btm_NavigatioView;
        btm_NavigatioView = findViewById(R.id.AppbarModiferInfo);
        btm_NavigatioView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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

}