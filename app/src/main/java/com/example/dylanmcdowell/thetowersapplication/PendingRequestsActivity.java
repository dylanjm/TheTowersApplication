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

public class PendingRequestsActivity extends AppCompatActivity {
    TextView textView;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_requests);

        FirebaseAuth fauth = FirebaseAuth.getInstance();
        FirebaseUser user = fauth.getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        textView = (TextView) findViewById(R.id.textView9);
        listView = (ListView) findViewById(R.id.listView);
        final List<Request> requests = new ArrayList<>();

        mDatabase.child("Requests").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        Context context = getApplicationContext();
                        ArrayAdapter<Request> listAdapter;
                        Request request;
                        int eCount = 0;

                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            request = child.getValue(Request.class);
                            if(request.getIsEmergency()) {
                                requests.add(0, request);
                                eCount++;
                            }
                            else
                                requests.add(eCount, request);
                        }

                        listAdapter = new ArrayAdapter<>(context, R.layout.customlayout, requests);
                        listView.setAdapter(listAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("getUser:onCancelled");
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                //bundle.putString("subject", view.;
                Intent intent = new Intent("android.intent.action.VIEWMESSAGE");
                startActivity(intent);
            }
        });

    }
}
