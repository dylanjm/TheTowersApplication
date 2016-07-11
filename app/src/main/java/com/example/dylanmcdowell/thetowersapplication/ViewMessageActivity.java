package com.example.dylanmcdowell.thetowersapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ViewMessageActivity extends AppCompatActivity {
    TextView sender;
    TextView subject;
    TextView body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);

        //link up to respective text views
        sender = (TextView) findViewById(R.id.textView6);
        subject = (TextView) findViewById(R.id.textView10);
        body = (TextView) findViewById(R.id.textView14);

        Bundle bundle2 = getIntent().getExtras();
        sender.setText(bundle2.getString("sender"));
        subject.setText(bundle2.getString("subject"));
        body.setText(bundle2.getString("body"));
        //System.out.println(bundle2.getString("subject"));

    }

}
