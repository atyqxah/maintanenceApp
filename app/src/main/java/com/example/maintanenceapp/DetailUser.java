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

public class DetailUser extends AppCompatActivity {

    TextView fullname,nric, phonenumber, gender, email;
    DatabaseReference databaseReference;
    Button deletebtn;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailUser.this, UsersList.class));
            }
        });
        fullname = findViewById(R.id.uname);
        nric= findViewById(R.id.unric);
        phonenumber = findViewById(R.id.uphone);
        gender = findViewById(R.id.ugender);
        email = findViewById(R.id.uemail);
        deletebtn = findViewById(R.id.deleteuser);

        userid = getIntent().getExtras().get("userid").toString();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String mname = snapshot.child("fullname").getValue().toString();
                String mnric = snapshot.child("nric").getValue().toString();
                String mphone = snapshot.child("phonenumber").getValue().toString();
                String mgender = snapshot.child("gender").getValue().toString();
                String memail = snapshot.child("email").getValue().toString();



                fullname.setText(mname);
                nric.setText(mnric);
                phonenumber.setText(mphone);
                gender.setText(mgender);
                email.setText(memail);

            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                            startActivity(new Intent(getApplicationContext(),UsersList.class));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
//                        Log.e(TAG, "onCancelled", databaseError.toException());
                    }
                });
            }
        });
    }
}