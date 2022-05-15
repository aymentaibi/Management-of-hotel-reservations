package com.example.projet_hotels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolder> {
    ArrayList<String> data1,data2,data3,data4;

    Context context;
    public Myadapter(Context ct , ArrayList<String> s1, ArrayList<String> s2,ArrayList<String> s3, ArrayList<String> s4) {
        context = ct;
        data1 = s1;
        data2 = s2;
        data3 = s3;
        data4 = s4;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.titre.setText(data1.get(position));
        holder.desc.setText(data2.get(position));
        holder.nbrChamber.setText(data3.get(position));
        holder.nbEtoile.setRating(Float.parseFloat(data4.get(position)));
    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titre,desc,nbrChamber;
        RatingBar nbEtoile;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titre = itemView.findViewById(R.id.titre_chamber);
            desc  = itemView.findViewById(R.id.descChamber);
            nbrChamber = itemView.findViewById(R.id.nbr_chamber);
            nbEtoile = itemView.findViewById(R.id.ratingStarts);
        }
    }
}
