package com.example.maintanenceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Feedback extends AppCompatActivity {

    EditText mFeedbackComment;
    Button mfeedbacksubmitbtn;
    FirebaseAuth fAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Feedback.this, MainActivity.class));
            }
        });

        mFeedbackComment = findViewById(R.id.feedbackcomment);
        mfeedbacksubmitbtn = findViewById(R.id.feedbacksubmitbtn);
        fAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Feedback");
        mfeedbacksubmitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    feedback();
            }
        });
    }

    private void feedback() {
        String feedbackcomment = mFeedbackComment.getText().toString().trim();
        String userid = firebaseUser.getUid();
        String email = firebaseUser.getEmail();
        if(!TextUtils.isEmpty(feedbackcomment)) {
            String feedbackid = databaseReference.push().getKey();
            FeedbackData feedbackdata = new FeedbackData(feedbackid,feedbackcomment,userid,email);
            databaseReference.child(feedbackid).setValue(feedbackdata);
            Toast.makeText(this,"Feedback submit successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }else{
            Toast.makeText(this,"Feedback is empty", Toast.LENGTH_LONG).show();
        }
    }

}

