package com.example.eductionery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class OnboardingScrenn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_screnn);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}