package com.example.sample;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class FileUploadActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    ImageView imageView;
    Button camera, gallery;
    private JSONObject jsonObject;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload);

        camera = findViewById(R.id.btn_camera);
        gallery = findViewById(R.id.btn_gallery);
        imageView = findViewById(R.id.imageViews);
        progressDialog = new ProgressDialog(FileUploadActivity.this);
        progressDialog.setMessage("Image Uploading...");
//Add permission for camera
        camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (CheckPermission()) {
                    Intent capture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(capture, 0);
                }
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckPermission()) {
                    Intent select = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(select, 1);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case 0: {

                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(bitmap);
                    progressDialog.show();
                    UploadImage(bitmap);

                }


                //capture
            }
            break;

            case 1: {
                if (resultCode == RESULT_OK) {


                    try {
                        Uri imageUri = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        imageView.setImageBitmap(bitmap);
                        progressDialog.show();
                        UploadImage(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
            break;
        }


    }

    private void UploadImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String image = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        String name = String.valueOf(Calendar.getInstance().getTimeInMillis());

        try {
            jsonObject = new JSONObject();
            jsonObject.put("name", name);
            jsonObject.put("image", image);

            jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://server.ssss/blabla", jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String message = response.getString("message");
                                Toast.makeText(FileUploadActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(FileUploadActivity.this, "Image Upload Failed", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }


            );
        } catch (JSONException e) {
            e.printStackTrace();
        }


        requestQueue = Volley.newRequestQueue(FileUploadActivity.this);
        requestQueue.add(jsonObjectRequest);

    }

    public boolean CheckPermission() {
        System.out.println("00000000000000000000000000000000");
        if (ContextCompat.checkSelfPermission(FileUploadActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(FileUploadActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(FileUploadActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            System.out.println("1111111111111111111111111111111111111111");
            if (ActivityCompat.shouldShowRequestPermissionRationale(FileUploadActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(FileUploadActivity.this,
                    Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(FileUploadActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                System.out.println("2222222222222222222222222222222222222222");
                new AlertDialog.Builder(FileUploadActivity.this)
                        .setTitle("Permission")
                        .setMessage("Please accept the permissions")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                System.out.println("3333333333333333333333333333333333333333333333");
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(FileUploadActivity.this,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                                System.out.println("444444444444444444444444444444444444444444444");

                                startActivity(new Intent(FileUploadActivity
                                        .this, FileUploadActivity.class));
                                FileUploadActivity.this.overridePendingTransition(0, 0);
                            }
                        })
                        .create()
                        .show();

            } else {
                System.out.println("555555555555555555555555555555555555555555555");
                ActivityCompat.requestPermissions(FileUploadActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            System.out.println("6666666666666666666666666666666666666666666");
            return false;
        } else {
            System.out.println("77777777777777777777777777777777777777777777");
            return true;

        }
    }


}
