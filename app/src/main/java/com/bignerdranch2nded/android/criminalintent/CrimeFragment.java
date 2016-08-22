package com.bignerdranch2nded.android.criminalintent;

/**
 * Created by Jeffrow on 8/2/2016.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

//this class's view will be fragment_crime.xml
//this class is a fragment
//this class will be called by CrimeActivity
//CrimeFragment is a controller object that interacts with model and view objects.
public class CrimeFragment extends Fragment {
    private Crime mCrime;   //this member variable will hold an isolated instance of Crime
    private EditText mTitleField;

    @Override
    public void onCreate(Bundle savedInstanceState) { //you configure fragment instance here by you create and configure the
        super.onCreate(savedInstanceState);         //fragment's view in onCreateView(...)
        mCrime = new Crime();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        //1st Parameter: pass in the layout resource ID
        //2nd Parameter: view's parent
        //3rd Parameter: tells the layout inflater to add the
        //              inflated view to view's parent. You
        //              pass in false you will add the view in
        //              the activity's code.

        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }
}
