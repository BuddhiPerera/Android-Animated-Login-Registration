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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.sample.model.Results;
import com.example.sample.model.SourceData;
import com.google.android.material.navigation.NavigationView;

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


public class FileUploadActivity extends AppCompatActivity{

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    ImageView imageView;
    Button camera, gallery, home;
    String path;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_file_upload);

        camera = findViewById(R.id.btn_camera);
        gallery = findViewById(R.id.btn_gallery);
        imageView = findViewById(R.id.imageViews);
        home = findViewById(R.id.btn_home);
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

        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent home = new Intent(FileUploadActivity.this, DashboardActivity.class);
                startActivity(home);
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
        Call<Results> call = uploadApis.uploadImage(image);
        Log.v("Response code:", "sssssssss" +call );
        call.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call,
                                   @NonNull Response<Results> response) {

                Results Results = response.body();
                Log.v("Responsez code:", Results +" " + response.code());
                System.out.println("_________________________________________________________");
//                assert Results != null;
                progressDialog.cancel();
//                System.out.println(Results.getResults().getStone_details().getFunctionalDescription() + "\n " + Results.getResults().getStone_details().getIsArtifact()
//                        + " \n" + Results.getResults().getStone_details().getRoughRelativeDating() + "\n "
//                        + " \n"
//                        + Results.getResults().getStone_details().getRoughRelativeDating());

//                SourceData.setSource_link("https://ucarecdn.com/cb9d35f5-1319-4c87-8cca-06750382b26f/1665545189066IMG_20220508_133259.jpg");
//                SourceData.getStone_details().setFunctionalDescription("\"This stone tool is a Blade. This tool was used to stripping flesh from hunted animals or to shape other tools such as woods and bones.\"");
//                SourceData.getStone_details().setMineralType("Quartz");
//                SourceData.getStone_details().setIsArtifact("true");
//                SourceData.getStone_details().setMakingTechnique("Blade Technique");
//                SourceData.getStone_details().setRoughRelativeDating("Mesolithic");

                switchToProfile(Results);
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                progressDialog.cancel();
                Log.e("Uploads error:", t.getMessage());
            }
        });
    }

    private void switchToProfile(Results sourceData) {
        Intent intent = new Intent(this, ResponseActivity.class);
        intent.putExtra("isArtifact",sourceData.getStone_details().isArtifact());
        intent.putExtra("mineralType",sourceData.getStone_details().getMineralType());
        intent.putExtra("roughRelativeRating",sourceData.getStone_details().getRoughRelativeDating());

        intent.putExtra("functionalDescription",sourceData.getStone_details().getFunctionalDescription());
        intent.putExtra("makingTechnique",sourceData.getStone_details().getMakingTechnique());
        intent.putExtra("sourceLink",sourceData.getSource_link());

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
