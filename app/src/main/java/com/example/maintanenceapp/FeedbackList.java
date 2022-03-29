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

public class FeedbackList extends AppCompatActivity {

    DatabaseReference ref;
    ArrayList<FeedbackData> list;
    RecyclerView recyclerView;
    SearchView searchView;
    Button fhome;

    private String authData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_list);
        fhome = findViewById(R.id.home);
        fhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FeedbackList.this,AdminMenu.class));
                finish();
            }
        });

        authData = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ref = FirebaseDatabase.getInstance().getReference("Feedback");

        recyclerView = findViewById(R.id.feedbacklist);


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
                            // if(authData.equals(ds.getValue(Deal.class).getUserid()) ) {
                            list.add(ds.getValue(FeedbackData.class));
                            // }
                        }
                    }
                    FeedbackAdapter feedbackAdapter = new FeedbackAdapter(list);
                    recyclerView.setAdapter(feedbackAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(FeedbackList.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
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

        ArrayList<FeedbackData> myList = new ArrayList<>();
        for (FeedbackData object : list)
        {
            if(object.getUserid().toLowerCase().contains(str.toLowerCase()))
            {
                myList.add(object);
            }
        }
        FeedbackAdapter feedbackAdapter = new FeedbackAdapter(myList);
        recyclerView.setAdapter(feedbackAdapter);
    }
}

/* package com.example.maintanenceapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FeedbackList extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FeedbackAdapter feedbackAdapter;
    ArrayList<FeedbackData> feedbacklist;
    EditText mFeedbackComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FeedbackList.this, AdminMenu.class));
            }
        });

        recyclerView = findViewById(R.id.feedbacklist);
        databaseReference = FirebaseDatabase.getInstance().getReference("Feedback");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        feedbacklist = new ArrayList<>();
        feedbackAdapter = new FeedbackAdapter(this,feedbacklist);
        recyclerView.setAdapter(feedbackAdapter);
        mFeedbackComment = findViewById(R.id.feedbackcomment);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    String feedbackid = databaseReference.push().getKey();
                    FeedbackData feedbackdata = new FeedbackData();
                    databaseReference.child(feedbackid).setValue(feedbackdata);
                    feedbacklist.add(snapshot.getValue(FeedbackData.class));
                }

                feedbackAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //databaseReference.addValueEventListener(new ValueEventListener() {
            //@Override
            //public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    // String df = dataSnapshot.getKey();
                    // feedbackData = snapshot.getValue(FeedbackData.class);
                    //feedbacklist.add(FeedbackData);
                    //feedbacklist.add(snapshot.getValue(FeedbackData.class));
               // }

                //feedbackAdapter.notifyDataSetChanged();
           // }

           // @Override
           // public void onCancelled(@NonNull DatabaseError error) {

            //}
        //});
    }
}*/