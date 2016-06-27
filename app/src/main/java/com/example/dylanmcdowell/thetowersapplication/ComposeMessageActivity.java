package com.example.dylanmcdowell.thetowersapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ComposeMessageActivity extends AppCompatActivity {
    EditText topicString;
    EditText messageString;
    EditText recipientString;
    Button send;
    String topic;
    String message;
    //String recipient;
    private static final String FIREBASE_URL = "https://android-chat.firebaseio-demo.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_message);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("message");

        send = (Button) findViewById(R.id.button11);
        topicString = (EditText) findViewById(R.id.editText3);
        recipientString = (EditText) findViewById(R.id.editText9);
        messageString = (EditText) findViewById(R.id.editText4);
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!messageString.equals("")) {
                    // Create our 'model', a Chat object
                    String subject = topicString.getText().toString();
                    String body = messageString.getText().toString();
                    String recipient = recipientString.getText().toString();
                    DatabaseReference myRef = database.getReference(recipient);
                    System.out.println(subject + body);
                    Message chat = new Message(subject, body, recipient);
                    // Create a new, auto-generated child of that chat location, and save our chat data there
                    myRef.push().setValue(chat);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //topicString.setText("");
                    //messageString.setText("");
                }
            }
        });
    }
}
