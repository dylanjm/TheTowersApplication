package com.example.dylanmcdowell.thetowersapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class SubmitRequestActivity extends AppCompatActivity {

    Button submit;
    Button cancel;
    CheckBox checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_request);

        //submit button
        submit = (Button) findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();

            }
        });

        //Cancel button
        cancel = (Button) findViewById(R.id.button2);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });

        checkbox = (CheckBox) findViewById(R.id.checkBox);


    }
}
