package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class ResponseActivity extends AppCompatActivity {

    TextView text_response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        Intent i  = getIntent();
        String name = i.getStringExtra("name");
        String source_link = i.getStringExtra("source_link");
        String category = i.getStringExtra("category");
        String _id = i.getStringExtra("_id");
        String date = i.getStringExtra("date");
                text_response = findViewById(R.id.text_response);
   String data = "Name :" + name
           +"\nSource Link :" + source_link
           +"\nCategory :" + category
           +"\nId :" +_id
           +"\nDate" + date;

        text_response.setText(data);

    }
}