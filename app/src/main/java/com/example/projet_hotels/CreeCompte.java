package com.example.projet_hotels;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class CreeCompte extends AppCompatActivity {
    private EditText et_nom,et_prenom,et_mail,et_pass1,et_pass2;
    private Button bt_valider;
    private String URL = "http://192.168.1.25/hotels/register.php";
    private String nom,prenom,mail,pass1,pass2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cree_compte);
        et_mail = findViewById(R.id.cr_mail);
        et_nom = findViewById(R.id.cr_nom);
        et_prenom = findViewById(R.id.cr_prenom);
        et_pass1 = findViewById(R.id.cr_password1);
        et_pass2 = findViewById(R.id.cr_password2);
        bt_valider = findViewById(R.id.bt_valider);
        nom = prenom = mail = pass1 = pass2 = "";

    }
    public void register(View view) {
            nom = et_nom.getText().toString().trim();
            prenom = et_prenom.getText().toString().trim();
            mail = et_mail.getText().toString().trim();
            pass1 = et_pass1.getText().toString().trim();
            pass2 = et_pass2.getText().toString().trim();
            if(!pass1.equals(pass2)){
                Toast.makeText(this,"Mot pass incorrect",Toast.LENGTH_SHORT).show();
            }
            else if(!nom.equals("") && !prenom.equals("") && !mail.equals("") &&!pass1.equals("")){
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if (response.equals("succes")) {
                            Toast.makeText(CreeCompte.this, "L'opération a été fait par succes", Toast.LENGTH_SHORT).show();

                        } else if (response.equals("failure")) {
                            Toast.makeText(CreeCompte.this, "Email déja existe", Toast.LENGTH_SHORT).show();

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CreeCompte.this,error.toString().trim(),Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> data = new HashMap<>();
                        data.put("nom",nom);
                        data.put("prenom",prenom);
                        data.put("mail",mail);
                        data.put("password",pass1);
                        return data;
                    }

                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
            else{
                Toast.makeText(this,"veuillez remplir tous les champ",Toast.LENGTH_SHORT).show();
            }
    }

    public void back_login(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}