package com.example.projet_hotels;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class MyadapterCommetValider extends RecyclerView.Adapter<MyadapterCommetValider.MyViewHolder> {
    ArrayList<String> data1,data2,data3,data4;

    Context context;
    public MyadapterCommetValider(Context ct , ArrayList<String> s1, ArrayList<String> s2, ArrayList<String> s3,ArrayList<String> s4) {
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
        View view = inflater.inflate(R.layout.row_commentconf,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.Nom.setText(data1.get(position));
        holder.hotelName.setText(data4.get(position));
        holder.commentaire.setText(data2.get(position));
        holder.idComment.setText(data3.get(position));
        holder.accepter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(context)
                        .setTitle("Confirmer l'opération")
                        .setMessage("Vous-êtes sûr d'accepter ce comment?")
                        .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        accpterRefuserComment(data3.get(position),"Valide");
                                        deletArow(position);
                                        Toast.makeText(context, "Opératio fait par succes", Toast.LENGTH_SHORT).show();
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
                        .setMessage("Vous-êtes sûr refuser ce comment?")
                        .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        accpterRefuserComment(data3.get(position),"NonValide");
                                        deletArow(position);
                                        Toast.makeText(context, "Opératio fait par succes", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        )
                        .setNegativeButton("Non",null)
                        .show();
            }
        });
    }

    private void accpterRefuserComment(String idComment,String statut) {
        String URL = Constant.addressIP +"updateComment.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("idComment", idComment);
                data.put("statut", statut);
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
        TextView Nom, commentaire,hotelName, idComment;
        Button accepter,refuser;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Nom =          itemView.findViewById(R.id.textView53);
            commentaire =  itemView.findViewById(R.id.textView60);
            idComment =    itemView.findViewById(R.id.textView102);
            hotelName =    itemView.findViewById(R.id.textView58);
            accepter = itemView.findViewById(R.id.button17);
            refuser = itemView.findViewById(R.id.button16);
        }
    }
    private void deletArow(int position){
        data1.remove(position);
        data2.remove(position);
        data3.remove(position);
        data4.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,data1.size());
    }
}
