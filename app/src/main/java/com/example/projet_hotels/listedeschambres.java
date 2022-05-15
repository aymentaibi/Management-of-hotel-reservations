package com.example.projet_hotels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class listedeschambres extends AppCompatActivity {
    protected RecyclerView liste_chamber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listedeschambres);
        liste_chamber = findViewById(R.id.listechambers);


    }
}