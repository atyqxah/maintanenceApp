package com.example.maintanenceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Objects;

public class ListAppointmentMainActivity extends AppCompatActivity {

    DatabaseReference ref;
    ArrayList<Deal> list;
    RecyclerView recyclerView;
    SearchView searchView;
    Button home;

    private String authData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_appointment_main);
        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListAppointmentMainActivity.this,MainActivity.class));
                finish();
            }
        });


        authData = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ref = FirebaseDatabase.getInstance().getReference("Appointment");

        recyclerView = findViewById(R.id.appointmentlist);


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
                            if(authData.equals(ds.getValue(Deal.class).getUserid())){
                                list.add(ds.getValue(Deal.class));
                            }
                        }
                    }
                    AdapterClass adapterClass = new AdapterClass(list);
                    recyclerView.setAdapter(adapterClass);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ListAppointmentMainActivity.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
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

      ArrayList<Deal> myList = new ArrayList<>();
       for (Deal object : list)
       {
          if(object.getAppointmentDate().toLowerCase().contains(str.toLowerCase()))
            {
                myList.add(object);
           }
       }
       AdapterClass adapterClass = new AdapterClass(myList);
       recyclerView.setAdapter(adapterClass);
  }
}