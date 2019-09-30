package com.example.emergencyapp;

import android.content.Intent;
import android.provider.ContactsContract;
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

public class FeedbackActivity extends AppCompatActivity {

    EditText textArea;
    Button send, back;
    String uid, name, message;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        textArea = (EditText) findViewById(R.id.textArea);
        send = (Button) findViewById(R.id.sendFeedback);
        back = (Button) findViewById(R.id.backBtn);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uid = firebaseUser.getUid();
                name = dataSnapshot.child("Users").child(uid).child("userFullName").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(), "NETWORK ERROR, please check your connection", Toast.LENGTH_LONG).show();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = textArea.getText().toString();

                databaseReference = firebaseDatabase.getReference("Feedback");

                Feedback feedback = new Feedback(uid, name, message);

                String key = databaseReference.push().getKey();

                databaseReference.child(key).setValue(feedback);

                Toast.makeText(getApplicationContext(), "Thanks for your feedback !", Toast.LENGTH_LONG).show();

                startActivity(new Intent(FeedbackActivity.this, MainActivity.class));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FeedbackActivity.this, MainActivity.class));
            }
        });

    }
}
