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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModiferChambre extends AppCompatActivity {

    private Spinner type_chamberSpinner;
    private String type_chambre,idHotel;
    private TextInputLayout tilID,tilPRIX;
    private EditText etid,etprix;
    private TextView IDchamb;
    private Button valider;
    private final String URL = Constant.addressIP + "modifierChamber.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifer_chambre);
        tilID = findViewById(R.id.textInputLayout2);
        tilPRIX = findViewById(R.id.textInputLayout5);
        etid = findViewById(R.id.chambre_id);
        etprix = findViewById(R.id.prix_chambre);
        IDchamb = findViewById(R.id.textView78);
        type_chamberSpinner = findViewById(R.id.spinner_type_chamb);
        IDchamb.setText(getIntent().getStringExtra("idChamb"));
        etprix.setText(getIntent().getStringExtra("prixChamber"));
        idHotel = getIntent().getStringExtra("idHotel");
        type_chambre = getIntent().getStringExtra("typeChamber");
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
        switch (type_chambre){
            case "simple": type_chamberSpinner.setSelection(0);break;
            case "double pour une personne": type_chamberSpinner.setSelection(1);break;
            case "double": type_chamberSpinner.setSelection(2);break;
            case "triple": type_chamberSpinner.setSelection(3);break;
            case "quadruple": type_chamberSpinner.setSelection(4);break;
            case "dortoir": type_chamberSpinner.setSelection(5);break;
        }
        type_chamberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type_chambre = types_chamber.get(i).substring(8);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        valider = findViewById(R.id.btn_valider_ch);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("succes")){
                            Toast.makeText(ModiferChambre.this,"L'opératio faite Par succes",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ModiferChambre.this,ListeDesChambreHotel.class);
                            intent.putExtra("idHotel",idHotel);
                            intent.putExtra("id_user",getIntent().getStringExtra("id_user"));
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(ModiferChambre.this,"Tu peux Pas modifier ce Champ",Toast.LENGTH_LONG).show();
                            tilID.setError("tu peux pas Modifier ce champ");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ModiferChambre.this,"Erreur de Connexion essayer plus tard",Toast.LENGTH_LONG).show();
                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> data = new HashMap<>();
                        data.put("idHotel",idHotel);
                        data.put("idChamb",IDchamb.getText().toString());
                        if(!etid.getText().toString().equals("")) data.put("newIdChamb",etid.getText().toString());
                        data.put("type",type_chambre);
                        data.put("prix",etprix.getText().toString());
                        return data;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });

    }
}