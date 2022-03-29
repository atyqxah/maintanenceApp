package com.example.maintanenceapp;



import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.MyViewHolder> {

    private Context context;


    ArrayList<Deal> list;
    public AdapterClass(ArrayList<Deal> list){
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ownappointmentlist, viewGroup, false);

        context = viewGroup.getContext();

        return new MyViewHolder(view);
    }

    public AdapterClass(Context c) {
        this.context = c;

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView AppointmentId, AppointmentTime, AppointmentDate, CarRegisterNumber, CarType;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //AppointmentId = itemView.findViewById(R.id.ansappid);
            AppointmentTime = itemView.findViewById(R.id.anstime);
            AppointmentDate = itemView.findViewById(R.id.ansdate);
            CarRegisterNumber = itemView.findViewById(R.id.ansregnum);
            CarType = itemView.findViewById(R.id.anscartype);


        }
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        //myViewHolder.AppointmentId.setText(list.get(i).getAppointmentId());
        myViewHolder.AppointmentTime.setText(list.get(i).getAppointmentTime());
        myViewHolder.AppointmentDate.setText(list.get(i).getAppointmentDate());
        myViewHolder.CarRegisterNumber.setText(list.get(i).getCarRegisterNum());
        myViewHolder.CarType.setText(list.get(i).getCarType());

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context,DetailAppointment.class);
                intent.putExtra("appointmentid",list.get(i).getAppointmentId());

                context.startActivity(intent);
            }
        });



    }


}

