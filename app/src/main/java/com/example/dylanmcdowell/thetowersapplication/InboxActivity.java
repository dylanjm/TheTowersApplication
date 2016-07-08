package com.example.dylanmcdowell.thetowersapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    Button sampleMessage;
    //Message message;
    TextView textView;
    ListView listView;
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
        final ArrayAdapter<Message> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messages);

        listView.setAdapter(listAdapter);

        mDatabase.child("Users").child(email).child("messages").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        Message message;
                        ArrayList<Message> messages = new ArrayList<>();



                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            message = child.getValue(Message.class);
                            messages.add(message);
                            System.out.println(message.getSubject());
                            System.out.println(messages.get(0));
                        }

                        System.out.println(messages.get(0).getSubject());
                        System.out.println(messages.get(0).getBody());
                        System.out.println(messages.get(0).getSender());
                        System.out.println(messages.get(1).getBody());


                        message = messages.get(0);
                        textView.setText(message.getSubject());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //Log.w(TAG4, "getUser:onCancelled", databaseError.toException());
                    }
                });
        //String ayy = message.getSubject();
        //textView.setText(ayy);

        //sampleMessage = (Button) findViewById(R.id.button10);
//        sampleMessage.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent intent = new Intent("android.intent.action.VIEWMESSAGE");
//                startActivity(intent);
//            }
//        });
    }
}
