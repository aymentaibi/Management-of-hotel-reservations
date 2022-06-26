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

public class MyadapterCommet extends RecyclerView.Adapter<MyadapterCommet.MyViewHolder> {
    ArrayList<String> data1,data2;

    Context context;
    public MyadapterCommet(Context ct , ArrayList<String> s1, ArrayList<String> s2) {
        context = ct;
        data1 = s1;
        data2 = s2;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_comment,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Nom.setText(data1.get(position));
        holder.commentaire.setText(data2.get(position));

    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Nom, commentaire;
        RatingBar nbEtoile;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Nom = itemView.findViewById(R.id.textView47);
            commentaire = itemView.findViewById(R.id.textView48);

        }
    }
}
