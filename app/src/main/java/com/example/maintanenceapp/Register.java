package com.example.maintanenceapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class Register extends AppCompatActivity {
    EditText mFullName, mNRIC, mPhoneNumber, mEmail, mPassword;
    RadioGroup mGender;
    Button mRegisterbtn;
    TextView mLoginHere;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,Login.class));
            }
        });

        mFullName = findViewById(R.id.FullName);
        mNRIC = findViewById(R.id.NRIC);
        mPhoneNumber = findViewById(R.id.PhoneNumber);
        mEmail = findViewById(R.id.EmailAdmin);
        mPassword = findViewById(R.id.PasswordAdmin);
        mGender = findViewById(R.id.Gender);
        mRegisterbtn = findViewById(R.id.userregisterbtn);
        mLoginHere = findViewById(R.id.LoginHere);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        mRegisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                String fullname = mFullName.getText().toString();
                String nric = mNRIC.getText().toString();
                String phonenumber = mPhoneNumber.getText().toString();
                int GENDER = mGender.getCheckedRadioButtonId();
                RadioButton selected_gender = mGender.findViewById(GENDER);

                if (selected_gender == null){
                    Toast.makeText(Register.this, "Select Gender", Toast.LENGTH_SHORT).show();
                }
                else{
                    final String gender = selected_gender.getText().toString();
                    if (TextUtils.isEmpty(fullname)){
                        mFullName.setError("Full name is required");
                    }
                    else if (TextUtils.isEmpty(nric)){
                        mNRIC.setError("NRIC is required");
                    }
                    else if(nric.length() != 12){
                        mNRIC.setError("Enter the correct NRIC number");
                    }
                    else if (TextUtils.isEmpty(phonenumber)){
                        mPhoneNumber.setError("Phone number is required");
                    }
                    else if (TextUtils.isEmpty(email)){
                        mEmail.setError("Email is required");
                    }
                    else if (TextUtils.isEmpty(password)){
                        mPassword.setError("Password is required");
                    }
                    else{
                        register(fullname,nric,phonenumber,gender,email,password);
                    }
                }


            }
        });

        mLoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }


    private void register(String fullname, String nric, String phonenumber, String gender, String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser rUser = fAuth.getCurrentUser();
                    assert rUser != null;
                    String userid = rUser.getUid();
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("userid",userid);
                    hashMap.put("fullname",fullname);
                    hashMap.put("nric",nric);
                    hashMap.put("phonenumber",phonenumber);
                    hashMap.put("gender",gender);
                    hashMap.put("email",email);
                    hashMap.put("imageUrl","default");
                    databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()){
                                Intent intent = new Intent(Register.this,Login.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }else{
                                Toast.makeText(Register.this, "User Created" + Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(Register.this, "Error" + Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}

