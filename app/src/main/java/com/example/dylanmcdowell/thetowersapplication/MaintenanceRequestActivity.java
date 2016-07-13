package com.example.dylanmcdowell.thetowersapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MaintenanceRequestActivity extends AppCompatActivity {
    Button resolve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_request);
        final String key;

        Bundle bundle2 = getIntent().getExtras();
        key = bundle2.getString("key");
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


        resolve = (Button) findViewById(R.id.button9);
        resolve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mDatabase.child("Requests").child(key).removeValue();
                finish();
            }
        });
    }
}
