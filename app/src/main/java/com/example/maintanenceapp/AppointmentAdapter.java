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

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MyViewHolder> {

    private Context context;


    ArrayList<Deal> list;
    public AppointmentAdapter(ArrayList<Deal> list){
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.appointmentitemlist, viewGroup, false);

        context = viewGroup.getContext();

        return new MyViewHolder(view);
    }

    public AppointmentAdapter(Context c) {
        this.context = c;

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView email, AppointmentTime, AppointmentDate, CarRegisterNumber, CarType;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //fullname = itemView.findViewById(R.id.ansaname);
            email = itemView.findViewById(R.id.ansemaila);
            AppointmentTime = itemView.findViewById(R.id.anstime);
            AppointmentDate = itemView.findViewById(R.id.ansdate);
            CarRegisterNumber = itemView.findViewById(R.id.ansregnum);
            CarType = itemView.findViewById(R.id.anscartype);


        }
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        //myViewHolder.fullname.setText(list.get(i).getFullname());
        myViewHolder.email.setText(list.get(i).getEmail());
        myViewHolder.AppointmentTime.setText(list.get(i).getAppointmentTime());
        myViewHolder.AppointmentDate.setText(list.get(i).getAppointmentDate());
        myViewHolder.CarRegisterNumber.setText(list.get(i).getCarRegisterNum());
        myViewHolder.CarType.setText(list.get(i).getCarType());

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context,AdminDetailAppointment.class);
                intent.putExtra("appointmentid",list.get(i).getAppointmentId());

                context.startActivity(intent);
            }
        });



    }

}



/*package com.example.maintanenceapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MyAppointmentHolder> {

    Context mcontext;
    ArrayList<AppointmentData> appointmentlist;

    public AppointmentAdapter(Context mcontext, ArrayList<AppointmentData> appointmentlist) {
        this.mcontext = mcontext;
        this.appointmentlist = appointmentlist;
    }

    @NonNull
    @Override
    public MyAppointmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View k = LayoutInflater.from(mcontext).inflate(R.layout.appointmentitemlist,parent,false);
        return new MyAppointmentHolder(k);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAppointmentHolder holder, int position) {
        AppointmentData appointmentData = appointmentlist.get(position);
        holder.AppointmentId.setText(appointmentData.getAppointmentId());
        holder.AppointmentTime.setText(appointmentData.getAppointmentTime());
        holder.AppointmentDate.setText(appointmentData.getAppointmentDate());
        holder.CarRegisterNumber.setText(appointmentData.getCarRegisterNum());
        holder.CarType.setText(appointmentData.getCarType());
    }

    @Override
    public int getItemCount() {
        return appointmentlist.size();
    }

    public static class MyAppointmentHolder extends RecyclerView.ViewHolder{

        TextView AppointmentId, AppointmentTime, AppointmentDate, CarRegisterNumber, CarType;
        public MyAppointmentHolder(@NonNull View itemView) {
            super(itemView);

            AppointmentId = itemView.findViewById(R.id.ansappid);
            AppointmentTime = itemView.findViewById(R.id.anstime);
            AppointmentDate = itemView.findViewById(R.id.ansdate);
            CarRegisterNumber = itemView.findViewById(R.id.ansregnum);
            CarType = itemView.findViewById(R.id.anscartype);
        }
    }


}*/
