package com.example.dylanmcdowell.thetowersapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ComposeMessageActivity extends AppCompatActivity {
    EditText topicString;
    EditText messageString;
    EditText recipientString;
    Spinner spinner;
    ArrayAdapter<User> listAdapter;
    Button send;
    private static final String FIREBASE_URL = "https://android-chat.firebaseio-demo.com";
    private String senderName;
    User theUser;
    User bundleUser;
    String theRecipient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_message);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final List<User> users = new ArrayList<>();

        spinner = (Spinner) findViewById(R.id.spinner);
        FirebaseAuth fauth = FirebaseAuth.getInstance();
        FirebaseUser user = fauth.getCurrentUser();
        final String username =  user.getEmail().replace(".","*%*");
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Users").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        Context context = getApplicationContext();

                        User user;

                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            user = child.getValue(User.class);
                            users.add(user);
                        }

                        listAdapter = new ArrayAdapter<>(context, R.layout.customlayout, users);
                        spinner.setAdapter(listAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("getUser:onCancelled");
                    }
                });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bundleUser = users.get(position);
                theRecipient = bundleUser.getEmail().replace(".","*%*");
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //do stuff
            }
        });

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
        topicString = (EditText) findViewById(R.id.editText4);
        messageString = (EditText) findViewById(R.id.editText3);
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!messageString.equals("")) {
                    // Create our 'model', a Chat object
                    System.out.println(senderName);
                    String subject = topicString.getText().toString();
                    String body = messageString.getText().toString();
                    theRecipient = "Users/" + theRecipient + "/messages";
                    DatabaseReference myRef = database.getReference(theRecipient);

                    System.out.println(subject + body);
                    Message chat = new Message(subject, body, senderName, theRecipient);
                    // Create a new, auto-generated child of that chat location, and save our chat data there
                    myRef.push().setValue(chat);
                    try {
                        Thread.sleep(3000);
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
