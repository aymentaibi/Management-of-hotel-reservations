package com.example.projet_hotels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;

public class rechercherUneChamberSurFavoris extends AppCompatActivity {
    private Button mDatePicker,btnRechercher;
    private TextView date_debut,date_fin;
    private EditText edprix;
    private String id_user,id_hotel;
    private final String[] specification={"Piscine","Salle De Sport","Parking","wifi"};
    private  boolean[] checkSpec={false,false,false,false};
    private String prixMax;
    private String dateDebut;
    private String dateFin;
    private String typeChmber;
    private Spinner chamberType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechercher_une_chamber_sur_favoris);
        chamberType = findViewById(R.id.spinner3);
        ArrayList<String> chamberType2 = new ArrayList<>();
        chamberType2.add("simple");
        chamberType2.add("double pour une personne");
        chamberType2.add("double");
        chamberType2.add("triple");
        chamberType2.add("quadruple");
        chamberType2.add("dortoir");

        ArrayAdapter<String> typesAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                chamberType2
        );
        chamberType.setAdapter(typesAdapter);
        chamberType.setSelection(0);
        typeChmber = "simple";
        chamberType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typeChmber = (String) chamberType.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //edRechercher = findViewById(R.id.loca_Ville);
        date_debut = findViewById(R.id.date_debut3);
        date_fin = findViewById(R.id.date_fin3);
        mDatePicker = findViewById(R.id.bt_dateFavroisChamber);
        btnRechercher = findViewById(R.id.button22);

        edprix = findViewById(R.id.prixMaxInput2);
        id_user = getIntent().getStringExtra("idUser");
        id_hotel = getIntent().getStringExtra("idHotel");
        //bottom Navigation View
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();
        long today = MaterialDatePicker.todayInUtcMilliseconds();
        calendar.setTimeInMillis(today);

        CalendarConstraints.Builder cons = new CalendarConstraints.Builder();
        cons.setStart(today);
        cons.setValidator(DateValidatorPointForward.now());

        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Période de reservation");
        builder.setCalendarConstraints(cons.build());
        MaterialDatePicker materialDatePicker = builder.build();
        mDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(),"DAYE_PICKER");
            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Pair<Long, Long> data = (Pair<Long, Long>) selection;
                Long long1 = data.first;
                Long long2 = data.second;
                date_debut.setText(TimeConvert.getDate(long1,"yyyy-MM-dd"));
                date_fin.setText(TimeConvert.getDate(long2,"yyyy-MM-dd"));
                date_debut.setTextSize(24);
                date_fin.setTextSize(24);
            }
        });
        btnRechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prixMax = edprix.getText().toString();
                dateDebut = date_debut.getText().toString();
                dateFin = date_fin.getText().toString();
                if(!dateDebut.equals("--/--/----") && !dateFin.equals("--/--/----")){
                    //Try new Method
                    //Intent intent_2 = new Intent(reservation.this, listedeschambres.class);
                    Intent intent_2 = new Intent(rechercherUneChamberSurFavoris.this, listedeschambres.class);
                    intent_2.putExtra("dateDebut",date_debut.getText().toString());
                    intent_2.putExtra("dateFin",date_fin.getText().toString());
                    intent_2.putExtra("id_user",id_user);
                    intent_2.putExtra("prixMax",prixMax);
                    intent_2.putExtra("idhotel",id_hotel);
                    intent_2.putExtra("typeChamber",typeChmber);
                    startActivity(intent_2);
                }
                else{
                    if(dateFin.equals("--/--/----")) Toast.makeText(rechercherUneChamberSurFavoris.this,"Selection La Date de Reservation",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}