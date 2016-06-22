package com.example.dylanmcdowell.thetowersapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class ComposeMessageActivity extends AppCompatActivity {
    EditText topicString;
    EditText messageString;
    String topic;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_message);

        topicString = (EditText) findViewById(R.id.editText3);
        messageString = (EditText) findViewById(R.id.editText4);


    }
}
