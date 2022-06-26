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

public class MyadapterFavoris extends RecyclerView.Adapter<MyadapterFavoris.MyViewHolder> {
    ArrayList<String> data1,data2,data3,data4,idHotel;
    String id_user;
    Context context;
    public MyadapterFavoris(Context ct ,String userid,ArrayList<String> s5, ArrayList<String> s1, ArrayList<String> s2, ArrayList<String> s3, ArrayList<String> s4) {
        context = ct;
        idHotel = s5;
        id_user = userid;
        data1 = s1;
        data2 = s2;
        data3 = s3;
        data4 = s4;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_card_favoris, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.titre.setText(data1.get(position));
        holder.desc.setText(data2.get(position));
        holder.nbrChamber.setText(data3.get(position));
        holder.nbEtoile.setRating(Float.parseFloat(data4.get(position)));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,hotelProfileRF.class);
                intent.putExtra("idHotel",idHotel.get(position));
                intent.putExtra("idUser",id_user);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titre,desc,nbrChamber;
        RatingBar nbEtoile;
        ConstraintLayout layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titre = itemView.findViewById(R.id.titre_chamber);
            desc  = itemView.findViewById(R.id.descChamber);
            nbrChamber = itemView.findViewById(R.id.nbr_chamber);
            nbEtoile = itemView.findViewById(R.id.ratingStarts);
            layout = itemView.findViewById(R.id.LayoutFavoris);
        }
    }
}
