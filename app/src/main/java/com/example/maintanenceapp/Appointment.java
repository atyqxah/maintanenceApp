package com.example.maintanenceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Appointment extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText datepick;
    EditText mplatenum;
    Button mcancelbook, mbookingbtn;
    Spinner cartypes, timepick, statusspinner;
    TextView statusa;
    DatePickerDialog.OnDateSetListener setListener;
    int hour, minutes;
    FirebaseAuth fAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
   // DatabaseReference databaseReference2;

    String ServiceType;
    String ServiceMileage;
    String ServiceCost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        datepick = findViewById(R.id.editTextDate);
        timepick = findViewById(R.id.editTextTime);
        timepick.setOnItemSelectedListener(this);
        mplatenum = findViewById(R.id.platenum);
        mcancelbook = findViewById(R.id.resetservebtn);
        mbookingbtn = findViewById(R.id.servicebtn);
        //statusa = findViewById(R.id.statustext);
        statusspinner = (Spinner) findViewById(R.id.editTextStatus);
        statusspinner.setEnabled(false);
        ServiceType = getIntent().getExtras().get("ServiceType").toString();
        ServiceMileage = getIntent().getExtras().get("ServiceMileage").toString();
        ServiceCost = getIntent().getExtras().get("ServiceCost").toString();

        fAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        // String rServiceId = databaseReference.push().getKey();
        databaseReference = FirebaseDatabase.getInstance().getReference("Appointment");
        mbookingbtn.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
                appointment();
           }
        });

        mcancelbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mplatenum.setText("");
                timepick.setSelection(0);
                datepick.setText("");
                cartypes.setSelection(0);
            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE);
        cartypes = findViewById(R.id.cartype);
        cartypes.setOnItemSelectedListener(this);


        //databaseReference2 = FirebaseDatabase.getInstance().getReference("Appointment").child("AppointmentTime");

        /*timepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        Appointment.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour = hourOfDay;
                        minutes = minute;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0, 0, 0,hour,minutes);
                        timepick.setText(android.text.format.DateFormat.format("hh:mm aa",calendar));
                    }
                }, 12, 0,false);
                timePickerDialog.updateTime(hour,minutes);
                timePickerDialog.show();
            }
        });*/

        datepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Appointment.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        datepick.setText(date);
                    }
                },year,month,day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("APPOINTMENT");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Appointment.this, CarService.class));
            }
        });
    }

    private void appointment() {

        String aptime = timepick.getSelectedItem().toString();
        String apdate = datepick.getText().toString().trim();
        String applatenum = mplatenum.getText().toString().trim();
        String mcartype = cartypes.getSelectedItem().toString();
        String mstatus = statusspinner.getSelectedItem().toString();
        //String mstatus = statusa.getText().toString().trim();
        String userid = firebaseUser.getUid();
        String memail = firebaseUser.getEmail();
        if(!TextUtils.isEmpty(apdate) && !TextUtils.isEmpty(aptime) && !TextUtils.isEmpty(applatenum) && !TextUtils.isEmpty(mcartype)) {
            String appointmentid = databaseReference.push().getKey();
            Deal deal = new Deal(appointmentid,aptime,apdate,applatenum,mcartype,mstatus,userid, memail, ServiceType, ServiceMileage, ServiceCost);
            databaseReference.child(appointmentid).setValue(deal);
            Toast.makeText(this,"Appointment book successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }else{
            Toast.makeText(this,"Data is empty", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,parent.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}