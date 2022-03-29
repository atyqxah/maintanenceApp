package com.example.maintanenceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

public class UpdateUserActivity extends AppCompatActivity {

    private EditText newFullName, newNRIC, newPhoneNumber;
    private RadioGroup mGender;
    private RadioButton mFemale, mMale;
    private Button muserupdatebtn;
    private DatabaseReference databaseReference;
    private ProgressBar progressBar;
    private FirebaseUser firebaseUser;
    private FirebaseAuth fAuth;
    private UsersData usersData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update User Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateUserActivity.this, MainActivity.class));
            }
        });

        fAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        newFullName = findViewById(R.id.FullName);
        newNRIC = findViewById(R.id.NRIC);
        newPhoneNumber = findViewById(R.id.PhoneNumber);
        mGender = findViewById(R.id.Gender);
        mFemale = findViewById(R.id.Female);
        mMale = findViewById(R.id.Male);
        progressBar = findViewById(R.id.progressBar);
        muserupdatebtn = findViewById(R.id.userupdatebtn);
        muserupdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = newFullName.getText().toString();
                String nric = newNRIC.getText().toString();
                String phonenumber = newPhoneNumber.getText().toString();
                int GENDER = mGender.getCheckedRadioButtonId();
                RadioButton selected_gender = mGender.findViewById(GENDER);
                final String gender = selected_gender.getText().toString();
                UpdateUSER(fullname,nric,phonenumber,gender);
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                usersData = datasnapshot.getValue(UsersData.class);
                assert usersData != null;
                newFullName.setText(usersData.getFullname());
                newNRIC.setText(usersData.getNric());
                newPhoneNumber.setText(usersData.getPhonenumber());

                String gender = datasnapshot.child("gender").getValue().toString();
                if (gender.equalsIgnoreCase("Male")){
                   mMale.setChecked(true);
                }
                else if (gender.equalsIgnoreCase("Female")){
                    mFemale.setChecked(true);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateUserActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UpdateUSER(String fullname, String nric, String phonenumber, String gender) {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseUser rUser = fAuth.getCurrentUser();
        assert rUser != null;
        String userid = rUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("userid",userid);
        hashMap.put("fullname",fullname);
        hashMap.put("nric",nric);
        hashMap.put("phonenumber",phonenumber);
        hashMap.put("gender",gender);
        databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    Intent intent = new Intent(UpdateUserActivity.this,UpdateUserActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{
                    Toast.makeText(UpdateUserActivity.this, "User Created" + Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}