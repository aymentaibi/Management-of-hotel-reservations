package com.example.projet_hotels;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class searchHotel extends AppCompatActivity {

    private RecyclerView recyclerView;
    protected String URL = Constant.addressIP +"listeDesHotels.php";
    private ArrayList<String> nom_Hotels;
    private ArrayList<String> adderss_Hotels;
    private ArrayList<String> etoile_Hotels;
    private ArrayList<String> nbr_chambre;
    private Button btnRechercher,btnDetail;
    private EditText edtVille;
    private String ville;
    private TextInputLayout tilRecherhcer;
    private final String[] specification={"Piscine","Salle De Sport","Parking","wifi"};
    private  boolean[] checkSpec={false,false,false,false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_hotel);
        btnDetail = findViewById(R.id.button14);
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        nom_Hotels = new ArrayList<>();
        adderss_Hotels = new ArrayList<>();
        etoile_Hotels = new ArrayList<>();
        nbr_chambre = new ArrayList<>();
        recyclerView = findViewById(R.id.RecycleViewHotels);
        btnRechercher = findViewById(R.id.button7);
        edtVille = findViewById(R.id.reserv_ville);
        tilRecherhcer = findViewById(R.id.textInputLayout3);

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
                    LinearLayoutManager llm = new LinearLayoutManager(searchHotel.this);
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    Myadapter myadapter = new Myadapter(searchHotel.this,nom_Hotels,adderss_Hotels,nbr_chambre,etoile_Hotels);
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
        btnRechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ville = edtVille.getText().toString();
                if(ville.equals("")){
                    tilRecherhcer.setError("Le champs est Vide");
            }
                else{
                    tilRecherhcer.setError(null);
                    Intent intent = new Intent(getApplicationContext(),resultatRechercherHotel.class);
                    intent.putExtra("ville",ville);
                    intent.putExtra("specification",checkSpec);
                    startActivity(intent);
                }
            }
        });
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Spécification de d'hotel");
        builder.setMultiChoiceItems(specification, checkSpec, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkSpec[which] = isChecked;
            }
        });
        builder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Arrays.fill(checkSpec, false);
            }
        });
        builder.show();
    }

}