package com.example.projet_hotels;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyadapterChamberHotel extends RecyclerView.Adapter<MyadapterChamberHotel.MyViewHolder> {
    private static final String URL = Constant.addressIP +"modifierStatusC.php";
    private ArrayList<String> data1,data2,data3,data4;
    private Context context;
    private String idhotel,iduser;


    public MyadapterChamberHotel(Context ct,String id,String id_Hotel,ArrayList<String> s1, ArrayList<String> s2, ArrayList<String> s3, ArrayList<String> s4) {
        context = ct;
        iduser = id;
        idhotel = id_Hotel;
        data1 = s1;
        data2 = s2;
        data3 = s3;
        data4 = s4;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_row_chambre_hotel,parent,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.idChmaber.setText(data1.get(position));
        holder.typeChamber.setText(data2.get(position));
        holder.prixChamber.setText(data3.get(position)+" DZD");
        holder.status.setChecked(data4.get(position).equals("service"));
        holder.status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    changeStatus(data1.get(position),"service");
                }
                else {
                    changeStatus(data1.get(position),"horsService");
                }
            }
        });
        holder.editChamber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ModiferChambre.class);
                intent.putExtra("idChamb",data1.get(position));
                intent.putExtra("typeChamber",data2.get(position));
                intent.putExtra("prixChamber",data3.get(position));
                intent.putExtra("idHotel",idhotel);
                intent.putExtra("id_user",iduser);
                context.startActivity(intent);
            }
        });
    }

    private void changeStatus(String s, String service) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("succes")) {
                    Toast.makeText(context, "Statut de la chambre est modifie", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "Erreur! Try again Later", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Erreur de connexion! Try Later", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("idChambre", s);
                data.put("idHotel",idhotel);
                data.put("statut",service);
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
        TextView idChmaber,typeChamber,prixChamber;
        ImageButton editChamber;
        SwitchMaterial status;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idChmaber =   itemView.findViewById(R.id.textView73);
            typeChamber = itemView.findViewById(R.id.textView76);
            status =      itemView.findViewById(R.id.serviceChamber);
            prixChamber = itemView.findViewById(R.id.textView75);
            editChamber = itemView.findViewById(R.id.imageButton7);
        }
    }
}
