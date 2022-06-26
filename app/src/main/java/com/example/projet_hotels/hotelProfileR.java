package com.example.projet_hotels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class hotelProfileR extends AppCompatActivity implements OnMapReadyCallback {

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private MapView mapView;
    private TextView note,listCommentaireVide,nomHotel,addressHotel,telephone,parking,wifi,sport,piscine;
    private RatingBar etoileHotel,noterHotel;
    private RecyclerView recyclerView;
    private ArrayList<String> nom,commentaire,rating;
    private LatLng positionHotel;
    private String idHotel,idUser;
    private Button reserver;
    private final String url = Constant.addressIP +"infoHotel.php";
    private final String URL = Constant.addressIP +"listeDesCommentaireValider.php";
    private final String url2 = Constant.addressIP +"ajouterNote.php";
    private final String url3 = Constant.addressIP +"getNote.php";
    private final String url4 = Constant.addressIP +"AjouterSupprimerFavoris.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_profile_r);
        //InitialiserLesTextViews
        nomHotel = findViewById(R.id.textView43);
        idHotel = getIntent().getStringExtra("idHotel");
        idUser = getIntent().getStringExtra("idUser");
        reserver = findViewById(R.id.button15);
        reserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hotelProfileR.this,reservatiConf.class);
                intent.putExtra("idHotel",idHotel);
                intent.putExtra("idUser",idUser);
                intent.putExtra("type",getIntent().getStringExtra("type"));
                intent.putExtra("dateDebut",getIntent().getStringExtra("dateDebut"));
                intent.putExtra("dateFin",getIntent().getStringExtra("dateFin"));
                intent.putExtra("prixMax",getIntent().getStringExtra("prixMax"));
                startActivity(intent);
            }
        });
        getNote(idHotel);
        ImageButton favoris = findViewById(R.id.imageButton5);
        favoris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AjouterSupprimerFavoris(idHotel,idUser);
            }
        });
        listCommentaireVide = findViewById(R.id.textView117);
        noterHotel = findViewById(R.id.ratingBar4);
        noterHotel.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                NoterHotel(idHotel,idUser, String.valueOf(rating));
            }
        });
        addressHotel = findViewById(R.id.textView502);
        telephone = findViewById(R.id.textView522);
        parking = findViewById(R.id.textView532);
        wifi = findViewById(R.id.textView56);
        note = findViewById(R.id.textView115);
        sport = findViewById(R.id.textView57);
        piscine = findViewById(R.id.textView55);
        etoileHotel = findViewById(R.id.ratingBarProfileHotel3);
        Button ajouterComment = findViewById(R.id.button9);
        ajouterComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hotelProfileR.this,AjouterCommetaire.class);
                intent.putExtra("idHotel", idHotel);
                intent.putExtra("idUser", idUser);
                startActivity(intent);
            }
        });

        nom = new ArrayList<>();
        commentaire = new ArrayList<>();
        rating = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if(obj.getString("status").equals("success")){
                        idHotel = obj.getString("IdHotel");
                        nomHotel.setText(obj.getString("nom_hotel"));
                        addressHotel.setText(obj.getString("addresse_hotel"));
                        telephone.setText(obj.getString("phone"));
                        etoileHotel.setRating(Float.parseFloat(obj.getString("nb_etoile")));
                        if(obj.getString("piscine").equals("0")) piscine.setPaintFlags(piscine.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        if(obj.getString("sallesport").equals("0")) sport.setPaintFlags(sport.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        if(obj.getString("wifi").equals("0")) wifi.setPaintFlags(wifi.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        if(obj.getString("parking").equals("0")) parking.setPaintFlags(parking.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        positionHotel = new LatLng(Double.parseDouble(obj.getString("cord_latitude")),Double.parseDouble(obj.getString("cord_longitude")));
                        mapView.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(@NonNull GoogleMap googleMap) {
                                googleMap.addMarker(new MarkerOptions().position(positionHotel).title(nomHotel.getText().toString()));
                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(positionHotel));
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(positionHotel,15));
                            }
                        });
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
                data.put("idHotel", idHotel);
                return data;
            }
        };
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mapView = findViewById(R.id.mapView3);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        recyclerView = findViewById(R.id.recycleViewComment);
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray obj = new JSONArray(response);
                    if(obj.length() == 0){
                        listCommentaireVide.setVisibility(View.VISIBLE);
                    }
                    else {
                        listCommentaireVide.setVisibility(View.INVISIBLE);
                        for (int i = 0; i < obj.length(); i++) {
                            JSONObject obj_2 = obj.getJSONObject(i);
                            nom.add(obj_2.getString("nom")+" " +obj_2.getString("prenom"));
                            commentaire.add(obj_2.getString("contenu"));

                        }
                        LinearLayoutManager llm = new LinearLayoutManager(hotelProfileR.this);
                        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
                        MyadapterCommet myadapter = new MyadapterCommet(hotelProfileR.this, nom, commentaire);
                        recyclerView.setLayoutManager(llm);
                        recyclerView.setAdapter(myadapter);
                    }

                } catch (JSONException e) {
                    listCommentaireVide.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(hotelProfileR.this,"Erreur de Connexion, Try Later",Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data;
                data = new HashMap<>();
                data.put("idHotel",idHotel);
                return data;
            }
        };
        requestQueue.add(stringRequest1);
    }

    private void AjouterSupprimerFavoris(String idHotel, String idUser) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url4, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Ajouter")){
                    Toast.makeText(hotelProfileR.this,"Vous avez ajouté  l'hotel "+ nomHotel.getText().toString() +" a votre liste favoris",Toast.LENGTH_LONG).show();
                }
                else if (response.equals("Supprimer")){
                    Toast.makeText(hotelProfileR.this,"Vous avez enlevé  l'hotel "+ nomHotel.getText().toString() +" a votre liste favoris",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(hotelProfileR.this,"Erreur, essayer plus tard",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(hotelProfileR.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data;
                data = new HashMap<>();
                data.put("idHotel",idHotel);
                data.put("idUser",idUser);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        //LatLng hotelCordiation = new LatLng(36.76350061761249, 2.8737590180892316);
        //map.addMarker(new MarkerOptions().position(positionHotel).title(nomHotel.getText().toString()));
        //map.moveCamera(CameraUpdateFactory.newLatLng(positionHotel));
        //map.animateCamera(CameraUpdateFactory.newLatLngZoom(positionHotel,20));
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    public void NoterHotel(String idHotelN,String idUserN,String Note){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("sucess")){
                    Toast.makeText(hotelProfileR.this,"Opération faite par succès",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(hotelProfileR.this,"Erreur, Try Later",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(hotelProfileR.this,"Erreur, Try Later",Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data;
                data = new HashMap<>();
                data.put("idHotel",idHotelN);
                data.put("idUser",idUserN);
                data.put("note",Note);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
    public void getNote(String idHotelN){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url3, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                if(response.equals("NULL")) note.setText("Pas encore noté");
                else if(response.equals("erreur")) note.setText("Pas encore noté");
                else note.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data;
                data = new HashMap<>();
                data.put("idHotel",idHotelN);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}