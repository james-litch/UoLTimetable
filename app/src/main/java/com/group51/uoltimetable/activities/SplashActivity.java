package com.group51.uoltimetable.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.group51.uoltimetable.activities.MainActivity;


public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();

    }

}
