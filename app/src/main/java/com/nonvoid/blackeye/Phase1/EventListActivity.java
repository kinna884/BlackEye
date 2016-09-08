package com.nonvoid.blackeye.Phase1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nonvoid.blackeye.R;
import com.nonvoid.blackeye.adapters.ImageCardViewViewHolder;
import com.nonvoid.blackeye.models.Event;

public class EventListActivity extends AppCompatActivity {

    FirebaseUser mUser;
    DatabaseReference mEventDB;
    RecyclerView mEventRecycleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mEventDB = FirebaseDatabase.getInstance().getReference().child("Events");

        mEventRecycleList = (RecyclerView)findViewById(R.id.event_recycle_list);
        mEventRecycleList.setHasFixedSize(true);
        mEventRecycleList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Event, ImageCardViewViewHolder> adapter =
                new FirebaseRecyclerAdapter<Event, ImageCardViewViewHolder>(
                        Event.class,
                        R.layout.image_cardview,
                        ImageCardViewViewHolder.class,
                        mEventDB
                ) {
                    @Override
                    protected void populateViewHolder(ImageCardViewViewHolder viewHolder, Event model, int position) {
                        viewHolder.setName(model.name);
                        viewHolder.setDate(model.date);
                    }
                };
        mEventRecycleList.setAdapter(adapter);

        AddFABIfAuthorized();
    }

    private void AddFABIfAuthorized() {
        DatabaseReference AuthDb = FirebaseDatabase.getInstance().getReference().child("contentadmins");
        Query isAuthorized = AuthDb;
        isAuthorized.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String currentUser = mUser.getEmail();
                for (DataSnapshot adminSnapshot:  dataSnapshot.getChildren()) {
                    String admin = adminSnapshot.getValue(String.class);
                    if(admin.equals(currentUser)){
                        enableFab();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("onCancelled", "Failed to read value.", databaseError.toException());
            }
        });
    }

    private void enableFab(){
        Log.d("Admin" , "Event FAB Enabled");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EventListActivity.this, AddEventActivity.class);
                startActivity(i);
            }
        });
    }
}
