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

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder> {

    private Context context;


    ArrayList<FeedbackData> list;
    public FeedbackAdapter(ArrayList<FeedbackData> list){
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feedbackitemlist, viewGroup, false);

        context = viewGroup.getContext();

        return new MyViewHolder(view);
    }

    public FeedbackAdapter(Context c) {
        this.context = c;

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView email;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //AppointmentId = itemView.findViewById(R.id.ansappid);
            email = itemView.findViewById(R.id.ansfeedname);


        }
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        //myViewHolder.AppointmentId.setText(list.get(i).getAppointmentId());
        myViewHolder.email.setText(list.get(i).getEmail());


        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context,DetailFeedback.class);
                intent.putExtra("feedbackId",list.get(i).getFeedbackId());

                context.startActivity(intent);
            }
        });



    }


}



/*package com.example.maintanenceapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder> {

    Context context;
    ArrayList<FeedbackData> feedbacklist;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


    public FeedbackAdapter(Context context, ArrayList<FeedbackData> feedbacklist) {
        this.context = context;
        this.feedbacklist = feedbacklist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.feedbackitemlist,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        FeedbackData feedbackData = feedbacklist.get(position);
        holder.FeedbackId.setText(feedbackData.getFeedbackId());
        holder.FeedbackComment.setText(feedbackData.getFeedbackComment());

        holder.deletefeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //holder.FeedbackId.setText(feedbackData.getFeedbackId());
                        databaseReference.child("Feedback").child(feedbackData.getFeedbackId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(context,"Successfully Deleted",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context,"Deleted Unsuccessful",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setMessage("Do you want to delete?");
                builder.show();
            }
        });
       // HashMap<String,Object> hashMap = new HashMap<>();
        //hashMap.put("FeedbackId",feedbacklist.get(position).getFeedbackId());
       // hashMap.put("FeedbackComment",feedbacklist.get(position).getFeedbackComment());

    }

    @Override
    public int getItemCount() {
        return feedbacklist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView FeedbackId, FeedbackComment;
        ImageView deletefeedback;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            FeedbackId = itemView.findViewById(R.id.ansfeedid);
            FeedbackComment = itemView.findViewById(R.id.ansfeedback);
            deletefeedback = itemView.findViewById(R.id.deletefeedback);
        }
    }
}*/
