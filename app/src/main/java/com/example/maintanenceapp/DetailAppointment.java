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

public class DetailAppointment extends AppCompatActivity {

    TextView date,time,platno,cartype,servicetype,servicemileage,servicecost, status;
    DatabaseReference databaseReference;
    //Button deletebtn;
    String appointmentid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_appointment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailAppointment.this, ListAppointmentMainActivity.class));
            }
        });
        date = findViewById(R.id.udate);
        time= findViewById(R.id.utime);
        platno = findViewById(R.id.uplatno);
        cartype = findViewById(R.id.ucartype);
        servicetype = findViewById(R.id.uservicetype);
        servicemileage = findViewById(R.id.uservicemileage);
        servicecost = findViewById(R.id.uservicecost);
        status = findViewById(R.id.appstatus);
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
                String ServiceType = snapshot.child("serviceType").getValue().toString();
                String ServiceMileage = snapshot.child("serviceMileage").getValue().toString();
                String ServiceCost = snapshot.child("serviceCost").getValue().toString();
                String statuss = snapshot.child("status").getValue().toString();

                date.setText(AppointmentDate);
                time.setText(AppointmentTime);
                platno.setText(CarRegisterNum);
                cartype.setText(CarType);
                servicetype.setText(ServiceType);
                servicemileage.setText(ServiceMileage);
                servicecost.setText(ServiceCost);
                status.setText(statuss);


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
//                        Log.e(TAG, "onCancelled", databaseError.toException());
                    }
                });
            }
        });*/
    }
}