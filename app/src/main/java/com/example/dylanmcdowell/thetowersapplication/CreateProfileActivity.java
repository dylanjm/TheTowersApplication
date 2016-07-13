package com.example.dylanmcdowell.thetowersapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
    boolean isLogged;

    /*************************************************
     * SHOW POP UP - prompts Staff for ID
     *************************************************/
    private void showPopUp() {
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Administration Identification");
        helpBuilder.setMessage("Only required for The Towers Staff");
        helpBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {}
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
        if(TextUtils.isEmpty(adminID)){
            isStaff = false;
        }
        else if(!adminID.equals("thetowers2016")){
            adminIDField.setError("Not Staff.");
            isStaff = false;
        }

        return valid;
    }

    /*************************************************
     * HANDLE LOGIN - creates user in FireBase
     *************************************************/
    boolean handleLogin(){
        //Atomic Boolean so we can change it wherever
        final AtomicBoolean authState = new AtomicBoolean(false);
        Calendar currentTime = Calendar.getInstance();
        Calendar timeOut = (Calendar)currentTime.clone();
        timeOut.add(Calendar.SECOND, 10);

        System.out.println("Current Time: " + currentTime);
        System.out.println("Time Out: " + timeOut);

        //Check our form
        if (!validateForm()) {
            Toast.makeText(getApplication(), "All Fields Required",
                    Toast.LENGTH_SHORT).show();
            return authState.get();
        }
        //Create User Profile
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        authState.set(true);
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplication(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        while(!authState.get() && currentTime.before(timeOut.getTime())){
            System.out.println("INSIDE WHILE LOOP!");
            System.out.println("Before current time: " + currentTime.before(timeOut));
            try{
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("OUTSIDE WHILE LOOP " + "AUTH STATE: " + authState.get());
        return authState.get();
    }

    /*************************************************
     * HANDLE DATA STORAGE - store user into FireBase
     *************************************************/
    void handleDataStorage(FirebaseUser user){
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

        //Pop up for when adminID is activated
        adminIDField = (EditText) findViewById(R.id.editText13);
        adminIDField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
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
                Intent intent  = new Intent("android.intent.action.MAIN");

                if (handleLogin()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if(user!=null){
                        handleDataStorage(user);
                        startActivity(intent);
                    }

                }
            }
        });
    }
}
