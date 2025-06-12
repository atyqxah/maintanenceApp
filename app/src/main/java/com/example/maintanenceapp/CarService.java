package com.example.maintanenceapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class CarService extends AppCompatActivity {

    EditText mservicetype, mmileage;
    Button mcancelserve, mservicebtn;
    boolean[] selectservice;
    ArrayList<Integer>servicelist = new ArrayList<>();
    String[] serviceArray = {"Cabin Filter Replacement [RM70]","General Repair [RM130]","Alignment and Balancing [RM40]",
    "Tyre Replacements [RM60]","Wheel Bearing [RM130]","Oil and Filter Replacement [RM60]","Engine Service [RM200]",
            "Brake Service [RM140]","Timing Belt [RM120]", "Water Pump [RM30]","Aircond Service [RM100]","Mileage Service [RM50]"};
    TextView total;
    int sum = 0;
    //ArrayList<Integer> cost = new ArrayList<>();
    int[] mservecost = {70,130,40,60,130,60,200,140,120,30,100,50};

    FirebaseAuth fAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_service);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CarService.this, MainActivity.class));
            }
        });

        mservicetype = findViewById(R.id.servicetype);
        mmileage = findViewById(R.id.mileage);
        mcancelserve = findViewById(R.id.resetservebtn);
        mservicebtn = findViewById(R.id.servicebtn);

        fAuth = FirebaseAuth.getInstance();
        //firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //databaseReference = FirebaseDatabase.getInstance().getReference("CarService").child(firebaseUser.getUid());;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("CarService");


        mcancelserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mservicetype.setText("");
                total.setText("");
                mmileage.setText("");
            }
        });

        mservicebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carservice();
            }
        });

        selectservice = new boolean[serviceArray.length];
        selectservice = new boolean[mservecost.length];

        mservicetype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        CarService.this
                );
                builder.setTitle("Select Service");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(serviceArray, selectservice,  new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked){
                            servicelist.add(which);
                            sum = sum + mservecost[which];
                            Collections.sort(servicelist);
                        }
                        else{
                            servicelist.remove(which);
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //StringBuilder costbuild = new StringBuilder();
                        //int sum =0;
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int j = 0; j<servicelist.size(); j++){
                            stringBuilder.append(serviceArray[servicelist.get(j)]);

                            if(j != servicelist.size()-0){
                                stringBuilder.append(",");
                                //sum = sum + mservecost[j];
                            }

                            //costbuild.append(mservecost[servicelist.get(j)]);
                            //if(j != servicelist.size()-0){
                                //sum = sum + mservecost[j];
                            //}
                        }
                        mservicetype.setText(stringBuilder.toString());
                        total.setText(String.valueOf(sum));

                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setNeutralButton("CLEAR ALL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int j = 0; j<selectservice.length; j++){
                            selectservice[j] = false;
                            servicelist.clear();
                            sum = 0;
                            mservicetype.setText("");
                        }
                    }
                });
                builder.show();
            }
        });
    }

    private void carservice() {
        String rServiceType = mservicetype.getText().toString().trim();
        String rServiceMileage = mmileage.getText().toString().trim();
        String rServiceCost = total.getText().toString().trim();
        if(!TextUtils.isEmpty(rServiceType) && !TextUtils.isEmpty(rServiceMileage) && !TextUtils.isEmpty(rServiceCost)) {
            String rServiceId = databaseReference.push().getKey();
            //FirebaseUser rUser = fAuth.getCurrentUser();
            //assert rUser != null;
            //String rServiceId = rUser.getUid();
            //databaseReference = FirebaseDatabase.getInstance().getReference("CarService").child(rServiceId);
            //CarServiceData carServiceData = new CarServiceData(rServiceId,rServiceType,rServiceMileage,rServiceCost);
            //databaseReference.child(rServiceId).setValue(carServiceData);
           // Toast.makeText(this,"Service book successfully",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CarService.this, Appointment.class);
            intent.putExtra("ServiceType",rServiceType);
            intent.putExtra("ServiceMileage",rServiceMileage);
            intent.putExtra("ServiceCost",rServiceCost);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this,"Data is empty", Toast.LENGTH_LONG).show();
        }
    }
}