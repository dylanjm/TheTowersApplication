package com.example.dylanmcdowell.thetowersapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ComposeMessageActivity extends AppCompatActivity {
    EditText topicString;
    EditText messageString;
    EditText recipientString;
    Button send;
    private static final String FIREBASE_URL = "https://android-chat.firebaseio-demo.com";
    private String senderName;
    User theUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_message);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseAuth fauth = FirebaseAuth.getInstance();
        FirebaseUser user = fauth.getCurrentUser();
        final String username =  user.getEmail().replace(".","*%*");
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Users").child(username).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        theUser = dataSnapshot.getValue(User.class);
                        senderName = theUser.getFirstName() + " " + theUser.getLastName();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });

        send = (Button) findViewById(R.id.button11);
        recipientString = (EditText) findViewById(R.id.editText9);
        topicString = (EditText) findViewById(R.id.editText4);
        messageString = (EditText) findViewById(R.id.editText3);
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!messageString.equals("")) {
                    // Create our 'model', a Chat object
                    System.out.println(senderName);
                    String subject = topicString.getText().toString();
                    String body = messageString.getText().toString();
                    String recipient = recipientString.getText().toString();
                    recipient = recipient.replace(".", "*%*");
                    recipient = "Users/" + recipient + "/messages";
                    DatabaseReference myRef = database.getReference(recipient);

                    System.out.println(subject + body);
                    Message chat = new Message(subject, body, senderName, recipient);
                    // Create a new, auto-generated child of that chat location, and save our chat data there
                    myRef.push().setValue(chat);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    topicString.setText("");
                    messageString.setText("");
                    recipientString.setText("");
                }
            }
        });
    }
}
