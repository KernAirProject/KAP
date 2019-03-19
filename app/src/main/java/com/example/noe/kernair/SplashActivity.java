package com.example.noe.kernair;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.*;
import com.google.firebase.auth.FirebaseUser;

//import com.google.android.gms.tasks.*;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import android.support.annotation.*;
//import android.util.Log;


public class SplashActivity extends AppCompatActivity {
    // Timeout in milliseconds
    private static int SPLASH_TIME_OUT = 1000;
    private static final String TAG = "SplashActivity";
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_splash);
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is logged in
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run(){
                    Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            },SPLASH_TIME_OUT);
        } else {
        setContentView(R.layout.activity_add_email);
   } }}



