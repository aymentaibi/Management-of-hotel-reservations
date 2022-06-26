package com.example.projet_hotels;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyadapterAttente extends RecyclerView.Adapter<MyadapterAttente.MyViewHolder> {
    ArrayList<String> data1,data2,data4,IdHotel;
    String userID;
    Context context;
    public MyadapterAttente(Context ct ,String user_ID, ArrayList<String> s1, ArrayList<String> s2, ArrayList<String> s4,ArrayList<String> s3) {
        context = ct;
        userID = user_ID;
        data1 = s1;
        data2 = s2;
        data4 = s4;
        IdHotel = s3;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_card_2,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.titre.setText(data1.get(position));
        holder.desc.setText(data2.get(position));
        holder.nbEtoile.setRating(Float.parseFloat(data4.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,profileHotel.class);
                intent.putExtra("hotelId",IdHotel.get(position));
                intent.putExtra("id_user",userID);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titre,desc;
        RatingBar nbEtoile;
        ConstraintLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titre = itemView.findViewById(R.id.titre_chamber_2);
            desc  = itemView.findViewById(R.id.descChamber_2);
            nbEtoile = itemView.findViewById(R.id.ratingStarts_2);
            mainLayout = itemView.findViewById(R.id.hotel_attente);
        }
    }
}
