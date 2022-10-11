package com.example.sample;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    Uri image_uri;

    // drawer navigation
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setContentView(R.layout.activity_dashboard);

        RelativeLayout relativeClick = (RelativeLayout) findViewById(R.id.dashboardProfile);
        relativeClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToProfile();
            }
        });
        RelativeLayout relativeClickCamera = (RelativeLayout) findViewById(R.id.dashboardCamera);
        relativeClickCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                switchToCamera();
                switchToCamera2();

            }
        });
        RelativeLayout relativeClickMap = (RelativeLayout) findViewById(R.id.dashboardMap);
        relativeClickMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMap();
            }
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) RelativeLayout history = (RelativeLayout) findViewById(R.id.history);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToHistory();
            }
        });

        // drawer layout
        // Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        // toolbar
        setSupportActionBar(toolbar);

        // navigation drawer menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);
    }

    private void switchToHistory() {
        Intent intent = new Intent(this, PastData.class);
        startActivity(intent);
        finish();
    }


    private void switchToCamera2() {
        Intent intent = new Intent(this, FileUploadActivity.class);
        startActivity(intent);
        finish();
    }

    private void switchToMap() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo: 4377.3 2005.789"));
        startActivity(intent);
    }

    private void switchToProfile() {
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
        finish();
    }


    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture ");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From The Camera ");
        image_uri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[1]
                    == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Permission Denied..", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {
//            mIma.setImageURI(image_uri);
        }
    }

    // back click --> stop close the app
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
        switch(menuItem.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_camera:
                Intent camera = new Intent(DashboardActivity.this, FileUploadActivity.class);
                startActivity(camera);
                break;
            case R.id.nav_profile:
                Intent profile = new Intent(DashboardActivity.this, UserProfileActivity.class);
                startActivity(profile);
            case R.id.nav_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}