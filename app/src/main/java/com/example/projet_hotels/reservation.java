package com.example.projet_hotels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet_hotels.ui.TimeConvert;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.Calendar;
import java.util.TimeZone;

public class reservation extends AppCompatActivity {
    private Button mDatePicker;
    private TextView date_debut,date_fin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        date_debut = findViewById(R.id.date_debut);
        date_fin = findViewById(R.id.date_fin);
        mDatePicker = findViewById(R.id.bt_date);

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
            date_debut.setText(TimeConvert.getDate(long1,"dd/MM/yyyy"));
            date_fin.setText(TimeConvert.getDate(long2,"dd/MM/yyyy"));
            date_debut.setTextSize(24);
            date_fin.setTextSize(24);
        }
    });
    }
}