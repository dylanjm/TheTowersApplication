package com.example.dylanmcdowell.thetowersapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

public class CreateProfileActivity extends AppCompatActivity {
    //Logistic Variables
    private static final String TAG = "theTag";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    final Intent intent = new Intent("android.intent.action.MENU");
    //Element Variables
    EditText emailString;
    EditText passwordString;
    EditText firstNameField;
    EditText lastNameField;
    EditText apartmentField;
    EditText adminIDField;
    Button createProfile;
    //Data Type Variables
    String email;
    String password;
    String firstName;
    String lastName;
    String apartment;
    String adminID;
    String databaseEmail;
    boolean isStaff;
    boolean isLogged = false;

    /*************************************************
     * SHOW POP UP - prompts Staff for ID
     *************************************************/
    private void showPopUp() {
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Administration Identification");
        helpBuilder.setMessage("Only required for The Towers Staff");
        helpBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }

    /*************************************************
     * VALIDATE FORM - checks for NULL strings
     *************************************************/
    private boolean validateForm() {
        boolean valid = true;
        isStaff = true;

        email = emailString.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailString.setError("Required.");
            valid = false;
        } else {
            emailString.setError(null);
        }
        password = passwordString.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordString.setError("Required.");
            valid = false;
        } else {
            passwordString.setError(null);
        }
        firstName = firstNameField.getText().toString();
        if (TextUtils.isEmpty(firstName)) {
            firstNameField.setError("Required.");
            valid = false;
        } else {
            firstNameField.setError(null);
        }
        lastName = lastNameField.getText().toString();
        if (TextUtils.isEmpty(lastName)) {
            lastNameField.setError("Required.");
            valid = false;
        } else {
            lastNameField.setError(null);
        }
        apartment = apartmentField.getText().toString();
        if (TextUtils.isEmpty(apartment)) {
            apartmentField.setError("Required.");
            valid = false;
        } else {
            apartmentField.setError(null);
        }
        adminID = adminIDField.getText().toString();
        if (TextUtils.isEmpty(adminID)) {
            isStaff = false;
        } else if (!adminID.equals("thetowers2016")) {
            adminIDField.setError("Not Staff.");
            isStaff = false;
        }
        return valid;
    }

    /*************************************************
     * HANDLE LOGIN - creates user in FireBase
     *************************************************/
    void handleLogin() {
        //Check our form
        if (!validateForm()) {
            Toast.makeText(getApplication(), "All Fields Required",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        //Sign in the user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(getApplication(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /*************************************************
     * HANDLE DATA STORAGE - store user into FireBase
     *************************************************/
    void handleDataStorage() {
        FirebaseUser user = mAuth.getCurrentUser();
        isLogged = true;
        databaseEmail = user.getEmail().replace(".", "*%*");
        User userInfo = new User(email, firstName, lastName, apartment, isStaff, isLogged);
        String base = "Users/" + databaseEmail;
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(base);
        myRef.setValue(userInfo);
    }

    /*************************************************
     * ON CREATE - The Activity runs from here!
     *************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_create_profile);

        //Checking authState from Firebase
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth mAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    handleDataStorage();
                    startActivity(intent);
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        //Pop up for when adminID is activated
        adminIDField = (EditText) findViewById(R.id.editText13);
        adminIDField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    showPopUp();
                else
                    System.out.println("Admin ID entered...");
            }
        });

        // Create Profile Button
        createProfile = (Button) findViewById(R.id.button13);
        createProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                emailString    = (EditText) findViewById(R.id.editText7);
                passwordString = (EditText) findViewById(R.id.editText8);
                firstNameField = (EditText) findViewById(R.id.editText10);
                lastNameField  = (EditText) findViewById(R.id.editText11);
                apartmentField = (EditText) findViewById(R.id.editText12);
                //Start Login
                handleLogin();
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