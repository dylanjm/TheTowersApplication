package com.example.dylanmcdowell.thetowersapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
    private FirebaseAuth mAuth;

    //UI elements
    EditText topicText;
    EditText messageText;
    CheckBox checkBox;
    Spinner spinner;
    Button send;
    ArrayAdapter<User> listAdapter;


    private static final String FIREBASE_URL = "https://android-chat.firebaseio-demo.com";
    private String topicString;
    private String messageString;
    private String senderName;
    private User theUser;
    private User recipientUser;
    private String theRecipient;
    private Boolean isPublic = false;
    List<User> users;

    /*************************************************
     * SET CURRENT USER - sets the user
     *************************************************/
    void setCurrentUser() {
        FirebaseUser user = mAuth.getCurrentUser();
        checkBox = (CheckBox) findViewById(R.id.checkBox2);
        if (user != null) {
            final String username = user.getEmail().replace(".", "*%*");
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference();
            userRef.child("Users").child(username).addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            theUser = dataSnapshot.getValue(User.class);
                            if(theUser.getIsStaff()){
                                checkBox.setVisibility(View.VISIBLE);
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("Main Activity - Read Failed! "
                                    + databaseError.toException());
                        }
                    });
        }
    }

    /*************************************************
     * VALIDATE FORM - checks for NULL strings
     *************************************************/
    private boolean validateForm() {
        boolean valid = true;

        topicString = topicText.getText().toString();
        if (TextUtils.isEmpty(topicString)) {
            topicText.setError("Required.");
            valid = false;
        } else {
            topicText.setError(null);
        }

        messageString = messageText.getText().toString();
        if (TextUtils.isEmpty(messageString)) {
            messageText.setError("Required.");
            valid = false;
        } else {
            messageText.setError(null);
        }

        return valid;
    }

    /***********************************************************
     * PUSH MESSAGE - creates requests and sends it to firebase
     ***********************************************************/
    void pushMessage(){
        //Check Fields
        if(!validateForm()){
            Toast.makeText(getApplication(), "All Fields Required",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        isPublic  = checkBox.isChecked();
        senderName = theUser.getFirstName() + " " + theUser.getLastName();
        theRecipient = "Users/" + theRecipient + "/messages";
        DatabaseReference myRef = database.getReference(theRecipient);
        DatabaseReference publicRef = database.getReference("Public");
        Message message = new Message(topicString, messageString, senderName, theRecipient);
        if (isPublic){
            message.setRecipient("Public");
            publicRef.push().setValue(message);
        }
        else {
            myRef.push().setValue(message);
        }

        //Clear Boxes
        Toast.makeText(getApplication(), "Message Sent", Toast.LENGTH_SHORT).show();
        topicText.setText("");
        messageText.setText("");
        checkBox.setChecked(false);
    }

    void generateList(){
        users = new ArrayList<>();
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

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_message);
        mAuth = FirebaseAuth.getInstance();


        send = (Button) findViewById(R.id.button11);
        topicText = (EditText) findViewById(R.id.editText4);
        messageText = (EditText) findViewById(R.id.editText3);
        spinner = (Spinner) findViewById(R.id.spinner);

        setCurrentUser();
        generateList();

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    spinner.setVisibility(buttonView.INVISIBLE);
                }
                else{
                    spinner.setVisibility(buttonView.VISIBLE);
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                recipientUser = users.get(position);
                theRecipient = recipientUser.getEmail().replace(".","*%*");
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //do stuff
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                    pushMessage();
                    topicText.setText("");
                    messageText.setText("");
                }
        });

    }
}
