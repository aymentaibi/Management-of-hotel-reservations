package com.example.projet_hotels;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AjouterChambre extends AppCompatActivity {
    protected Button bt_valider;
    protected EditText ed_id_chambre,ed_typechambre,ed_Prix;
    protected String idHotel,id_chambre,type_chambre,chambre_prix;
    protected String URL = Constant.addressIP +"ajouterch.php";
    protected Spinner type_chamberSpinner;
    private String id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_chambre);
        Bundle extras = getIntent().getExtras();
        idHotel = extras.getString("idHotel");
        id_user = extras.getString("id_user");
        ed_id_chambre = findViewById(R.id.chambre_id);
        ed_Prix = findViewById(R.id.prix_chambre);
        bt_valider = findViewById(R.id.bt_valider);

        type_chamberSpinner = findViewById(R.id.spinner_type_chamb);
        ArrayList<String> types_chamber = new ArrayList<>();
        types_chamber.add("Chambre simple");
        types_chamber.add("Chambre double pour une personne");
        types_chamber.add("Chambre double");
        types_chamber.add("Chambre triple");
        types_chamber.add("Chambre quadruple");
        types_chamber.add("Chambre dortoir");

        ArrayAdapter<String> typesAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                types_chamber
        );
        type_chamberSpinner.setAdapter(typesAdapter);
        type_chamberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type_chambre = types_chamber.get(i).substring(8);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void ajouter_chamber(View view) {
        id_chambre = ed_id_chambre.getText().toString().trim();
        chambre_prix = ed_Prix.getText().toString().trim();

        if(!id_chambre.equals("") && !type_chambre.equals("") && !chambre_prix.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (obj.getString("status").equals("succes")){
                            Toast.makeText(AjouterChambre.this, "L'opération a été fait par succes", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AjouterChambre.this,ListeDesChambreHotel.class);
                            intent.putExtra("idHotel",idHotel);
                            intent.putExtra("id_user",id_user);
                            startActivity(intent);
                        }
                        else if (obj.getString("status").equals("failure")){
                            Toast.makeText(AjouterChambre.this, "ID déja existe", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AjouterChambre.this,error.toString().trim(),Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> data = new HashMap<>();
                    data.put("id",id_chambre);
                    data.put("type",type_chambre);
                    data.put("chambre",chambre_prix);
                    data.put("idHotel", idHotel);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
        else{
            Toast.makeText(this,"Remplire tous les champs",Toast.LENGTH_SHORT).show();
        }

    }
}