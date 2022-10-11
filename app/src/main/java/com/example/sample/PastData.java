package com.example.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class PastData extends AppCompatActivity {
    private ListView listViewMessage;
    private TextView textView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_data);

        listViewMessage = findViewById(R.id.listViewMessage);

        textView = findViewById(R.id.textViewStudent);

        intent = getIntent();
        textView.setText("Welcome ");
//https://www.youtube.com/watch?v=M8sKwoVjqU0
       // ArrayAdapter<List> arrayAdapter = new ArrayAdapter<List>(this, android.R.layout.simple_list_item_1, db.loadAllMessage());
        //listViewMessage.setAdapter(arrayAdapter);

        listViewMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String posii = listViewMessage.getItemAtPosition(position).toString();
//                Intent intent = new Intent(Student.this,Message.class);
//                intent.putExtra("Posi",posii);
//                startActivity(intent);
            }
        });

    }
}