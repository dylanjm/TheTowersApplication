package com.example.dylanmcdowell.thetowersapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MaintenanceRequestActivity extends AppCompatActivity {
    Button resolve;
    TextView sender;
    TextView subject;
    TextView body;
    TextView timeStamp;
    TextView roomNum;
    TextView isEmergency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_request);

        subject     = (TextView) findViewById(R.id.textView2);
        sender      = (TextView) findViewById(R.id.textView4);
        timeStamp   = (TextView) findViewById(R.id.textView17);
        roomNum     = (TextView) findViewById(R.id.textView18);
        body        = (TextView) findViewById(R.id.textView5);
        isEmergency = (TextView) findViewById(R.id.textView19);

        Bundle bundle2 = getIntent().getExtras();
        final String key = bundle2.getString("key");
        subject.setText("Subject: " + bundle2.getString("subject"));
        body.setText(bundle2.getString("body"));
        sender.setText("From: " + bundle2.getString("sender"));
        timeStamp.setText(bundle2.getString("timeStamp"));
        roomNum.setText("Apt: " + bundle2.getString("roomNum"));
        if (bundle2.getBoolean("isEmergency"))
        {
            isEmergency.setText("Emergency!!");
        }
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
