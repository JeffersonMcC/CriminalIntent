package com.bignerdranch2nded.android.criminalintent;

import android.util.Log;

import java.util.UUID;

/**
 * Created by Jeffrow on 8/1/2016.
 */
import java.util.Date;
//an instance of Crime will represent a single office crime
public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate; //represents the data a crime occurred
    private boolean mSolved;    //represents whether the crime has been solved

    private static final String TAG = "Crime";

    public Crime(){
        this(UUID.randomUUID());
    }

    public Crime(UUID id){
        mId = id;
        mDate = new Date(); //sets mDate to the current date
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

    public Date getDate() {
        //Log.d(TAG, "getDate method started");
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
