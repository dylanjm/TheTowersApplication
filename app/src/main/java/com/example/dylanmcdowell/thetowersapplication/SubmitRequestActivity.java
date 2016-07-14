package com.example.dylanmcdowell.thetowersapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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
    //Logistic Variables
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "theTag";
    //Element Variables
    User currentUser;
    EditText subjectText;
    EditText messageText;
    String subjectString;
    String messageString;
    String time;
    Boolean emergency;
    CheckBox checkbox;
    Button submit;
    String senderName;
    String apt;

    /*************************************************
     * SET CURRENT USER - sets current user
     *************************************************/
    void setCurrentUser() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            final String username = user.getEmail().replace(".", "*%*");
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference();
            userRef.child("Users").child(username).addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            currentUser = dataSnapshot.getValue(User.class);
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

        subjectString = subjectText.getText().toString();
        if (TextUtils.isEmpty(subjectString)) {
            subjectText.setError("Required.");
            valid = false;
        } else {
            subjectText.setError(null);
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
        DatabaseReference myRef = database.getReference("Requests");
        emergency  = checkbox.isChecked();
        time       = new SimpleDateFormat("MM/dd/yyyy_hh:mm a").format(Calendar.getInstance().getTime());
        senderName = currentUser.getFirstName() + " " + currentUser.getLastName();
        apt        = currentUser.getAptNumber();

        Request request = new Request(subjectString, messageString, senderName, emergency, apt, time);
        myRef.push().setValue(request);

        //Clear Boxes
        Toast.makeText(getApplication(), "Request Sent", Toast.LENGTH_SHORT).show();
        subjectText.setText("");
        messageText.setText("");
        checkbox.setChecked(false);
    }

    /*************************************************
     * ON CREATE - activity runs from here
     *************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_submit_request);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    setCurrentUser();
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        //submit button
        submit = (Button) findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                subjectText   = (EditText) findViewById(R.id.editText6);
                messageText   = (EditText) findViewById(R.id.editText5);
                checkbox      = (CheckBox) findViewById(R.id.checkBox);

                pushMessage();
            }
        });
    }

    /*************************************************
     * HANDLE LISTENERS - handles firebase listeners
     *************************************************/
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
