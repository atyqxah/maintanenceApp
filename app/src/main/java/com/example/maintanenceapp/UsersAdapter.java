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

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {

    private Context context;


    ArrayList<UsersData> list;
    public UsersAdapter(ArrayList<UsersData> list){
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.usersitemlist, viewGroup, false);

        context = viewGroup.getContext();

        return new MyViewHolder(view);
    }

    public UsersAdapter(Context c) {
        this.context = c;

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView userid, fullname, nric, phonenumber, gender, email;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            email = itemView.findViewById(R.id.ansuserid);
            fullname = itemView.findViewById(R.id.ansname);
            nric = itemView.findViewById(R.id.ansnric);

        }
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        myViewHolder.email.setText(list.get(i).getEmail());
        myViewHolder.fullname.setText(list.get(i).getFullname());
        myViewHolder.nric.setText(list.get(i).getNric());


        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context,DetailUser.class);
                intent.putExtra("userid",list.get(i).getUserid());

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

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyUsersHolder> {

    Context context;
    ArrayList<UsersData> userslist;

    public UsersAdapter(Context context, ArrayList<UsersData> userslist) {
        this.context = context;
        this.userslist = userslist;
    }

    @NonNull
    @Override
    public MyUsersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.usersitemlist,parent,false);
        return new UsersAdapter.MyUsersHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyUsersHolder holder, int position) {

        UsersData usersData = userslist.get(position);
        holder.userid.setText(usersData.getUserid());
        holder.fullname.setText(usersData.getFullname());
        holder.nric.setText(usersData.getNric());
        holder.phonenumber.setText(usersData.getPhonenumber());
        holder.gender.setText(usersData.getGender());
        holder.email.setText(usersData.getEmail());

    }

    @Override
    public int getItemCount() {
        return userslist.size();
    }

    public static class MyUsersHolder extends RecyclerView.ViewHolder{

        TextView userid, fullname, nric, phonenumber, gender, email;

        public MyUsersHolder(@NonNull View itemView) {
            super(itemView);

            userid = itemView.findViewById(R.id.ansuserid);
            fullname = itemView.findViewById(R.id.ansname);
            nric = itemView.findViewById(R.id.ansnric);
            phonenumber = itemView.findViewById(R.id.ansphone);
            gender = itemView.findViewById(R.id.ansgender);
            email = itemView.findViewById(R.id.ansemail);

        }
    }
}*/
