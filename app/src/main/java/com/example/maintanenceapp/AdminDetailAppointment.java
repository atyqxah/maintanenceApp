package com.example.maintanenceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AdminDetailAppointment extends AppCompatActivity {

    TextView date,time,platno,cartype,servicemileage;
    EditText mservicetype, statuss;
    DatabaseReference databaseReference;
    Button deletebtn,updatebtn, cancelbtn, emailbtn;
    String appointmentid;
    //Spinner statuss;
    boolean[] selectservice;
    ArrayList<Integer> servicelist = new ArrayList<>();
    String[] serviceArray = {"Cabin Filter Replacement [RM70]","General Repair [RM130]","Alignment and Balancing [RM40]",
            "Tyre Replacements [RM60]","Wheel Bearing [RM130]","Oil and Filter Replacement [RM60]","Engine Service [RM200]",
            "Brake Service [RM140]","Timing Belt [RM120]", "Water Pump [RM30]","Aircond Service [RM100]","Mileage Service [RM50]"};
    TextView total;
    int sum = 0;
    //ArrayList<Integer> cost = new ArrayList<>();
    int[] mservecost = new int[]{70,130,40,60,130,60,200,140,120,30,100,50};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_appointment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDetailAppointment.this, AppointmentList.class));
            }
        });
       // appointmentid = getIntent().getExtras().get("appointmentid").toString();
        emailbtn = findViewById(R.id.emailusebtn);
        emailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminDetailAppointment.this,EmailUser.class);
                startActivity(i);
                finish();
            }
        });
        date = findViewById(R.id.udate);
        time= findViewById(R.id.utime);
        platno = findViewById(R.id.uplatno);
        //int mstat = statuss.getSelectedItemPosition();
        statuss = findViewById(R.id.statuse);
        //statuss.setOnItemSelectedListener(this);
        //int mstat = statuss.getSelectedItemPosition();
        cancelbtn = findViewById(R.id.resetservebtn);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mservicetype.setText("");
                total.setText("");
            }
        });
        cartype = findViewById(R.id.ucartype);
        selectservice = new boolean[serviceArray.length];
        selectservice = new boolean[mservecost.length];

        mservicetype = findViewById(R.id.servicetype);
        mservicetype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        AdminDetailAppointment.this
                );
                builder.setTitle("Select Service (Price of Goods not Included)");
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
        servicemileage = findViewById(R.id.uservicemileage);
        total = findViewById(R.id.uservicecost);
        //deletebtn = findViewById(R.id.deleteappointment);

        appointmentid = getIntent().getExtras().get("appointmentid").toString();
        databaseReference = FirebaseDatabase.getInstance().getReference("Appointment").child(appointmentid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String AppointmentDate = snapshot.child("appointmentDate").getValue().toString();
                String AppointmentTime = snapshot.child("appointmentTime").getValue().toString();
                String CarRegisterNum = snapshot.child("carRegisterNum").getValue().toString();
                String CarType = snapshot.child("carType").getValue().toString();
                String status = snapshot.child("status").getValue().toString();
                String ServiceType = snapshot.child("serviceType").getValue().toString();
                String ServiceMileage = snapshot.child("serviceMileage").getValue().toString();
                String ServiceCost = snapshot.child("serviceCost").getValue().toString();

                date.setText(AppointmentDate);
                time.setText(AppointmentTime);
                platno.setText(CarRegisterNum);
                cartype.setText(CarType);
                statuss.setText(status);
                mservicetype.setText(ServiceType);
                servicemileage.setText(ServiceMileage);
                total.setText(ServiceCost);

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
                         Log.e(TAG, "onCancelled", databaseError.toException());
                    }
                });
            }
        });*/
        updatebtn = findViewById(R.id.updateappbtn);
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wservicetype = mservicetype.getText().toString();
                String mservicecost = total.getText().toString();
                String mstatus = statuss.getText().toString();
                updateadappt(wservicetype,mservicecost,mstatus);
            }
        });
    }

    private void updateadappt(String wservicetype, String mservicecost, String mstatus) {

        /*appointmentid = getIntent().getExtras().get("appointmentid").toString();
        databaseReference = FirebaseDatabase.getInstance().getReference("Appointment");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot appleSnapshot: dataSnapshot.getChildren()){
                    appleSnapshot.getRef().child("serviceType").setValue(wservicetype);
                    appleSnapshot.getRef().child("serviceCost").setValue(mservicecost);
                }
                Toast.makeText(AdminDetailAppointment.this,"Service Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AdminDetailAppointment.this, AdminDetailAppointment.class));
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        appointmentid = getIntent().getExtras().get("appointmentid").toString();
        //String statuss = status.getSelectedItem().toString();
        databaseReference = FirebaseDatabase.getInstance().getReference("Appointment").child(appointmentid);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("appointmentid",appointmentid);
        hashMap.put("serviceType",wservicetype);
        hashMap.put("serviceCost",mservicecost);
        hashMap.put("status",mstatus);
        databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Intent intent1 = new Intent (AdminDetailAppointment.this, AdminDetailAppointment.class);
                    Bundle data1 = new Bundle();
                    data1.putString("appointmentid",appointmentid);
                    intent1.putExtras(data1);
                    startActivity(intent1);
                    //Intent intent = new Intent(AdminDetailAppointment.this,AdminDetailAppointment.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                    //startActivity(intent);
                }else{
                    Toast.makeText(AdminDetailAppointment.this, "Service updated" + Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}