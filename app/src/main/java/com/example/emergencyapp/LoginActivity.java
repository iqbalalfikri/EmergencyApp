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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    Button loginButton, registerButton;
    ProgressBar loginProgressBar, registerProgressBar;
    FirebaseAuth firebaseAuth;
    EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        setupUIViews();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              userLogin();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, registerActivity.class));
            }
        });
    }

    public void userLogin(){
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter your email address", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(etPassword.getText().toString())){
            Toast.makeText(this,"Please enter your password", Toast.LENGTH_LONG).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                
                else{
                    Toast.makeText(LoginActivity.this, "Failed to Login. Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setupUIViews(){
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);

        loginButton = findViewById(R.id.loginButton);
        loginProgressBar = findViewById(R.id.loginProgressBar);
        loginProgressBar.setVisibility(View.INVISIBLE);

        registerButton = findViewById(R.id.registerButton);
        registerProgressBar = findViewById(R.id.registerProgressBar);
        registerProgressBar.setVisibility(View.INVISIBLE);
    }
}
