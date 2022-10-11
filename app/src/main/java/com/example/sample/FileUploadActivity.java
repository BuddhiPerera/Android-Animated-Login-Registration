package com.example.sample;

import android.Manifest;
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

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class FileUploadActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    ImageView imageView;
    Button camera, gallery;
    OkHttpClient client = new OkHttpClient();
    //    private ProgressDialog progressDialog;
    String path;
    private JSONObject jsonObject;
    private JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload);

        camera = findViewById(R.id.btn_camera);
        gallery = findViewById(R.id.btn_gallery);
        imageView = findViewById(R.id.imageViews);
//        progressDialog = new ProgressDialog(FileUploadActivity.this);
//        progressDialog.setMessage("Image Uploading...");

        //Add permission for camera
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CheckPermission()) {
                    Intent m_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
                    Context context = FileUploadActivity.this;
                    Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
                    m_intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(m_intent, 0);
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
                System.out.println(resultCode + "SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSss");
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    imageView.setImageBitmap(imageBitmap);
        /*            try {
                        Uri imageUri = data.getData();
                        imageView.setImageBitmap(imageBitmap);
//                        progressDialog.show();
                        Context context = FileUploadActivity.this;
                        path = RealPathUtil.getRealPath(context, imageUri);
                        System.out.println(imageUri+"RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRrr");
                        UploadImage(path);
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        imageView.setImageBitmap(bitmap);
////                        progressDialog.show();
//                        UploadImage(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }
                //capture
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
//                        progressDialog.show();
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
        MultipartBody.Part image = MultipartBody.Part.createFormData("/*", file.getName(), requestFile);

        Retrofit retrofit = NetworkClient.getRetrofit();
        UploadApis uploadApis = retrofit.create(UploadApis.class);
        Call<ResponseBody> call = uploadApis.uploadImage(image);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   @NonNull Response<ResponseBody> response) {
                ResponseBody ResponseBody = response.body();
                Log.v("Respose code:",""+response.code());
                Log.v("Upload", "success" + call + " " + ResponseBody);
                // System.out.println(ResponseBody.getImage() + "SSSSSSSSSSSSSAAAAAAAAAA");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });

        /*
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
         requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("UPLOADCARE_PUB_KEY", "6c37d1cf7ea0f23ed7c0")
                .build();
        MultipartBody.Part parts = MultipartBody.Part.createFormData("image", file.getPath(), requestBody);

        RequestBody someData = RequestBody.create(MediaType.parse("multipart/form-data"), "This is a new Image");

        UploadApis uploadApis = retrofit.create(UploadApis.class);
        Call call = uploadApis.uploadImage(parts, someData);
       */
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
            return true;
        } else {
            System.out.println("77777777777777777777777777777777777777777777");
            return true;

        }
    }


}
