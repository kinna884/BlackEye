package com.nonvoid.blackeye.Phase1.EventStuff;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.nonvoid.blackeye.adapters.HintCardViewHolderAdapter;
import com.nonvoid.blackeye.models.Event;
import com.nonvoid.blackeye.models.Hint;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewEventActivity extends AppCompatActivity {

    FirebaseUser mUser;
    DatabaseReference mHintsDB;
    RecyclerView mEventRecycleList;
    Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        event = new Event(getIntent().getStringExtra("event"));

        Log.d("ViewEvent", "EventID = " + event.Id );
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mHintsDB = FirebaseDatabase.getInstance().getReference().child("Objectives").child(event.Id);

        mEventRecycleList = (RecyclerView)findViewById(R.id.event_recycle_list);
        mEventRecycleList.setHasFixedSize(true);
        mEventRecycleList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Hint, HintCardViewHolderAdapter> adapter =
                new FirebaseRecyclerAdapter<Hint, HintCardViewHolderAdapter>(
                        Hint.class,
                        R.layout.hint_cardview,
                        HintCardViewHolderAdapter.class,
                        mHintsDB
                ) {
                    @Override
                    protected void populateViewHolder(HintCardViewHolderAdapter viewHolder, Hint model, int position) {
                        viewHolder.setTitle("Temp Title");
                        viewHolder.setBody(model.description);
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
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.view_event_fab);
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewEventActivity.this, AddObjectiveActivity.class);
                i.putExtra("event", event.toJSON());
                startActivity(i);
            }
        });
    }

    public void getObjectives(){

    }
}
