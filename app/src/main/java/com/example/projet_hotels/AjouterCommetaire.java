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

import java.util.HashMap;
import java.util.Map;

public class AjouterCommetaire extends AppCompatActivity {
    private TextInputLayout comment;
    private EditText textComment;
    private String iduser,idHotel,commentA;
    private final String url = Constant.addressIP +"AjouterCommetaire.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_commetaire);
        comment = findViewById(R.id.LayoutComment);
        textComment = findViewById(R.id.ct_Comment);
        Button valider = findViewById(R.id.button21);
        idHotel = getIntent().getStringExtra("idHotel");
        iduser = getIntent().getStringExtra("idUser");
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textComment.getText().toString().equals("")){
                    Toast.makeText(AjouterCommetaire.this,"Le Champs de Commetaire est Vide", Toast.LENGTH_LONG).show();
                    comment.setError("Le Champs est Vide");
                }
                else if(textComment.getText().toString().length() > 300){
                    Toast.makeText(AjouterCommetaire.this,"Vous avez dépassé la limite des caracteres", Toast.LENGTH_LONG).show();
                    comment.setError("Vous avez dépassé la limite des caracteres");
                }
                else{
                    comment.setError(null);
                    commentA = textComment.getText().toString();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(AjouterCommetaire.this,"Votre commentaire est En Attente",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AjouterCommetaire.this,"Erreur de Connexion, Essayer Plus tard",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> data = new HashMap<>();
                            data.put("idHotel", idHotel);
                            data.put("idUser",iduser);
                            data.put("commentA",commentA);
                            return data;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
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