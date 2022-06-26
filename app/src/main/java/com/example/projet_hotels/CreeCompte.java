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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class CreeCompte extends AppCompatActivity {

    private TextInputLayout layout_nom,layout_prenom,layout_mail,layout_pass1,layout_pass2,layout_telephoe;
    private EditText et_nom,et_prenom,et_mail,et_pass1,et_pass2,et_telephoe;
    private Button bt_valider;
    private String URL = Constant.addressIP +"register.php";
    private String nom,prenom,mail,pass1,pass2,telephoe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cree_compte);
        et_mail = findViewById(R.id.cr_mail);
        et_nom = findViewById(R.id.cr_nom);
        layout_telephoe = findViewById(R.id.telephoneLayout);
        et_telephoe = findViewById(R.id.cr_telephone);
        et_prenom = findViewById(R.id.cr_prenom);
        et_pass1 = findViewById(R.id.cr_password1);
        et_pass2 = findViewById(R.id.cr_password2);
        bt_valider = findViewById(R.id.bt_valider);
        layout_nom=findViewById(R.id.crlayoutnom);
        layout_prenom = findViewById(R.id.crlayoutprenom);
        layout_mail = findViewById(R.id.crlayourmail);
        layout_pass1 = findViewById(R.id.cr_layout_pass);
        layout_pass2 = findViewById(R.id.cr_layout_pass2);
        nom = prenom = mail = pass1 = pass2 = "";

    }
    public void register(View view) {
            nom = et_nom.getText().toString().trim();
            prenom = et_prenom.getText().toString().trim();
            mail = et_mail.getText().toString().trim();
            pass1 = et_pass1.getText().toString().trim();
            pass2 = et_pass2.getText().toString().trim();
            telephoe = et_telephoe.getText().toString().trim();
            if(!telephoe.equals("") && !nom.equals("") && !prenom.equals("") && !mail.equals("") && !pass1.equals("") && pass1.equals(pass2)){
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("succes")) {
                            Toast.makeText(CreeCompte.this, "Vous avez Cree le compte", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CreeCompte.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (response.equals("failure")) {
                            layout_nom.setError(null);
                            layout_prenom.setError(null);
                            layout_pass1.setError(null);
                            layout_pass2.setError(null);
                            layout_telephoe.setError(null);
                            layout_mail.setError("Email deja existe");
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
                        data.put("telephone",telephoe);
                        data.put("password",pass1);
                        return data;
                    }

                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
            else{
                if(nom.equals("")){
                    layout_nom.setError("Le Champs est vide !");
                }
                else layout_nom.setError(null);
                if(prenom.equals("")){
                    layout_prenom.setError("Le Champs est vide !");
                }
                else layout_prenom.setError(null);
                if(mail.equals("")){
                    layout_mail.setError("Le Champs est vide !");
                }
                else layout_mail.setError(null);
                if(pass1.equals("")){
                    layout_pass1.setError("Le Champs est vide !");
                }
                else layout_pass1.setError(null);
                if(pass2.equals("")){
                    layout_pass2.setError("Le Champs est vide !");
                }
                else layout_pass2.setError(null);
                if(!pass1.equals(pass2)){
                    layout_pass2.setError("Mot pass incorrect !");
                }
                else{
                    layout_pass2.setError(null);
                }
                if(!telephoe.equals("")){
                    layout_telephoe.setError("Le Champs est Vide !");
                }
                else{
                    layout_telephoe.setError(null);
                }

            }
    }

    public void back_login(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}