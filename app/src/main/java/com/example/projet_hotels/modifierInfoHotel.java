package com.example.projet_hotels;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class modifierInfoHotel extends AppCompatActivity {
    private TextInputLayout Phone_number,Hotel_name,Hotel_Adress,Hotel_Etoile,Hotel_Password,Hotel_Longtitude,Hotel_Latitude,Hotel_email;
    private EditText phoneNumber,nomHotel,addressHotel,etoileHotel,motpassHotel,cordLongitude,cordLatitude,email;
    private final String URL = Constant.addressIP +"creeHotel.php";
    private String mail;
    private final String[] specification={"piscine","Salle de Sport","Parking","Wifi"};
    private  boolean[] checkSpec={false,false,false,false};
    private final String url = Constant.addressIP + "infoHotel.php";
    private final String url_2 = Constant.addressIP+"modifierHotel.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_info_hotel);
        nomHotel = findViewById(R.id.hotel_name_mhotel);
        addressHotel = findViewById(R.id.hotel_addr_mhotel);
        etoileHotel = findViewById(R.id.hotel_etoile_mhotel);
        Hotel_name=findViewById(R.id.Hotel_name_Lay_mhotel);
        Phone_number = findViewById(R.id.telephoneumberLayout_mhotel);
        phoneNumber = findViewById(R.id.phoneNumber_mhotel);
        Hotel_Adress = findViewById(R.id.Addr_Hotel_Lay_mhotel);
        Button valider = findViewById(R.id.button2_mhotel);
        Hotel_Etoile  =findViewById(R.id.Etoil_Hotel_Lay_mhotel);
        cordLongitude = findViewById(R.id.etLongitude_mhotel);
        cordLatitude = findViewById(R.id.etLatitude_mhotel);
        Button checkSepc = findViewById(R.id.button10_mhotel);
        Hotel_Longtitude = findViewById(R.id.Longtitude_Layout_mhotel);
        Hotel_Latitude = findViewById(R.id.Latitude_layout_mhotel);
        String id_Hotel = getIntent().getStringExtra("IDhotel");
        getData(id_Hotel);
        checkSepc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatehotel(id_Hotel);
            }
        });
    }

    private void updatehotel(String id_Hotel) {
        String nom = nomHotel.getText().toString().trim();
        String address = addressHotel.getText().toString().trim();
        String etoile = etoileHotel.getText().toString().trim();
        String longitude = cordLongitude.getText().toString();
        String latitude = cordLatitude.getText().toString();
        String phone = phoneNumber.getText().toString();
        if(!phone.equals("") && !nom.equals("") && !address.equals("") && !etoile.equals("")  && !longitude.equals("") && !latitude.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_2, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.equals("succes")){
                        Toast.makeText(modifierInfoHotel.this, "Opération Fait par succes", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(modifierInfoHotel.this, "Le Nom de hotel deja existe", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(modifierInfoHotel.this, "Erreur de Conextion, essayé plus tard", Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("idHotel",id_Hotel);
                    data.put("HotelName",nom);
                    data.put("Hoteladdr",address);
                    data.put("HotelEtoile",etoile);
                    data.put("HotelLong",longitude);
                    data.put("HotelLatitude",latitude);
                    data.put("phone",phone);
                    if(checkSpec[0]) data.put("piscine","1");
                    if(checkSpec[1]) data.put("SalleSport","1");
                    if(checkSpec[2]) data.put("Parking","1");
                    if(checkSpec[3]) data.put("wifi","1");
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

            if(latitude.equals("")){
                Hotel_Latitude.setError("Le champs est vide");
            }
            else Hotel_Latitude.setError(null);
            if(longitude.equals("")){
                Hotel_Longtitude.setError("Le champs est Vide");
            }
            else Hotel_Longtitude.setError(null);
            if(phone.equals("")){
                Phone_number.setError("Le champs est Vide");
            }
            else Phone_number.setError(null);
        }
    }

    private void getData(String id_hotel){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if(object.getString("status").equals("success")){
                        nomHotel.setText(object.getString("nom_hotel"));
                        addressHotel.setText(object.getString("addresse_hotel"));
                        etoileHotel.setText(object.getString("nb_etoile"));
                        cordLatitude.setText(object.getString("cord_latitude"));
                        cordLongitude.setText(object.getString("cord_longitude"));
                        phoneNumber.setText(object.getString("phone"));
                        if(object.getString("piscine").equals("1")) checkSpec[0]=true;
                        if(object.getString("sallesport").equals("1")) checkSpec[1]=true;
                        if(object.getString("parking").equals("1")) checkSpec[2]=true;
                        if(object.getString("wifi").equals("1")) checkSpec[3]=true;
                    }
                    else{
                        Toast.makeText(modifierInfoHotel.this, "Erreur Try Later,essayé plus tard", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(modifierInfoHotel.this, "Erreur de Conextion, essayé plus tard", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(modifierInfoHotel.this, "Erreur de Conextion, essayé plus tard", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data=  new HashMap<>();
                data.put("idHotel",id_hotel);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Spécification de L'Hotel");
        builder.setMultiChoiceItems(specification, checkSpec, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkSpec[which] = isChecked;
            }
        });
        builder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Arrays.fill(checkSpec, false);
            }
        });
        builder.show();
    }
}