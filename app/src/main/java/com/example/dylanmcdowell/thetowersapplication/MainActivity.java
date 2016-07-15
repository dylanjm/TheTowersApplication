package com.example.dylanmcdowell.thetowersapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    //Logistic Variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = MainActivity.class.getSimpleName();
    //Element Variables
    User currentUser;
    Button viewRequests;
    ImageButton submitRequest1;
    ImageButton signOut1;
    ImageButton inbox1;
    ImageButton composeMessage1;

    /**************************************************
     * SET CURRENT USER - retrieves data from firebase
     **************************************************/
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
                            if(currentUser.getIsStaff()){
                                viewRequests.setVisibility(View.VISIBLE);
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

    /**************************************************
     * INITIALIZE BUTTONS - sets up all button listeners
     **************************************************/
    void initializeButtons(){

        //Maintenance Requests Button
        submitRequest1 = (ImageButton) findViewById(R.id.imageButton);
        submitRequest1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent submitRequest = new Intent("android.intent.action.SUBMITREQUEST");
                startActivity(submitRequest);
            }
        });

        //Compose Message Button
        composeMessage1 = (ImageButton) findViewById(R.id.imageButton2);
        composeMessage1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent composeMessage = new Intent("android.intent.action.COMPOSEMESSAGE");
                startActivity(composeMessage);
            }
        });

        //Message Inbox Button
        inbox1 = (ImageButton) findViewById(R.id.imageButton3);
        inbox1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent inbox = new Intent("android.intent.action.INBOX");
                startActivity(inbox);
            }
        });

        //Sign Out Button
        signOut1 = (ImageButton) findViewById(R.id.imageButton4);
        signOut1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                currentUser.signOut();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        //Maintenance Request ListView Button - STAFF ONLY
        viewRequests = (Button) findViewById(R.id.button5);
        viewRequests.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent pendingRequests = new Intent("android.intent.action.PENDINGREQUESTS");
                startActivity(pendingRequests);
            }
        });

    }

    /**************************************************
     * ON CREATE - Activity runs from here
     **************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    setCurrentUser(); //Set up current user
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        //Create all the Buttons
        initializeButtons();
    }

    /**************************************************
     * MANAGE LISTENERS - firebase listeners
     **************************************************/
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