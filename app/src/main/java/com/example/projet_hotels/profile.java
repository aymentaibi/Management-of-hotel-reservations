package com.example.projet_hotels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        BottomNavigationView btm_NavigatioView;
        btm_NavigatioView = findViewById(R.id.bottom_navigation);
        btm_NavigatioView.setSelectedItemId(R.id.profile);
        btm_NavigatioView.setOnNavigationItemSelectedListener(navListener);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // By using switch we can easily get
            // the selected fragment
            // by using there id.
            switch (item.getItemId()) {
                case R.id.add_hotel:
                    startActivity(new Intent(getApplicationContext(),CreateHotel.class));
                    finish();
                    return true;
                case R.id.profile:
                    return true;

            }
            return false;
        }
    };
}