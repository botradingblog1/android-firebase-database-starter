package com.mobile.justmobiledev.firebasedatabasestarter.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by chris on 12/21/2017.
 */

@IgnoreExtraProperties
public class Note {

    public String id;
    public String title;
    public String body;

    public Note() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Note(String id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }
}
