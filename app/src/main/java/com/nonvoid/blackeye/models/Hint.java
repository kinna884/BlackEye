package com.nonvoid.blackeye.models;

import com.google.android.gms.maps.model.LatLng;
import com.nonvoid.blackeye.helper.ImageHelper;

import java.io.Serializable;

/**
 * Created by Matt on 6/21/2016.
 */
public class Hint implements Serializable {
    public String id;
    public String title;
    public String description;
    
    public Hint() {
    }

    public Hint(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Hint(String description) {
        this.description = description;
    }

}
