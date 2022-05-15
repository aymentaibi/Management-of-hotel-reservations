package com.example.projet_hotels;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText etmail,etpassword;
    private String mail,password;
    private String URL = "http://192.168.1.25/hotels/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mail = password = "";
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
                            switch (obj.getString("type_user")){
                                case "normal":
                                    Intent intent_3 = new Intent(MainActivity.this, reservation.class);
                                    startActivity(intent_3);
                                    finish();
                                    break;
                                case "admin":
                                    Intent intent = new Intent(MainActivity.this, CreateHotel.class);
                                    startActivity(intent);
                                    finish();
                                    break;
                                case "hotel":
                                    Intent intent_2 = new Intent(MainActivity.this, AjouterChambre.class);
                                    intent_2.putExtra("hotel_name",obj.getString("nom"));
                                    startActivity(intent_2);
                                    finish();
                                    break;

                            }

                        } else if (obj.getString("status").equals("failure")) {
                            Toast.makeText(MainActivity.this, "Invalid Login ID/PASSWORD", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this,"STP REMPLIRE TOUS LES CHAMPS",Toast.LENGTH_SHORT).show();
        }
    }

    public void creeAccout(View view) {
        Intent intent = new Intent(this, CreeCompte.class);
        startActivity(intent);
        finish();
    }
}