package com.example.emergencyapp;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ImageButton profileBtn, callBtn, msgBtn, feedbackBtn, guideBtn, logoutBtn;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileBtn = (ImageButton) findViewById(R.id.profileBtn);
        callBtn = (ImageButton) findViewById(R.id.callBtn);
        msgBtn = (ImageButton) findViewById(R.id.msgBtn);
        feedbackBtn = (ImageButton) findViewById(R.id.fbBtn);
        guideBtn = (ImageButton) findViewById(R.id.guideBtn);
        logoutBtn = (ImageButton) findViewById(R.id.logoutBtn);
        firebaseAuth = FirebaseAuth.getInstance();

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EmergencyCallActivity.class));
            }
        });
        msgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EmergencyMessageActivity.class));
            }
        });
        feedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
            }
        });
        guideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SurvivalGuideActivity.class));
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

    }
}
