package com.example.dylanmcdowell.thetowersapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InboxActivity extends AppCompatActivity {
    Button sampleMessage;
    //Message message;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        textView = (TextView) findViewById(R.id.textView9);
        //Query query = new Query().endAt("z");
        mDatabase.child("Users").child("Sam Ciavardini").child("messages").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        dataSnapshot.getChildren();
                        Iterable ayy;
                        ayy = dataSnapshot.getChildren();
                        Message message = dataSnapshot.getValue(Message.class);
                        //String wat = message.getSubject();
                        textView.setText(message.getSubject());
                        // .
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //Log.w(TAG4, "getUser:onCancelled", databaseError.toException());
                    }
                });
        //String ayy = message.getSubject();
        //textView.setText(ayy);

        sampleMessage = (Button) findViewById(R.id.button10);
        sampleMessage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.VIEWMESSAGE");
                startActivity(intent);
            }
        });
    }
}
