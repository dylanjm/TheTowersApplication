package com.example.dylanmcdowell.thetowersapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button submitRequest;
    Button signOut;
    Button inbox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        submitRequest = (Button) findViewById(R.id.button3);
        submitRequest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.SUBMITREQUEST");
                startActivity(intent);
            }
        });

        inbox = (Button) findViewById(R.id.button4);
        inbox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.COMPOSEMESSAGE");
                startActivity(intent);
            }
        });

        signOut = (Button) findViewById(R.id.button6);
        signOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
    }

 }