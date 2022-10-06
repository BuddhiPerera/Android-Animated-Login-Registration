package com.example.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;


public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setContentView(R.layout.activity_dashboard);

        RelativeLayout relativeClick =(RelativeLayout)findViewById(R.id.dashboardProfile);
        relativeClick.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                switchToProfile();
            }
        });
    }
    private void switchToProfile() {
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
        finish();
    }
}