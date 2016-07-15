package com.example.dylanmcdowell.thetowersapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MaintenanceRequestActivity extends AppCompatActivity {
    //Element Variables
    Button resolve;
    TextView sender;
    TextView subject;
    TextView body;
    TextView timeStamp;
    TextView roomNum;
    TextView isEmergency;
    Bundle bundle;

    /**************************************************
     * SET VIEWS - this sets all the views
     **************************************************/
    void setViews(){
        Spanned aptNumber = Html.fromHtml("<b>Apartment: </b>" + bundle.getString("roomNum"));
        Spanned issue     = Html.fromHtml("<b>Subject: </b>" + bundle.getString("subject"));
        Spanned from      = Html.fromHtml("<b>From: </b>" + bundle.getString("sender"));
        Spanned date      = Html.fromHtml("<b>Date: </b>"
                          + bundle.getString("timeStamp").replace("_0"," ").replace("_"," "));

        timeStamp.setText(date);
        body.setText(bundle.getString("body"));
        roomNum.setText(aptNumber);
        subject.setText(issue);
        sender.setText(from);

        if (bundle.getBoolean("isEmergency"))
            isEmergency.setText("Emergency!!");
    }

    /**************************************************
     * ON CREATE - the activity runs form here
     **************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_request);
        timeStamp    = (TextView) findViewById(R.id.textView2);
        roomNum      = (TextView) findViewById(R.id.textView4);
        subject      = (TextView) findViewById(R.id.textView17);
        sender       = (TextView) findViewById(R.id.textView18);
        body         = (TextView) findViewById(R.id.textView5);
        isEmergency  = (TextView) findViewById(R.id.textView19);

        bundle = getIntent().getExtras();
        setViews();

        resolve = (Button) findViewById(R.id.button9);
        resolve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final String key  = bundle.getString("key");
                final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("Requests").child(key).removeValue();
                finish();
            }
        });
    }
}
