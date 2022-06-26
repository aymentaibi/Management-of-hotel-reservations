package com.example.projet_hotels;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyadapterReservationHotel extends RecyclerView.Adapter<MyadapterReservationHotel.MyViewHolder> {
    private final ArrayList<String> data1;
    private final ArrayList<String> data2;
    private final ArrayList<String> data3;
    private final ArrayList<String> data4;
    private final ArrayList<String> data5;
    private final Context context;
    private final String status;
    private final String url=Constant.addressIP +"modiferStatusReservation.php";

    public MyadapterReservationHotel(Context ct, ArrayList<String> s1, ArrayList<String> s2, ArrayList<String> s3, ArrayList<String> s4,ArrayList<String> s5,String statut) {
        context = ct;
        data1 = s1;
        data2 = s2;
        data3 = s3;
        data4 = s4;
        data5 = s5;
        status = statut;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_reservation_hotel,parent,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.idChmaber.setText(data4.get(position));
        holder.nomPrenom.setText(data5.get(position));
        holder.checkin.setText(data2.get(position));
        holder.checkOut.setText(data3.get(position));
        holder.reservationID.setText(data1.get(position));
        if(status.equals("En Attente")){
            holder.accpt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new MaterialAlertDialogBuilder(context)
                            .setTitle("Confirmer l'opération")
                            .setMessage("Vous-êtes sûr d'accepter ce reservation?")
                            .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            updateStatusCh("En Cours",data1.get(position));
                                            deletArow(position);
                                        }
                                    }
                            )
                            .setNegativeButton("Non",null)
                            .show();
                }
            });
            holder.refuser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new MaterialAlertDialogBuilder(context)
                            .setTitle("Confirmer l'opération")
                            .setMessage("Vous-êtes sûr de refuser ce reservation?")
                            .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            updateStatusCh("Refuser", data1.get(position));
                                            deletArow(position);
                                        }
                                    }
                            )
                            .setNegativeButton("Non",null)
                            .show();
                }
            });
        }
        else {
            holder.accpt.setVisibility(View.INVISIBLE);
            holder.refuser.setVisibility(View.INVISIBLE);
            holder.accpt.setEnabled(false);
            holder.refuser.setEnabled(false);
        }
    }

    private void updateStatusCh(String en_cours, String s) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context,"Opératio fait par succes",Toast.LENGTH_LONG).show();
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
                data.put("statut", en_cours);
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

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView idChmaber,nomPrenom,checkin,checkOut,reservationID;
        ImageButton accpt,refuser;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idChmaber = itemView.findViewById(R.id.textView90);
            nomPrenom = itemView.findViewById(R.id.textView84);
            checkin = itemView.findViewById(R.id.textView86);
            checkOut = itemView.findViewById(R.id.textView88);
            reservationID = itemView.findViewById(R.id.textView80);
            accpt = itemView.findViewById(R.id.imageButton4);
            refuser = itemView.findViewById(R.id.imageButton3);

        }
    }

    private void deletArow(int position){
        data1.remove(position);
        data2.remove(position);
        data3.remove(position);
        data4.remove(position);
        data5.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,data1.size());
    }
}
