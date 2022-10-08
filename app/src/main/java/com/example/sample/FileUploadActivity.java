package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Button;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;

public class FileUploadActivity extends AppCompatActivity {

    ImageView imageView;
    Button camera,gallery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload);

        camera=findViewById(R.id.btn_camera);
        gallery=findViewById(R.id.btn_gallery);
        imageView=findViewById(R.id.imageViews);
//Add permission for camera
        if (ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CAMERA)==
                PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(FileUploadActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                    WeakReference<Bitmap> result1 = new WeakReference<Bitmap>(Bitmap.createScaledBitmap(thumbnail,
                            thumbnail.getWidth(), thumbnail.getHeight(), false).copy(
                            Bitmap.Config.RGB_565, true));
                    Bitmap bm=result1.get();
                    imageView.setImageBitmap(bm);


               /*   Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    imageView.setImageBitmap(imageBitmap);*/
 /*

                    File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
                    //Uri of camera image
                    Uri selectedImage = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", file);
                    System.out.println(selectedImage + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaa");
                    imageView.setImageURI(selectedImage);*/

//                    camerastore(u);
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    imageView.setImageURI(selectedImage);
                }
                break;
        }
    }
    Uri u;
    public void pick_from_camera(View view) {

        /*
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);

        intentArray = new Intent[]{takePictureIntent};
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Choose an action");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
        startActivityForResult(chooserIntent, 0);
      */
        Intent m_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
        Uri uri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", file);
//        m_intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
//        u = uri;
        startActivityForResult(m_intent, 0);

   /*     long time = System.currentTimeMillis();
        String stvalue = Long.toString(time);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File output = new File(dir, stvalue);
        u = Uri.fromFile(output);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
        startActivityForResult(intent, 0);*/
    }

    void camerastore(Uri u) {
        Bitmap bitmap = null;
        File pictureFile = new File("yourpath/image.jpg");

        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), u);
            FileOutputStream fos = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("file_uri", u);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        u = savedInstanceState.getParcelable("file_uri");
    }
    public void pick_from_gallery(View view) {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);
    }
}
