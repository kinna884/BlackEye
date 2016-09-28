package com.nonvoid.blackeye.Phase1.EventStuff;

import android.content.Intent;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nonvoid.blackeye.R;
import com.nonvoid.blackeye.helper.LogTags;
import com.nonvoid.blackeye.models.Event;

import java.util.ArrayList;
import java.util.List;

public class PairHintsToObjectiveActivity extends AppCompatActivity {

    public ArrayList <Event> eventList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pair_hints_to_objective);
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        Log.d(LogTags.MAIN, database.child("Events").toString());
       // ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, )
        eventList = new ArrayList();
        Spinner eventSpinner = (Spinner) findViewById(R.id.eventSpinner);




    }
    private void getEventsFromDB(ArrayList<Event> eventList) {
        DatabaseReference AuthDb = FirebaseDatabase.getInstance().getReference().child("Events");
        Query receivedEvents = AuthDb;
        receivedEvents.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot eventSnapshot:  dataSnapshot.getChildren()) {
                    Event event = eventSnapshot.getValue(Event.class);
                    PairHintsToObjectiveActivity.this.eventList.add(event);
                    Log.d(LogTags.MAIN, "Name of the Event: " + event.name);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("onCancelled", "Failed to read value.", databaseError.toException());
            }
        });
    }




   public void onClick(View view)
    {


    }



}
