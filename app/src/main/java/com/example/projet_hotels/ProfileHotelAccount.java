package com.example.projet_hotels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileHotelAccount extends AppCompatActivity {
    private static final String URL = Constant.addressIP +"inforamtionProfile.php";
    private TextView txNom,txPrenom,txtMail;
    private String id_user,telephone;
    private Button btnModiferMotpass,btnModiferInfo,btnModiferInfoHotel;
    private BottomNavigationView btnBottom;
    private String id_hotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_hotel_account);
        id_hotel = getIntent().getStringExtra("idHotel");
        id_user = getIntent().getStringExtra("id_user");
        btnModiferInfoHotel = findViewById(R.id.button_hotel2);
        txNom = findViewById(R.id.nom_profile_hotel);
        txPrenom = findViewById(R.id.textView11_hotel);
        txtMail = findViewById(R.id.textView12_hotel);
        btnModiferMotpass = findViewById(R.id.button5_hotel);
        btnModiferInfo = findViewById(R.id.button_hotel);
        BottomNavigationView btm_NavigatioView;
        btm_NavigatioView = findViewById(R.id.brn_navig_profile_hotel);
        btm_NavigatioView.setSelectedItemId(R.id.status_hotel);
        btm_NavigatioView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.add_Chamber:
                        Intent intent = new Intent(ProfileHotelAccount.this,ListeDesChambreHotel.class);
                        intent.putExtra("idHotel",id_hotel);
                        intent.putExtra("id_user",id_user);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.dashboard_hotel:
                        Intent intent1 = new Intent(ProfileHotelAccount.this,listeDesReservatioHotel.class);
                        intent1.putExtra("idHotel",id_hotel);
                        intent1.putExtra("id_user",id_user);
                        startActivity(intent1);
                        finish();
                        return true;
                }
                return false;
            }
        });
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if(obj.getString("status").equals("success")){
                        txNom.setText(obj.getString("nom"));
                        txPrenom.setText(obj.getString("prenom"));
                        txtMail.setText(obj.getString("mail"));
                        telephone = obj.getString("telephone");
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
                data.put("mail", id_user);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        btnModiferMotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),modfierMotPass.class);
                intent.putExtra("user_id",id_user);
                startActivity(intent);
            }
        });
        btnModiferInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ModiferIformationClient.class);
                intent.putExtra("user_id",id_user);
                intent.putExtra("nom",txNom.getText());
                intent.putExtra("prenom",txPrenom.getText());
                intent.putExtra("mail",txtMail.getText());
                intent.putExtra("telephone",telephone);
                intent.putExtra("idHotel",id_hotel);
                startActivity(intent);
            }
        });

        BottomNavigationView btm_NavigatioView2;
        btm_NavigatioView2 = findViewById(R.id.AppbarProfileD_hotel);
        btm_NavigatioView2.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.sigh_out){
                    Intent intent = new Intent(ProfileHotelAccount.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });

        btnModiferInfoHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileHotelAccount.this,modifierInfoHotel.class);
                intent.putExtra("IDhotel",id_hotel);
                startActivity(intent);
            }
        });
    }
}