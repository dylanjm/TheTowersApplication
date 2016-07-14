package com.example.dylanmcdowell.thetowersapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignInActivity extends AppCompatActivity {
    //Logistic Variables
    private static final String TAG2 = SignInActivity.class.getSimpleName();
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    //Element Variables
    Button signIn;
    Button createProfile;
    String emailString;
    String passwordString;
    EditText emailText;
    EditText passwordText;
    //Intents
    final Intent intent = new Intent("android.intent.action.MENU");
    final Intent intent2 = new Intent("android.intent.action.CREATEPROFILE");

    /*************************************************
     * VALIDATE FORM - checks for NULL strings
     *************************************************/
    private boolean validateForm() {
        boolean valid = true;

        emailString = emailText.getText().toString();
        if (TextUtils.isEmpty(emailString)) {
            emailText.setError("Required.");
            valid = false;
        } else {
            emailText.setError(null);
        }

        passwordString = passwordText.getText().toString();
        if (TextUtils.isEmpty(passwordString)) {
            passwordText.setError("Required.");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

    /****************************************************
     * INITIALIZE BUTTONS - confirms buttons and display
     ****************************************************/
    void initializeButtons(){
        //Sign In Button
        signIn = (Button) findViewById(R.id.button);
        signIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                emailText = (EditText) findViewById(R.id.editText);
                passwordText = (EditText) findViewById(R.id.editText2);
                if(!validateForm()){
                    Toast.makeText(getApplication(), "All Fields Required",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                //Handle Sign In
                mAuth.signInWithEmailAndPassword(emailString, passwordString);
            }
        });

        //Create Profile Button
        createProfile = (Button) findViewById(R.id.button2);
        createProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Start Create Profile Activity
                startActivity(intent2);
            }
        });
    }

    /*************************************************
     * ON CREATE - Activity runs from here
     *************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_sign_in);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.i(TAG2, "onAuthStateChanged:signed_in:" + user.getUid());
                    //Start Main Activity
                    startActivity(intent);
                } else {
                    Log.i(TAG2, "onAuthStateChanged:signed_out");
                }
            }
        };

        //Create and Display all the buttons
        initializeButtons();
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
