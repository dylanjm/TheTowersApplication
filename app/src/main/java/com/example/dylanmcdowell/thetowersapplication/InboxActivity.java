package com.example.dylanmcdowell.thetowersapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InboxActivity extends AppCompatActivity {
    TextView textView;
    ListView listView;
    ArrayAdapter<Message> listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        FirebaseAuth fauth = FirebaseAuth.getInstance();
        FirebaseUser user = fauth.getCurrentUser();
        final String email =  user.getEmail().replace(".", "*%*");
        final List<Message> messages = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        textView = (TextView) findViewById(R.id.textView9);
        listView = (ListView) findViewById(R.id.listView);

        mDatabase.child("Users").child(email).child("messages").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        Context context = getApplicationContext();

                        Message message;

                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            message = child.getValue(Message.class);
                            messages.add(0, message);
                        }

                        listAdapter = new ArrayAdapter<>(context, R.layout.customlayout, messages);
                        listView.setAdapter(listAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("getUser:onCancelled");
                    }
                });

        mDatabase.child("Public").child("messages").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Context context = getApplicationContext();
                        Message message;

                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            message = child.getValue(Message.class);
                            messages.add(0, message);
                            listView.setAdapter(listAdapter);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("getUser:onCancelled");
                    }
                }
        );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                Message bundleMessage = messages.get(position);

                bundle.putString("subject", bundleMessage.getSubject());
                bundle.putString("body", bundleMessage.getBody());
                bundle.putString("sender", bundleMessage.getSender());
                Intent intent = new Intent("android.intent.action.VIEWMESSAGE");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
