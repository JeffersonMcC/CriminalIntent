package com.bignerdranch2nded.android.criminalintent;

import java.util.UUID;

/**
 * Created by Jeffrow on 8/1/2016.
 */
import java.util.Date;
//an instance of Crime will represent a single office crime
public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate; //represents the data a crime occured
    private boolean mSolved;    //represents whether the crime has been solved

    public Crime(){
        //Generate unique identifier
        mId = UUID.randomUUID();
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
