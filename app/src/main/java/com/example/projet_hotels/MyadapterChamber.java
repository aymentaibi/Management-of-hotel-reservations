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

public class MyadapterChamber extends RecyclerView.Adapter<MyadapterChamber.MyViewHolder> {
    private ArrayList<String> data1,data2,data3,data4;
    private Context context;
    private ArrayList<Float> data5;
    private String user_id,date_debut,date_fin,id_hotel;
    private ArrayList<String> dataLalitude,longtitude;

    public MyadapterChamber(Context ct,String idhotel,ArrayList<String> s1, ArrayList<String> s2, ArrayList<String> s3, ArrayList<String> s4, ArrayList<Float> s5, String user_id, String date1, String date2, ArrayList<String> data6, ArrayList<String> data7) {
        context = ct;
        data1 = s1;
        data2 = s2;
        data3 = s3;
        data4 = s4;
        data5 = s5;
        id_hotel = idhotel;
        this.user_id = user_id;
        date_debut = date1;
        date_fin = date2;
        dataLalitude = data6;
        longtitude = data7;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_chambre_result,parent,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.idChmaber.setText(data1.get(position));
        holder.typeChamber.setText(data2.get(position));
        holder.hotelName.setText(data3.get(position));
        holder.prixChamber.setText(data4.get(position)+" DZD");
        holder.nbEtoile.setRating(data5.get(position));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,reservatiConf.class);
                intent.putExtra("idchamber",data1.get(position));
                intent.putExtra("idHotel",id_hotel);
                intent.putExtra("prix",data4.get(position));
                intent.putExtra("idUser",user_id);
                intent.putExtra("dateDebut",date_debut);
                intent.putExtra("dateFin",date_fin);
                intent.putExtra("lalitude",dataLalitude.get(position));
                intent.putExtra("longtitude",longtitude.get(position));
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return data1.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView idChmaber,typeChamber,hotelName,prixChamber;
        RatingBar nbEtoile;
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idChmaber = itemView.findViewById(R.id.id_chamber);
            typeChamber = itemView.findViewById(R.id.type_chambre);
            hotelName = itemView.findViewById(R.id.Hname);
            prixChamber = itemView.findViewById(R.id.prixTotal);
            nbEtoile = itemView.findViewById(R.id.ratingChamber);
            mainLayout = itemView.findViewById(R.id.LayoutChamber);
        }
    }
}
