package com.example.projet_hotels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

public class CreateHotel extends AppCompatActivity {
    private TextInputLayout Hotel_name,Hotel_Adress,Hotel_Etoile,Hotel_Password;
    private EditText nomHotel,addressHotel,etoileHotel,motpassHotel;
    private String nom,address,etoile,motpass;
    private String URL = "http://192.168.1.25/hotels/creeHotel.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_hotel);
        nomHotel = findViewById(R.id.hotel_name);
        addressHotel = findViewById(R.id.hotel_addr);
        etoileHotel = findViewById(R.id.hotel_etoile);
        motpassHotel = findViewById(R.id.mot_pass_hotel);
        Hotel_name=findViewById(R.id.Hotel_name_Lay);
        Hotel_Adress = findViewById(R.id.Addr_Hotel_Lay);
        Hotel_Etoile  =findViewById(R.id.Etoil_Hotel_Lay);
        Hotel_Password = findViewById(R.id.Pass_Hoyel_Lay);
        nom = address =etoile = motpass = "";
        BottomNavigationView btm_NavigatioView;
        btm_NavigatioView = findViewById(R.id.bottom_navigation);
        btm_NavigatioView.setSelectedItemId(R.id.add_hotel);
        btm_NavigatioView.setOnNavigationItemSelectedListener(navListener);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // By using switch we can easily get
            // the selected fragment
            // by using there id.
            switch (item.getItemId()) {
                case R.id.profile:
                    startActivity(new Intent(getApplicationContext(),profile.class));
                    finish();
                    return true;
                case R.id.dashboard:
                    startActivity(new Intent(getApplicationContext(),ListeDesHotels.class));
                    finish();
                    return true;
            }
            return false;
        }
        };


    public void creeHotel(View view) {
        nom = nomHotel.getText().toString().trim();
        address = addressHotel.getText().toString().trim();
        etoile = etoileHotel.getText().toString().trim();
        motpass = motpassHotel.getText().toString().trim();
        if(!nom.equals("") && !address.equals("") && !etoile.equals("") && !motpass.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("succes")) {
                        Toast.makeText(CreateHotel.this, "L'opération a été fait par succes", Toast.LENGTH_SHORT).show();

                    } else if (response.equals("failure")) {
                        Toast.makeText(CreateHotel.this, "Email déja existe", Toast.LENGTH_SHORT).show();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(CreateHotel.this,error.toString().trim(),Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> data = new HashMap<>();
                    data.put("nom",nom);
                    data.put("address",address);
                    data.put("nombreEtoile",etoile);
                    data.put("password",motpass);
                    return data;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
        else{
            if(nom.equals("")){
                Hotel_name.setError("Le Champs est vide !");
            }
            else Hotel_name.setError(null);
            if(address.equals("")){
                Hotel_Adress.setError("Le Champs est vide !");
            }
            else Hotel_Adress.setError(null);
            if(etoile.equals("")){
                Hotel_Etoile.setError("Le Champs est vide !");
            }
            else Hotel_Etoile.setError(null);
            if(motpass.equals("")){
                Hotel_Password.setError("Le Champs est vide !");
            }
            else Hotel_Password.setError(null);
        }
    }
}