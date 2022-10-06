package com.example.sample;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDb = mDatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userKey = user.getUid();

        mDb.child("users").child(userKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                String mobile = dataSnapshot.child("mobileNumber").getValue(String.class);
                String email = dataSnapshot.child("email").getValue(String.class);
                Log.d(TAG, "Name: " + name);
                Log.d(TAG, "mobile: " + mobile);
                Log.d(TAG, "email: " + email);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}