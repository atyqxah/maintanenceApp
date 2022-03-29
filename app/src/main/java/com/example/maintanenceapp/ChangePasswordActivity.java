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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {
    
    private EditText mCurrPassword, mNewPassword, mConfirmPass;
    private FirebaseUser firebaseUser;
    private ProgressBar progressBar;
    private FirebaseAuth fAuth;
    private Button mchangepassbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Change Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChangePasswordActivity.this,MainActivity.class));
            }
        });
        fAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mCurrPassword = findViewById(R.id.CurrPassword);
        mNewPassword = findViewById(R.id.NewPassword);
        mConfirmPass = findViewById(R.id.ConfirmPass);
        progressBar = findViewById(R.id.progressBar);
        mchangepassbtn = findViewById(R.id.changepassbtn);
        mchangepassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtCurrPass = mCurrPassword.getText().toString();
                String txtNewPass = mNewPassword.getText().toString();
                String txtConPass = mConfirmPass.getText().toString();

                if (txtCurrPass == null){
                    Toast.makeText(ChangePasswordActivity.this, "Please enter this field", Toast.LENGTH_SHORT).show();
                }
                else if (txtNewPass == null){
                    Toast.makeText(ChangePasswordActivity.this, "Please enter this field", Toast.LENGTH_SHORT).show();
                }
                else if (txtConPass == null){
                    Toast.makeText(ChangePasswordActivity.this, "Please enter this field", Toast.LENGTH_SHORT).show();
                }
                else if (! txtConPass.equals(txtNewPass)){
                    Toast.makeText(ChangePasswordActivity.this, "Thr confirm password did not match with the new password", Toast.LENGTH_SHORT).show();
                }
                else{
                    changePassword(txtCurrPass,txtNewPass);
                }
            }
        });
    }

    private void changePassword(String txtCurrPass, String txtNewPass) {
        progressBar.setVisibility(View.VISIBLE);
        AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail(),txtCurrPass);
        firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    firebaseUser.updatePassword(txtNewPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                fAuth.signOut();
                                Intent intent = new Intent(ChangePasswordActivity.this,Login.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(ChangePasswordActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ChangePasswordActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}