package com.example.emergencyapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registerActivity extends AppCompatActivity {
    Button loginButton, registerButton;
    EditText etEmail, etPassword, etConfirmPass, etPhone, etFullName;
    FirebaseAuth firebaseAuth;
    String fullName, email, password, phone;
    String emergencyNumber = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        setupUIViews();


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRegister();

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(registerActivity.this, LoginActivity.class));
            }
        });
    }

    public void setupUIViews(){
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPass = (EditText) findViewById(R.id.confirmPassword);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);
        etFullName = (EditText) findViewById(R.id.etFullName);
        etPhone = (EditText) findViewById(R.id.etPhone);

    }

    public void userRegister(){
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        fullName = etFullName.getText().toString();
        phone = etPhone.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter your email address", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(etPassword.getText().toString())){
            Toast.makeText(this,"Please enter your password", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(etConfirmPass.getText().toString())){
            Toast.makeText(this,"Please confirm your password", Toast.LENGTH_LONG).show();
            return;
        }


        if(etPassword.getText().toString().equals(etConfirmPass.getText().toString())) {
            password = etPassword.getText().toString();
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        sendUserData();
                        Toast.makeText(registerActivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(registerActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(registerActivity.this, "Couldn't Register. Please try again", Toast.LENGTH_LONG).show();
                    }

                }
            });
        }

    }

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("Users");
        UserProfile userProfile = new UserProfile(fullName, email, phone, emergencyNumber);
        myRef.child(firebaseAuth.getUid()).setValue(userProfile);
    }

}
