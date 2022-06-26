package com.example.projet_hotels;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int ERROR_DIALOG_REQUEST = 1001;
    private EditText etmail,etpassword;
    private TextInputLayout tilMail,tilPassword;
    private String mail,password;
    private String URL = Constant.addressIP +"login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mail = password = "";
        tilMail = findViewById(R.id.txtloginEmail);
        tilPassword = findViewById(R.id.txtLoginPassword);
        etmail = findViewById(R.id.etmail);
        etpassword = findViewById(R.id.etpassword);
    }

    public void login(View fview) {
        mail = etmail.getText().toString().trim();
        password = etpassword.getText().toString().trim();

        if(!mail.equals("") && !password.equals("")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (obj.getString("status").equals("success")) {
                            tilMail.setError(null);
                            tilPassword.setError(null);
                            switch (obj.getString("type_user")){
                                case "normal":
                                    //Todo: CaseNormal
                                    Intent intent_3 = new Intent(MainActivity.this, reservation.class);
                                    intent_3.putExtra("id_user",obj.getString("idUser"));
                                    startActivity(intent_3);
                                    finish();
                                    break;
                                case "admin":
                                    //Todo: startWithAdminProfileAdmine
                                    Intent intent = new Intent(MainActivity.this, ValidationDesHotels.class);
                                    intent.putExtra("id_user",obj.getString("idUser"));
                                    startActivity(intent);
                                    finish();
                                    break;
                                case "hotel":
                                    //Todo: CaseHotel
                                    Intent intent_2 = new Intent(MainActivity.this, ListeDesChambreHotel.class);
                                    intent_2.putExtra("idHotel",obj.getString("idHotel"));
                                    intent_2.putExtra("id_user",obj.getString("idUser"));
                                    startActivity(intent_2);
                                    finish();
                                    break;
                                case "Attente":
                                    Toast.makeText(MainActivity.this,"votre demande est cours de traitement", Toast.LENGTH_LONG).show();
                                    etmail.setText("");
                                    tilMail.setError(null);
                                    etpassword.setText("");
                                    tilPassword.setError(null);
                                case "refuser":
                                    Toast.makeText(MainActivity.this,"votre demande a été refuser", Toast.LENGTH_LONG).show();
                                    etmail.setText("");
                                    tilMail.setError(null);
                                    etpassword.setText("");
                                    tilPassword.setError(null);
                            }

                        } else if (obj.getString("status").equals("failure")) {
                            tilMail.setError("Invalide email ou Mot Pass");
                            tilMail.setError("invalide email ou Mot Pass");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Erreur de connexion! Verfier votre internet", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("mail", mail);
                    data.put("password", password);
                    return data;
                }

            };


            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
     else{
         if(mail.equals(""))tilMail.setError("Champ est Vide");
         else tilMail.setError(null);
         if(password.equals("")) tilPassword.setError("Champ est Vide");
         else tilPassword.setError(null);
        }
    }

    public void creeAccout(View view) {
        Intent intent = new Intent(this, CreeCompte.class);
        startActivity(intent);
    }
    public void compteResposableH(View view){
        Intent intent = new Intent(this, CreateHotel.class);
        startActivity(intent);
    }
}