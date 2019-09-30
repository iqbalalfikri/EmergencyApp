package com.example.emergencyapp;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmergencyMessageActivity extends AppCompatActivity {

    Button kecelakaan, kemalingan, tersesat;
    EditText phone;
    int MY_PERMISSION_REQUEST_SEND_SMS = 1;
    String SENT = "SMS_SENT";
    String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;
    FusedLocationProviderClient fusedLocationProviderClient;
    double locationLat;
    double locationLong;
    int REQUEST_LOCATION = 1;

    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onResume() {
        super.onResume();

        smsSentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(EmergencyMessageActivity.this, "SMS SENT !", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(EmergencyMessageActivity.this, "Generic Failure !", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(EmergencyMessageActivity.this, "No Service !", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(EmergencyMessageActivity.this, "Null PDU !",Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(EmergencyMessageActivity.this, "Radio Off !", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        };
        smsDeliveredReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(EmergencyMessageActivity.this, "SMS DELIVERED", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(EmergencyMessageActivity.this, "SMS not delivered", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        registerReceiver(smsSentReceiver, new IntentFilter(SENT));
        registerReceiver(smsDeliveredReceiver, new IntentFilter(DELIVERED));
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(smsDeliveredReceiver);
        unregisterReceiver(smsSentReceiver);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_message);

        phone = (EditText) findViewById(R.id.phoneTxt);
        kecelakaan = (Button) findViewById(R.id.kecelakaan);
        kemalingan = (Button) findViewById(R.id.kemalingan);
        tersesat = (Button) findViewById(R.id.Tersesat);
        sentPI = PendingIntent.getBroadcast(this, 0,new Intent(SENT), 0);
        deliveredPI = PendingIntent.getBroadcast(this, 0,new Intent(DELIVERED), 0);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                phone.setText(dataSnapshot.child("Users").child(firebaseUser.getUid()).child("emergencyNumber").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "NETWORK ERROR, please check your connection", Toast.LENGTH_LONG).show();
            }
        });

        if(ContextCompat.checkSelfPermission(EmergencyMessageActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(EmergencyMessageActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION );
        }
        GPSTracker gps = new GPSTracker(this);






        kecelakaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GPSTracker gps = new GPSTracker(EmergencyMessageActivity.this);
                String number = phone.getText().toString();
                String message = "Tolong, saya mengalami kecelakaan \n Lokasi : " + "http://maps.google.com/maps?daddr=" + gps.getLatitude() + "," + gps.getLongitude();

                if(ContextCompat.checkSelfPermission(EmergencyMessageActivity.this, Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(EmergencyMessageActivity.this, new String[]{Manifest.permission.SEND_SMS},
                           MY_PERMISSION_REQUEST_SEND_SMS );
                }

                else
                {
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(number, null, message, sentPI, deliveredPI);
                }

            }
        });

        kemalingan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GPSTracker gps = new GPSTracker(EmergencyMessageActivity.this);
                String number = phone.getText().toString();
                String message = "Tolong, saya mengalami kemalingan \n Lokasi : " + "http://maps.google.com/maps?daddr=" + gps.getLatitude() + "," + gps.getLongitude();

                if(ContextCompat.checkSelfPermission(EmergencyMessageActivity.this, Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(EmergencyMessageActivity.this, new String[]{Manifest.permission.SEND_SMS},
                            MY_PERMISSION_REQUEST_SEND_SMS );
                }

                else
                {
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(number, null, message, sentPI, deliveredPI);
                }

            }
        });

        tersesat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GPSTracker gps = new GPSTracker(EmergencyMessageActivity.this);
                String number = phone.getText().toString();
                String message = "Tolong, saya tersesat \n Lokasi : " + "http://maps.google.com/maps?daddr=" + gps.getLatitude() + "," + gps.getLongitude();

                if(ContextCompat.checkSelfPermission(EmergencyMessageActivity.this, Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(EmergencyMessageActivity.this, new String[]{Manifest.permission.SEND_SMS},
                            MY_PERMISSION_REQUEST_SEND_SMS );
                }

                else
                {
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(number, null, message, sentPI, deliveredPI);
                }

            }
        });


    }
}
