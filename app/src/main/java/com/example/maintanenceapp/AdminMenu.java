package com.example.maintanenceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

public class AdminMenu extends AppCompatActivity {

    Button madminlogout;
    LinearLayout mfeedbackadminbtn, mappointmentadminbtn, mcarserviceadminbtn, musersadminbtn;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
        madminlogout = findViewById(R.id.adminlogout);
        madminlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(AdminMenu.this,Login.class));
                    finish();
            }
        });
        mfeedbackadminbtn = (LinearLayout) findViewById(R.id.feedbackadminbtn);
        mfeedbackadminbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminMenu.this,FeedbackList.class);
                startActivity(i);
                finish();
            }
        });

        mappointmentadminbtn = (LinearLayout) findViewById(R.id.appointmentadminbtn);
        mappointmentadminbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminMenu.this,AppointmentList.class);
                startActivity(i);
                finish();
            }
        });
    }

}