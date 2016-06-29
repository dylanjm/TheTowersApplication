package com.example.dylanmcdowell.thetowersapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubmitRequestActivity extends AppCompatActivity {

    EditText topicString;
    EditText messageString;
    Button submit;
    Button cancel;
    CheckBox checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_request);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        topicString = (EditText) findViewById(R.id.editText6);
        messageString = (EditText) findViewById(R.id.editText5);
        checkbox = (CheckBox) findViewById(R.id.checkbox);

        //submit button
        submit = (Button) findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String subject = topicString.getText().toString();
                String body = messageString.getText().toString();
                Boolean emergency = checkbox.isChecked();

                DatabaseReference myRef = database.getReference("Requests");
                System.out.println(subject + body);
                Request request = new Request(subject, body, "Yo momma", emergency);
                // Create a new, auto-generated child of that chat location, and save our chat data there
                myRef.push().setValue(request);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //finish();
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
