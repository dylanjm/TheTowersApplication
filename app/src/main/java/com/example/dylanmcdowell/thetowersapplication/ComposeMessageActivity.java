package com.example.dylanmcdowell.thetowersapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ComposeMessageActivity extends AppCompatActivity {
    EditText topicString;
    EditText messageString;
    Button send;
    String topic;
    String message;
    private static final String FIREBASE_URL = "https://android-chat.firebaseio-demo.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_message);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        send = (Button) findViewById(R.id.button11);
        topicString = (EditText) findViewById(R.id.editText3);
        messageString = (EditText) findViewById(R.id.editText4);

        if (!messageString.equals("")) {
            // Create our 'model', a Chat object
            String subject = topicString.getText().toString();
            String body = messageString.getText().toString();
            Message chat = new Message(subject, body, "BILL");
            // Create a new, auto-generated child of that chat location, and save our chat data there
            myRef.push().setValue(chat);
            topicString.setText("");
            messageString.setText("");
        }



    }
}
