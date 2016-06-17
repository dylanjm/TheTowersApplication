package com.example.dylanmcdowell.thetowersapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button signIn;
    Button createProfile;
    String username;
    String password;
    EditText usernameTxt;
    EditText passwordTxt;
    private FirebaseAuth.AuthStateListener mAuthListener;


    void setUsername(String name){
        username = name;
    }

    void setPassword(String pass){
        password = pass;
    }

    String getUsername(){
        return username;
    }

    String getPassword(){
        return password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Sign In Button
        signIn = (Button) findViewById(R.id.button);
        signIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final Intent intent = new Intent("android.intent.action.MAIN");
                // Create a handler to handle the result of the authentication
                usernameTxt = (EditText) findViewById(R.id.editText);
                passwordTxt = (EditText) findViewById(R.id.editText2);
                username = usernameTxt.getText().toString();
                password = passwordTxt.getText().toString();
                mAuth.signInWithEmailAndPassword(username, password);
                mAuthListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            // User is signed in
                            System.out.println("AUTHENTICATION SUCCESS!!!!");
                            startActivity(intent);

                        } else {
                            // User is signed out
                            System.out.println("AUTHENTICATION FAILURE!!!!");
                        }
                    }
                };

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
}
