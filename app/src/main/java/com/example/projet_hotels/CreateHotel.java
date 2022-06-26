package com.example.projet_hotels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CreateHotel extends AppCompatActivity {
    private TextInputLayout nom_respon,prenom_respons,Phone_number,Hotel_name,Hotel_Adress,Hotel_Etoile,Hotel_Password,Hotel_Longtitude,Hotel_Latitude,Hotel_email;
    private EditText nomrespon,prenomrespon,phoneNumber,nomHotel,addressHotel,etoileHotel,motpassHotel,cordLongitude,cordLatitude,email;
    private String phone,nom,address,etoile,motpass,longitude,latitude;
    private final String URL = Constant.addressIP +"creeHotel.php";
    private String mail;
    private Button checkSepc,valider;
    private final String[] specification={"piscine","Salle de Sport","Parking","Wifi"};
    private  boolean[] checkSpec={false,false,false,false};
    private String nomRespon,prenomRespon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_hotel);
        nomHotel = findViewById(R.id.hotel_name);
        nom_respon = findViewById(R.id.textInputLayoutNom);
        prenom_respons = findViewById(R.id.textInputLayoutPrenom);
        nomrespon = findViewById(R.id.cr_nom_resp);
        prenomrespon = findViewById(R.id.cr_prenom_resp);
        addressHotel = findViewById(R.id.hotel_addr);
        etoileHotel = findViewById(R.id.hotel_etoile);
        motpassHotel = findViewById(R.id.mot_pass_hotel);
        Hotel_name=findViewById(R.id.Hotel_name_Lay);
        Phone_number = findViewById(R.id.telephoneumberLayout);
        phoneNumber = findViewById(R.id.phoneNumber);
        Hotel_Adress = findViewById(R.id.Addr_Hotel_Lay);
        valider = findViewById(R.id.button2);
        Hotel_Etoile  =findViewById(R.id.Etoil_Hotel_Lay);
        Hotel_Password = findViewById(R.id.Pass_Hoyel_Lay);
        cordLongitude = findViewById(R.id.etLongitude);
        cordLatitude = findViewById(R.id.etLatitude);
        checkSepc = findViewById(R.id.button10);
        Hotel_Longtitude = findViewById(R.id.Longtitude_Layout);
        Hotel_Latitude = findViewById(R.id.Latitude_layout);
        email = findViewById(R.id.hotel_mail);
        Hotel_email = findViewById(R.id.Email_Hotel);
        nom = address =etoile = motpass = "";
        checkSepc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomRespon = nomrespon.getText().toString();
                prenomRespon = prenomrespon.getText().toString();
                nom = nomHotel.getText().toString().trim();
                address = addressHotel.getText().toString().trim();
                etoile = etoileHotel.getText().toString().trim();
                motpass = motpassHotel.getText().toString().trim();
                longitude = cordLongitude.getText().toString();
                latitude = cordLatitude.getText().toString();
                mail = email.getText().toString();
                phone = phoneNumber.getText().toString();
                if(!nomRespon.equals("") && !prenomRespon.equals("") && !phone.equals("") && !nom.equals("") && !address.equals("") && !etoile.equals("") && !motpass.equals("") && !longitude.equals("") && !latitude.equals("") && !mail.equals("")){
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("succes")){
                                finish();
                                Toast.makeText(CreateHotel.this, "votre demande est cours de traitement", Toast.LENGTH_SHORT).show();
                            }
                            if(response.equals("failure")){
                                Hotel_name.setError("Le Nom de L'hotel Déja existe");
                            }
                            else Hotel_name.setError(null);

                            if(response.equals("Email existe")){
                                Hotel_email.setError("Email déja Utiliser");
                            }
                            else Hotel_email.setError(null);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(CreateHotel.this, "Error connecting with Server", Toast.LENGTH_SHORT).show();
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
                            data.put("latitude",latitude);
                            data.put("longitude",longitude);
                            data.put("email",mail);
                            data.put("phone",phone);
                            data.put("nomRes",nomRespon);
                            data.put("prenomRes",prenomRespon);
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
                    if(nomRespon.equals("")){
                        nom_respon.setError("Le Champs est vide !");
                    }
                    else nom_respon.setError(null);
                    if(prenomRespon.equals("")){
                        prenom_respons.setError("Le Champs est vide !");
                    }
                    else prenom_respons.setError(null);
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
                    if(latitude.equals("")){
                        Hotel_Latitude.setError("Le champs est vide");
                    }
                    else Hotel_Latitude.setError(null);
                    if(longitude.equals("")){
                        Hotel_Longtitude.setError("Le champs est Vide");
                    }
                    else Hotel_Longtitude.setError(null);
                    if(mail.equals("")){
                        Hotel_email.setError("le champs est Vide");
                    }
                    else Hotel_email.setError(null);
                    if(phone.equals("")){
                        Phone_number.setError("Le champs est Vide");
                    }
                    else Phone_number.setError(null);
                }
            }
        });
    }
    private void SupperErrors(){
        Hotel_name.setError(null);
        Hotel_Adress.setError(null);
        Hotel_Etoile.setError(null);
        Hotel_email.setError(null);
        Hotel_Longtitude.setError(null);
        Hotel_Latitude.setError(null);
        Hotel_Password.setError(null);
        Phone_number.setError(null);
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