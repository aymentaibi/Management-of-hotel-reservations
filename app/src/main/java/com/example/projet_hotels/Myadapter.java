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

public class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolder> {
    private String dateDebutA,dateFinA,type_ChamberA,prixMaxA;
    ArrayList<String> data1,data2,data3,data4,idHotelA;
    String idClientA;

    Context context;
    public Myadapter(Context ct , ArrayList<String> s1, ArrayList<String> s2,ArrayList<String> s3, ArrayList<String> s4) {
        context = ct;
        data1 = s1;
        data2 = s2;
        data3 = s3;
        data4 = s4;
    }

    public Myadapter(resultatRechercherHotel ct,String idClient,ArrayList<String> idHotel, ArrayList<String> nom_hotels, ArrayList<String> adderss_hotels, ArrayList<String> etoile_hotels,String dateDebut,String dateFin,String prixMax,String type_Chamber) {
        context = ct;
        idClientA = idClient;
        idHotelA = idHotel;
        data1 = nom_hotels;
        data2 = adderss_hotels;
        data4 = etoile_hotels;
        dateDebutA = dateDebut;
        dateFinA = dateFin;
        type_ChamberA = type_Chamber;
        prixMaxA = prixMax;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.titre.setText(data1.get(position));
        holder.desc.setText(data2.get(position));
        if(data3 != null) {
            holder.nbrChamber.setText(data3.get(position));
        }
        else{
            holder.nbrChamber.setVisibility(View.INVISIBLE);
            holder.nbrChm.setVisibility(View.INVISIBLE);
            holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,hotelProfileR.class);
                    intent.putExtra("idUser",idClientA);
                    intent.putExtra("idHotel",idHotelA.get(position));
                    intent.putExtra("type",type_ChamberA);
                    intent.putExtra("dateDebut",dateDebutA);
                    intent.putExtra("dateFin",dateFinA);
                    intent.putExtra("prixMax",prixMaxA);
                    context.startActivity(intent);
                }
            });
        }
        holder.nbEtoile.setRating(Float.parseFloat(data4.get(position)));
    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titre,desc,nbrChamber,nbrChm;
        RatingBar nbEtoile;
        ConstraintLayout constraintLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titre = itemView.findViewById(R.id.titre_chamber);
            constraintLayout = itemView.findViewById(R.id.layoutList);
            nbrChm= itemView.findViewById(R.id.textView13);
            desc  = itemView.findViewById(R.id.descChamber);
            nbrChamber = itemView.findViewById(R.id.nbr_chamber);
            nbEtoile = itemView.findViewById(R.id.ratingStarts);
        }
    }
}
