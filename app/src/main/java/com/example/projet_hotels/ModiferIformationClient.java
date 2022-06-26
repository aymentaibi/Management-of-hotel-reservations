package com.example.projet_hotels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ModiferIformationClient extends AppCompatActivity {
    private static final String URL = Constant.addressIP +"modifierinfoClient.php";
    private TextInputLayout TLINom,TLIPrenom,TLIEmail,TLIMotPass,TILtelephone;
    private EditText EDTNom,EDTPrenom,EDTEmail,EDTMotPass,EDTTelephone;
    private String user_id;
    private Button valider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifer_iformation_client);

        user_id = getIntent().getStringExtra("user_id");
        TLINom = findViewById(R.id.txtLayoutNom);
        TLIPrenom =findViewById(R.id.txtLayoutPrenom);
        TILtelephone = findViewById(R.id.textInputLayoutPhone);
        EDTTelephone = findViewById(R.id.infoTelephone);
        EDTTelephone.setText(getIntent().getStringExtra("telephone"));
        TLIEmail = findViewById(R.id.txtLayoutEmail);
        TLIMotPass = findViewById(R.id.txtLayoutMotPass);
        EDTNom = findViewById(R.id.infoClient_nom);
        EDTNom.setText(getIntent().getStringExtra("nom"));
        EDTTelephone = findViewById(R.id.infoTelephone);
        EDTPrenom = findViewById(R.id.infoClient_preom);
        EDTPrenom.setText(getIntent().getStringExtra("prenom"));
        EDTEmail = findViewById(R.id.infoClient_Email);
        EDTEmail.setText(getIntent().getStringExtra("mail"));
        EDTMotPass = findViewById(R.id.infoClient_MotPass);
        valider = findViewById(R.id.button4);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = EDTNom.getText().toString();
                String prenom = EDTPrenom.getText().toString();
                String email = EDTEmail.getText().toString();
                String motPass = EDTMotPass.getText().toString();
                String phone = EDTTelephone.getText().toString();
                if(!phone.equals("") && !nom.equals("") && !prenom.equals("") && !email.equals("") && !motPass.equals("")){
                    TLINom.setError(null);
                    TLIPrenom.setError(null);
                    TLIEmail.setError(null);
                    TLIMotPass.setError(null);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                if(obj.getString("status").equals("succes")) {
                                    Toast.makeText(ModiferIformationClient.this, "Opération faites par success", Toast.LENGTH_LONG).show();
                                    if (getIntent().getStringExtra("admin") != null){
                                        Intent intent = new Intent(ModiferIformationClient.this, profileADMIN.class);
                                        intent.putExtra("id_user", user_id);
                                        startActivity(intent);
                                        finish();
                                }
                                    else if(getIntent().getStringExtra("idHotel") != null){
                                        Intent intent = new Intent(ModiferIformationClient.this, ProfileHotelAccount.class);
                                        intent.putExtra("id_user", user_id);
                                        intent.putExtra("idHotel",getIntent().getStringExtra("idHotel"));
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Intent intent = new Intent(ModiferIformationClient.this, profile.class);
                                        intent.putExtra("id_user", user_id);
                                        startActivity(intent);
                                        finish();

                                    }

                                }
                                else if(obj.getString("status").equals("wrongPass")){
                                    TLIMotPass.setError("Mot pass Incorrect");
                                }
                                else if(obj.getString("status").equals("fail")){
                                    TLIEmail.setError("Email deja utiliser");
                                }
                            } catch (JSONException e) {
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
                            data.put("nom",nom);
                            data.put("prenom",prenom);
                            data.put("email",user_id);
                            if(!getIntent().getStringExtra("mail").equals(email)) data.put("newEmail",email);
                            data.put("motPass",motPass);
                            data.put("telephone",phone);
                            return data;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }
                else{
                    if(nom.equals("")) TLINom.setError("Le Champs est VIDE");
                    else TLINom.setError(null);
                    if(prenom.equals("")) TLIPrenom.setError("Le Champs est VIDE");
                    else TLIPrenom.setError(null);
                    if(email.equals("")) TLIEmail.setError("Le Champs est VIDE");
                    else TLIEmail.setError(null);
                    if(motPass.equals("")) TLIMotPass.setError("Le Champs est VIDE");
                    else TLIMotPass.setError(null);
                    if(phone.equals("")) TILtelephone.setError("Le Champs est VIDE");
                    else TILtelephone.setError(null);
                }

            }
        });
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