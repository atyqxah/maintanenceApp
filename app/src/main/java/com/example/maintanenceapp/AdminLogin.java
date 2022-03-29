package com.example.maintanenceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminLogin extends AppCompatActivity {
    TextView mbackuser;
    EditText madEmail, madPassword;
    ProgressBar progressBar;
    Button madLoginBtn;
    FirebaseAuth fAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        madEmail = findViewById(R.id.EmailAdmin);
        madPassword = findViewById(R.id.PasswordAdmin);
        progressBar = findViewById(R.id.progressBar);
        madLoginBtn = findViewById(R.id.adLoginBtn);
        mbackuser = findViewById(R.id.backuser);
        fAuth = FirebaseAuth.getInstance();

        mbackuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
        madLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ADemail = madEmail.getText().toString();
                String ADpassword = madPassword.getText().toString();

                if (TextUtils.isEmpty(ADemail)){
                    madEmail.setError("Email is required");
                }
                else if (TextUtils.isEmpty(ADpassword)){
                    madPassword.setError("Password is required");
                }
                else {
                    adminlogin();
                }
            }
        });
    }

    private void adminlogin() {
        String ADemail = madEmail.getText().toString();
        String ADpassword = madPassword.getText().toString();
        databaseReference = FirebaseDatabase.getInstance().getReference("Admin");
        if(ADemail.equals("admin@gmail.com") && ADpassword.equals("adminemployee")){
            Toast.makeText(this, "Login Successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(),AdminMenu.class));
            finish();
        }
        else{
            Toast.makeText(this, "Login Unsuccessfully",Toast.LENGTH_LONG).show();
        }
    }

}