package com.example.maintanenceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailFeedback extends AppCompatActivity {

    TextView userid, mfeedbackid, pemail, FeedbackComment;
    DatabaseReference databaseReference;
    Button deletebtn;
    String feedbackid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_feedback);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailFeedback.this, FeedbackList.class));
            }
        });
        //userid = findViewById(R.id.uiduser);
        //mfeedbackid = findViewById(R.id.ufeedid);
        pemail = findViewById(R.id.uemail);
        FeedbackComment = findViewById(R.id.usercomment);
        //deletebtn = findViewById(R.id.deletefeedback);

        feedbackid = getIntent().getExtras().get("feedbackId").toString();

        databaseReference = FirebaseDatabase.getInstance().getReference("Feedback").child(feedbackid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //String puserid = snapshot.child("userid").getValue().toString();
                //String pfeedid = snapshot.child("feedbackId").getValue().toString();
                String email = snapshot.child("email").getValue().toString();
                String pfeedbackcomment = snapshot.child("feedbackComment").getValue().toString();

                //userid.setText(puserid);
                //mfeedbackid.setText(pfeedid);
                pemail.setText(email);
                FeedbackComment.setText(pfeedbackcomment);
                return;
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
//                        Log.e(TAG, "onCancelled", databaseError.toException());
                    }
                });
            }
        });*/
    }
}