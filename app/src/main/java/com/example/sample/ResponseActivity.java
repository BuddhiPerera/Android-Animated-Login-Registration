package com.example.sample;

import android.app.ProgressDialog;
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

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;

public class ResponseActivity extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    TextView text_response;
    ImageView responseImage;

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
        responseImage = findViewById(R.id.responseImage);
   String data = "Name :" + name
           +"\nCategory :" + category
           +"\nId :" +_id
           +"\nDate" + date;


        new DownloadImageTask(responseImage).execute(source_link);


        text_response.setText(data);

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
