package com.example.dylanmcdowell.thetowersapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;


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
        FirebaseAuth fauth = FirebaseAuth.getInstance();
        FirebaseUser user = fauth.getCurrentUser();
        final String username =  user.getEmail();

        //submit button
        submit = (Button) findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DatabaseReference myRef = database.getReference("Requests");

                String subject = topicString.getText().toString();
                String body = messageString.getText().toString();
                Boolean emergency = checkbox.isChecked();
                String time = new SimpleDateFormat("MM/dd/yyyy_hh:mm a").format(Calendar.getInstance().getTime());


                System.out.println(subject + body);
                Request request = new Request(subject, body, username, emergency, "420", time);
                // Create a new, auto-generated child of that request location, and save our chat data there
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
