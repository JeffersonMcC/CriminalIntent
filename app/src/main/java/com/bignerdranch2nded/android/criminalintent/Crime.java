package com.bignerdranch2nded.android.criminalintent;

import java.util.UUID;

/**
 * Created by Jeffrow on 8/1/2016.
 */
//an instance of Crime will represent a single office crime
//I think this will be the model layer for CriminalIntent
public class Crime {

    private UUID mId;
    private String mTitle;

    public Crime(){
        //Generate unique identifier
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
