package com.example.sample;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.sample.model.SourceData;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class FileUploadActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    ImageView imageView;
    Button camera, gallery;
    String path;
    ProgressDialog progressDialog;

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
//                    Intent m_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(m_intent, 0);
                    dispatchTakePictureIntent();
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

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.sample.provider",
                        photoFile);

                mPhotoFile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CAMERA_PHOTO);
            }
        }
    }

    File mPhotoFile;

    static final int REQUEST_CAMERA_PHOTO =0;
    private File createImageFile() throws IOException {
        // Create an image file name
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 0: {
                if (resultCode == RESULT_OK) {

                    File file = new File(String.valueOf(mPhotoFile));
                    imageView.setImageURI(Uri.fromFile(file));
                    progressDialog.show();
                    try {
                        UploadImage(String.valueOf(mPhotoFile));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
            case 1: {
                if (resultCode == RESULT_OK) {
                    try {
                        Uri uri = data.getData();
                        Context context = FileUploadActivity.this;
                        path = RealPathUtil.getRealPath(context, uri);
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        imageView.setImageBitmap(bitmap);
                        progressDialog.show();
                        UploadImage(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
            break;
        }
    }

    private void UploadImage(String imageUri) throws IOException {
        File file = new File(imageUri);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("uploadFile", file.getName(), requestFile);

        Retrofit retrofit = NetworkClient.getRetrofit();
        UploadApis uploadApis = retrofit.create(UploadApis.class);
        Call<SourceData> call = uploadApis.uploadImage(image);

        call.enqueue(new Callback<SourceData>() {
            @Override
            public void onResponse(Call<SourceData> call,
                                   @NonNull Response<SourceData> response) {

                SourceData SourceData = response.body();
                Log.v("Response code:", "" + response.code());
                System.out.println("_________________________________________________________");
                assert SourceData != null;
                progressDialog.cancel();
                System.out.println(SourceData.getSourcefile().get_id() + "\n " + SourceData.getSourcefile().getSource_link()
                        + " \n" + SourceData.getSourcefile().getCategory() + "\n "
                        + SourceData.getSourcefile().getName() + " \n"
                        + SourceData.getSourcefile().getDate());
                switchToProfile(SourceData);
            }

            @Override
            public void onFailure(Call<SourceData> call, Throwable t) {
                progressDialog.cancel();
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

    private void switchToProfile(SourceData sourceData) {
        Intent intent = new Intent(this, ResponseActivity.class);
        intent.putExtra("name",sourceData.getSourcefile().getName());
        intent.putExtra("source_link",sourceData.getSourcefile().getSource_link());
        intent.putExtra("category",sourceData.getSourcefile().getCategory());
        intent.putExtra("_id",sourceData.getSourcefile().get_id());
        intent.putExtra("date",sourceData.getSourcefile().getDate());
        startActivity(intent);
        finish();
    }
    public boolean CheckPermission() {

        if (ContextCompat.checkSelfPermission(FileUploadActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(FileUploadActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(FileUploadActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(FileUploadActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(FileUploadActivity.this,
                    Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(FileUploadActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(FileUploadActivity.this)
                        .setTitle("Permission")
                        .setMessage("Please accept the permissions")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(FileUploadActivity.this,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST_LOCATION);

                                startActivity(new Intent(FileUploadActivity
                                        .this, FileUploadActivity.class));
                                FileUploadActivity.this.overridePendingTransition(0, 0);
                            }
                        })
                        .create()
                        .show();

            } else {
                ActivityCompat.requestPermissions(FileUploadActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return true;
        } else {
            return true;

        }
    }


}
