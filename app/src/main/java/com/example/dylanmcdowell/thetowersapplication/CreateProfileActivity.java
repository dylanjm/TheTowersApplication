package com.example.dylanmcdowell.thetowersapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class CreateProfileActivity extends AppCompatActivity {

    Button createProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_create_profile);
        final Firebase ref = new Firebase("https://towers-app.firebaseio.com");
        ref.createUser("bobtony@firebase.com", "correcthorsebatterystaple", new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {System.out.println("Successfully created user account with uid: " + result.get("uid"));}
            @Override
            public void onError(FirebaseError firebaseError) {System.out.println("FAIL");}
        });
        //try{Thread.sleep(20000);}
        //catch(Exception e){e.printStackTrace();}
        createProfile = (Button) findViewById(R.id.button13);
        createProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                finish();
            }
        });

    }
}
