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


public class Login extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button mLoginBtn;
    TextView mRegisterHere, mforgetpassword, madmin;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mEmail = findViewById(R.id.EmailAdmin);
        mPassword = findViewById(R.id.PasswordAdmin);
        progressBar = findViewById(R.id.progressBar);
        mLoginBtn = findViewById(R.id.adLoginBtn);
        mRegisterHere = findViewById(R.id.RegisterHere);
        //madmin = findViewById(R.id.admin);
        mforgetpassword = findViewById(R.id.forgetpassword);
        fAuth = FirebaseAuth.getInstance();


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required");
                }
                else if (TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required");
                }
                else {
                    login(email,password);
                }
            }
        });

        mRegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
        mforgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,ForgetPasswordActivity.class));
            }
        });
       /* madmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,AdminLogin.class));
            }
        });*/
    }

    private void login(String email, String password) {
        //String ADemail = mEmail.getText().toString();
        //String ADpassword = mPassword.getText().toString();
        //if(ADemail.equals("admin@gmail.com") && ADpassword.equals("adminemployee")){
          //  Toast.makeText(this, "Login Successfully",Toast.LENGTH_LONG).show();
          //  startActivity(new Intent(getApplicationContext(),AdminMenu.class));
          //  finish();
       // }
       // else {
            progressBar.setVisibility(View.VISIBLE);
            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {

                        if(email.equals("admin@gmail.com") && password.equals("adminemployee")){
                            startActivity(new Intent(getApplicationContext(),AdminMenu.class));
                            finish();
                        }else{

                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Toast.makeText(Login.this, "Login successfully", Toast.LENGTH_SHORT).show();
                        finish();}

                    }else{
                    Toast.makeText(Login.this, "Password and Email is not entered correctly" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        //}
    }

}