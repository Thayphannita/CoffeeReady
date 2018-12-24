package com.example.asus.coffeeready;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
        finish();
    }
}
