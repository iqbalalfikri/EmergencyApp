package com.example.emergencyapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {

    EditText fullName, email, phone, emergencyNumber;
    Button save, cancel;

    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        fullName = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        emergencyNumber = (EditText) findViewById(R.id.emergencyNumber);

        save = (Button) findViewById(R.id.saveBtn);
        cancel = (Button) findViewById(R.id.cancelBtn);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fullName.setText(dataSnapshot.child("Users").child(firebaseUser.getUid()).child("userFullName").getValue(String.class));

                email.setText(dataSnapshot.child("Users").child(firebaseUser.getUid()).child("userEmail").getValue(String.class));

                phone.setText(dataSnapshot.child("Users").child(firebaseUser.getUid()).child("userPhone").getValue(String.class));


                emergencyNumber.setText(dataSnapshot.child("Users").child(firebaseUser.getUid()).child("emergencyNumber").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "NETWORK ERROR, please check your connection", Toast.LENGTH_LONG).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newFullName = fullName.getText().toString();
                String newEmail = email.getText().toString();
                String newPhone = phone.getText().toString();
                String eNumber = emergencyNumber.getText().toString();


                databaseReference = firebaseDatabase.getReference("Users");

                UserProfile userProfile = new UserProfile(newFullName, newEmail, newPhone, eNumber);

                databaseReference.child(firebaseUser.getUid()).setValue(userProfile);

                Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_LONG).show();

                startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
            }
        });
    }
}
