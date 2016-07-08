package com.example.dylanmcdowell.thetowersapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "theTag";
    EditText emailString;
    EditText passwordString;
    EditText firstNameField;
    EditText lastNameField;
    EditText apartmentField;
    Button createProfile;
    String email;
    String password;
    String firstName;
    String lastName;
    String apartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_create_profile);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        createProfile = (Button) findViewById(R.id.button13);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.i(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.i(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        /************************************************************************
         * Save This Comment Block we might have to verify authentication later.
         ************************************************************************/
        // mAuth.createUserWithEmailAndPassword("bobtony@firebase.com", "thetowers");
        //                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
        //                    @Override
        //                    public void onComplete(@NonNull Task<AuthResult> task) {
        //                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
        //                        if (!task.isSuccessful()) {
        //                           Log.d(TAG, "Authentication failed.");
        //                        }
        //                    }
        //                });
        //


        createProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.MAIN");
                // Create a handler to handle the result of the authentication
                try
                {
                    emailString = (EditText) findViewById(R.id.editText7);
                    passwordString = (EditText) findViewById(R.id.editText8);
                    firstNameField = (EditText) findViewById(R.id.editText10);
                    lastNameField = (EditText) findViewById(R.id.editText11);
                    apartmentField = (EditText) findViewById(R.id.editText12);
                    email = emailString.getText().toString();
                    password = passwordString.getText().toString();
                    firstName = firstNameField.getText().toString();
                    lastName = lastNameField.getText().toString();
                    apartment = apartmentField.getText().toString();

                    mAuth.createUserWithEmailAndPassword(email, password);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String uid;
                    String uEmail;


                    FirebaseUser user = mAuth.getCurrentUser();
                    uid = user.getUid();
                    uEmail = user.getEmail().replace(".", "*%*");
                    User userInfo = new User(firstName, lastName, apartment);
                    String base = "Users/" + uEmail;
                    DatabaseReference myRef = database.getReference(base);
                    myRef.setValue(userInfo);
                    //myRef.push().setValue(userInfo);

                    if (user != null) {
                        //User is signed in
                        startActivity(intent);
                        Log.i(TAG, "AUTHENTICATION SUCCESS!!!!");
                    } else {
                        Toast.makeText(getApplication(), "Something went wrong.",
                                Toast.LENGTH_LONG).show();
                        Log.e(TAG, "AUTHENTICATION FAILURE!!!!");
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplication(), "One or more of the required inputs have been left blank.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

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
