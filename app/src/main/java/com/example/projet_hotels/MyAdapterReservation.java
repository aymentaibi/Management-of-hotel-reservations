package com.example.projet_hotels;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAdapterReservation extends RecyclerView.Adapter<MyAdapterReservation.MyViewHolder>{

    private final ArrayList<String> data1;
    private final ArrayList<String> data2;
    private final ArrayList<String> data3;
    private final ArrayList<String> data4;
    private final ArrayList<String> data5;
    private final ArrayList<String> data6;
    private final ArrayList<String> data7;
    private final Context context;
    private final ArrayList<String> data8;
    private final ArrayList<String> data9;
    private final ArrayList<String> status;
    private final String url=Constant.addressIP +"modiferStatusReservation.php";

    public MyAdapterReservation(Context ct, ArrayList<String> s1, ArrayList<String> s2, ArrayList<String> s3, ArrayList<String> s4, ArrayList<String> s5, ArrayList<String> s6, ArrayList<String> s7,ArrayList<String> s8,ArrayList<String> s9,ArrayList<String> s10) {
        context = ct;
        data1 = s1;
        data2 = s2;
        data3 = s3;
        data4 = s4;
        data5 = s5;
        data6 = s6;
        data7 = s7;
        data8 = s8;
        data9 = s9;
        status = s10;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_reservation,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String periodeField;
        holder.nResarvation.setText(data1.get(position));
        holder.hotelName.setText(data2.get(position));
        holder.typeChamber.setText(data3.get(position));
        holder.addressHotel.setText(data4.get(position));
        holder.idChamber.setText(data5.get(position));
        periodeField = data6.get(position) +" JUSQU'A " + data7.get(position);
        holder.periodeReservation.setText(periodeField);
        holder.status.setText(status.get(position));
        switch (status.get(position)){
            case "En Attente":
                holder.status.setTextColor(Color.parseColor("#FF001CD4"));
                holder.suppr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        annuleReseravation(data1.get(position));
                        status.set(position, "Annuler");
                        notifyItemChanged(position);
                    }
                });

                break;
            case "Refuser":
                holder.status.setTextColor(Color.parseColor("#FFDC0B0B"));
                holder.suppr.setVisibility(View.INVISIBLE);
                holder.suppr.setEnabled(false);
                break;
            case "En Cours":
                holder.status.setTextColor(Color.parseColor("#FFFFFB22"));
                holder.suppr.setVisibility(View.INVISIBLE);
                holder.suppr.setEnabled(false);
                break;
            case "Terminer":
                holder.status.setTextColor(Color.parseColor("#FF0F4709"));
                holder.suppr.setEnabled(false);
                holder.suppr.setVisibility(View.INVISIBLE);
                break;
        }
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, itineraire.class);
                intent.putExtra("longtitude",data8.get(position));
                intent.putExtra("latitude",data9.get(position));
                intent.putExtra("hotalName",data2.get(position));
                context.startActivity(intent);
            }
        });

    }
    private void annuleReseravation(String s) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context,"Opération fait par succes",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Erreur De connexion,Try Later !",Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("idReservation", s);
                data.put("statut", "Annuler");
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView status,nResarvation,typeChamber,hotelName,addressHotel,periodeReservation,idChamber;
        Button details;
        ImageButton suppr;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nResarvation = itemView.findViewById(R.id.nReservation);
            status = itemView.findViewById(R.id.textView82);
            typeChamber = itemView.findViewById(R.id.res_typeCh);
            hotelName = itemView.findViewById(R.id.nom_hotel_row);
            addressHotel = itemView.findViewById(R.id.hotelAdressRow);
            periodeReservation = itemView.findViewById(R.id.pariode_row_reservation);
            idChamber = itemView.findViewById(R.id.row_resrv_cham);
            details = itemView.findViewById(R.id.button3);
            suppr = itemView.findViewById(R.id.imageButton2);
        }
    }





}
