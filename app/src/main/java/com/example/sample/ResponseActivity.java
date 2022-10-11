package com.example.sample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sample.model.SourceFile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;

public class ResponseActivity extends AppCompatActivity {
    TextView text_response;
    ImageView responseImage;
    Button btn_save_result;
    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        Intent i = getIntent();
        String name = i.getStringExtra("name");
        String source_link = i.getStringExtra("source_link");
        String category = i.getStringExtra("category");
        String _id = i.getStringExtra("_id");
        String date = i.getStringExtra("date");

        SourceFile sourceFile = new SourceFile();

        sourceFile.set_id(_id);
        sourceFile.setDate(date);
        sourceFile.setCategory(category);
        sourceFile.setSource_link(source_link);
        sourceFile.setName(name);


        mStorageRef = FirebaseStorage.getInstance().getReference("stonelia");
        databaseReference = FirebaseDatabase.getInstance().getReference("stonelia");


        text_response = findViewById(R.id.text_response);

        btn_save_result = findViewById(R.id.btn_save_result);
        responseImage = findViewById(R.id.responseImage);
        String data = "Name :" + name
                + "\nCategory :" + category
                + "\nId :" + _id
                + "\nDate" + date;
        mAuth = FirebaseAuth.getInstance();

        new DownloadImageTask(responseImage).execute(source_link);


        text_response.setText(data);


        btn_save_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveInFireBase(sourceFile);
            }
        });

    }

    private void saveInFireBase(SourceFile sourceFile) {
        String name = sourceFile.getName();
        String source_link = sourceFile.getSource_link();
        String category = sourceFile.getCategory();
        String _id = sourceFile.get_id();
        String date = sourceFile.getDate();

        if (name.isEmpty() || source_link.isEmpty() || category.isEmpty() || _id.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }
        String uploadId = databaseReference.push().getKey();
        databaseReference.child(uploadId).setValue(sourceFile);

        // after adding this data we are showing toast message.
        Toast.makeText(ResponseActivity.this, "data added", Toast.LENGTH_SHORT).show();


    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
