package com.nonvoid.blackeye.models;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Jacob on 9/3/2016.
 */

public class Event {

    //Properties
    public String Id;
    public String name;
    public String description;
    public String imageUrl;
    public String date;


    public Event(){}


    public boolean isValid() throws VerifyError
    {
        if(name == null || name.equals(""))
            throw new VerifyError("Name Required");
        if(description == null || description.equals(""))
            throw new VerifyError("Description Required");
        if(date == null || date.equals(""))
            throw new VerifyError("Date Required");

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        try {
            Date dateFormat = format.parse(date);
            Log.d("EventVerify", dateFormat.toString());
        } catch (ParseException e) {
            throw new VerifyError("Invalid Date Format");
        }

        return true;
    }


    public static void AddEvent(Event event){
        PutEvent(event);
    }

    public static void UpdateEvent(Event event){
        PutEvent(event);
    }

    public static void RemoveEvent(Event event){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child("Events").child(event.Id).removeValue();
    }

    private static void PutEvent(Event event){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        if(event.Id == null)
            event.Id = database.child("Events").push().getKey();

        //Add/Override Event
        database.child("Events").child(event.Id).setValue(event);
    }

}
