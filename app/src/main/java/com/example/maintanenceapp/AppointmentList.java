package com.example.maintanenceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



import com.example.maintanenceapp.AdapterClass;
import com.example.maintanenceapp.Deal;
import com.example.maintanenceapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AppointmentList extends AppCompatActivity {

    DatabaseReference ref;
    ArrayList<Deal> list;
    RecyclerView recyclerView;
    SearchView searchView;
    Button home;

    private String authData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);
        home = findViewById(R.id.apphome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppointmentList.this,AdminMenu.class));
                finish();
            }
        });

        authData = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ref = FirebaseDatabase.getInstance().getReference("Appointment");

        recyclerView = findViewById(R.id.adminappointmentlist);


    }

    @Override
    protected void onStart() {
        super.onStart();

        if(ref != null){
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                    if(dataSnapshot.exists())
                    {
                        list = new ArrayList<>();
                        for(DataSnapshot ds: dataSnapshot.getChildren())
                        {
                            //if(authData.equals(ds.getValue(Deal.class).getUserid()) ) {
                            list.add(ds.getValue(Deal.class));
                             //}
                        }
                    }
                    AppointmentAdapter appointmentAdapter = new AppointmentAdapter(list);
                    recyclerView.setAdapter(appointmentAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(AppointmentList.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
        if(searchView != null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });
        }
    }

    private void search(String str){

        ArrayList<Deal> list = new ArrayList<>();
        for (Deal object : list)
        {
            if(object.getAppointmentDate().toLowerCase().contains(str.toLowerCase()))
            {
                list.add(object);
            }
        }
        AppointmentAdapter appointmentAdapter = new AppointmentAdapter(list);
        recyclerView.setAdapter(appointmentAdapter);
    }
}

/*package com.example.maintanenceapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AppointmentList extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    AppointmentAdapter appointmentAdapter;
    ArrayList<AppointmentData> appointmentlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppointmentList.this, AdminMenu.class));
            }
        });

        recyclerView = findViewById(R.id.appointmentlist);
        databaseReference = FirebaseDatabase.getInstance().getReference("Appointment");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        appointmentlist = new ArrayList<>();
        appointmentAdapter = new AppointmentAdapter(this,appointmentlist);
        recyclerView.setAdapter(appointmentAdapter);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for(DataSnapshot datasnapshot : snapshot.getChildren()){

                    appointmentlist.add(datasnapshot.getValue(AppointmentData.class));
                }

                appointmentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}*/