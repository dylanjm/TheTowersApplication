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


public class SignInActivity extends AppCompatActivity {
    private static final String TAG2 = SignInActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    Button signIn;
    Button createProfile;
    String username;
    String password;
    EditText usernameTxt;
    EditText passwordTxt;
    private FirebaseAuth.AuthStateListener mAuthListener;
    final Intent intent = new Intent("android.intent.action.MAIN");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.i(TAG2, "onAuthStateChanged:signed_in:" + user.getUid());
                    startActivity(intent);

                } else {
                    // User is signed out
                    Log.i(TAG2, "onAuthStateChanged:signed_out");
                }
            }
        };

        //Sign In Button
        signIn = (Button) findViewById(R.id.button);
        signIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if ((usernameTxt != null) && (passwordTxt != null)) {
                    // Create a handler to handle the result of the authentication
                    usernameTxt = (EditText) findViewById(R.id.editText);
                    passwordTxt = (EditText) findViewById(R.id.editText2);
                    username = usernameTxt.getText().toString();
                    password = passwordTxt.getText().toString();
                    mAuth.signInWithEmailAndPassword(username, password);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        //User is signed in
                        startActivity(intent);
                        Log.i(TAG2, "AUTHENTICATION SUCCESS!!!!");
                    } else {
                        // User is signed out
                        Log.e(TAG2, "AUTHENTICATION FAILURE!!!!");
                        Toast.makeText(getApplication().getApplicationContext(), "User does not exist.",
                                Toast.LENGTH_LONG).show();                     }
                }
                else
                {
                    Toast.makeText(getApplication().getApplicationContext(), "No user and or password input.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        //Create Profile Button
        createProfile = (Button) findViewById(R.id.button2);
        createProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.CREATEPROFILE");
                startActivity(intent);
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
