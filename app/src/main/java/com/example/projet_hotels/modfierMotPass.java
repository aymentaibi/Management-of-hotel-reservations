package com.example.projet_hotels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class modfierMotPass extends AppCompatActivity {
    private static final String URL = Constant.addressIP +"modifierMotPass.php";
    private TextInputLayout TILMotPass1,TILMotPass2,TILMotPass3;
    private EditText motpass1,motpass2,motpass3;
    private Button btnValider;
    private String id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modfier_mot_pass);
        TILMotPass1 = findViewById(R.id.textInputLayout9);
        id_user = getIntent().getStringExtra("user_id");
        TILMotPass2 = findViewById(R.id.textInputLayout7);
        TILMotPass3 = findViewById(R.id.textInputLayout8);
        motpass1 = findViewById(R.id.mot_pass_old);
        motpass2 = findViewById(R.id.newPassWord);
        motpass3 = findViewById(R.id.confirmerMotPass);
        btnValider = findViewById(R.id.button6);
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(motpass1.getText().toString().equals("")){
                    TILMotPass1.setError("Champs est Vide");
                }
                else if(motpass2.getText().toString().equals("")){
                        TILMotPass1.setError(null);
                        if(motpass2.getText().toString().length() < 8 ){
                            TILMotPass2.setError("le Mot de passe Doit être plus de 8 caractere");
                        }
                        else{
                            TILMotPass2.setError(null);
                        }
                }
                else if(motpass3.getText().toString().equals(motpass2.getText().toString())){
                    TILMotPass2.setError(null);
                    TILMotPass3.setError(null);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                if(obj.getString("status").equals("succes")){
                                    Toast.makeText(modfierMotPass.this,"Mot passe Changer par succès",Toast.LENGTH_LONG).show();
                                    finish();
                                }
                                else{
                                    TILMotPass1.setError("Mot pass Incorrect");
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
                            data.put("id_user", id_user);
                            data.put("pass", motpass1.getText().toString());
                            data.put("pass2",motpass3.getText().toString());
                            return data;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }
                else{
                    TILMotPass3.setError("Mot pass Incorrect");
                }
            }
        });
        BottomNavigationView btm_NavigatioView;
        btm_NavigatioView = findViewById(R.id.bottomAppBar_backIteneraire);
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