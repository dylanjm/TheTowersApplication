package com.example.dylanmcdowell.thetowersapplication;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PendingRequestsActivity extends AppCompatActivity {
    //Element Variables
    ListView listView;
    Context context;
    final List<Request> requests = new ArrayList<>();

    /**************************************************
     * ON RESUME
     **************************************************/
    @Override
    protected void onResume(){
        super.onResume();
        requests.clear();
        generateList();
        displayList();
    }

    /**************************************************
     * GENERATE LIST
     **************************************************/
    void generateList(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Requests").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Request request;
                        int eCount = 0;

                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            request = child.getValue(Request.class);
                            request.setKey(child.getKey());
                            if(request.getIsEmergency()) {
                                requests.add(0, request);
                                eCount++;
                            }
                            else
                                requests.add(eCount, request);
                        }

                        displayList();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("getUser:onCancelled");
                    }
                });
    }

    /**************************************************
     * DISPLAY LIST
     **************************************************/
    void displayList(){
        context = getApplicationContext();
        CustomListAdapter customAdapter = new CustomListAdapter(context, R.layout.maitenence_layout, requests);
        customAdapter.notifyDataSetChanged();
        listView.setAdapter(customAdapter);
    }

    /**************************************************
     * INITALIZE BUTTONS
     **************************************************/
    void initializeButtons(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                Request bundleRequest = requests.get(position);
                bundle.putString("subject", bundleRequest.getSubject());
                bundle.putString("body", bundleRequest.getBody());
                bundle.putString("sender", bundleRequest.getSender());
                bundle.putString("timeStamp", bundleRequest.getTimeStamp());
                bundle.putString("roomNum", bundleRequest.getAptNumber());
                bundle.putBoolean("isEmergency", bundleRequest.getIsEmergency());
                bundle.putString("key", bundleRequest.getKey());
                Intent intent = new Intent("android.intent.action.MAINTENANCEREQUEST");
                intent.putExtras(bundle);
                //Start Maintenance Request Activity
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return false;
            }
        });
    }

    /**************************************************
     * ON CREATE - activity runs from here
     **************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getApplicationContext().getResources();
        setContentView(R.layout.activity_pending_requests);
        getWindow().getDecorView().setBackgroundColor(res.getColor(R.color.darkTheme));

        listView = (ListView) findViewById(R.id.listView);
        initializeButtons();
    }
}
