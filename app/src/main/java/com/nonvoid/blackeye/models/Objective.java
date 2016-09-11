package com.nonvoid.blackeye.models;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Matt on 9/10/2016.
 */

public class Objective {
    public String id;
    public String eventId;
    public boolean completed = false;
    public double latitude;
    public double longitude;

    public Objective() {
    }

    public boolean isValid() throws VerifyError
    {
        if(latitude == 0)
            throw new VerifyError("Latitude Required");
        if(longitude == 0)
            throw new VerifyError("Longitude Required");

        return true;
    }


    public static void addObjective(Objective objective){
        PutObjective(objective);
    }

    public static void updateObjective(Objective objective){
        PutObjective(objective);
    }

    public static void removeObjective(Objective objective){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child("Objectives").child(objective.eventId).child(objective.id).removeValue();
    }

    private static void PutObjective(Objective objective){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        if(objective.id == null)
            objective.id = database.child("Objectives").child(objective.eventId).push().getKey();

        //Add/Override Event
        database.child("Objectives").child(objective.eventId).child(objective.id).setValue(objective);
    }

    public String toJSON(){
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("id", this.id);
            jsonObject.put("completed", this.completed);
            jsonObject.put("latitude", this.latitude);
            jsonObject.put("longitude", this.longitude);
            jsonObject.put("eventId", this.eventId);

            return jsonObject.toString();

        } catch (JSONException e){
            e.printStackTrace();
            return "";
        }
    }
}
