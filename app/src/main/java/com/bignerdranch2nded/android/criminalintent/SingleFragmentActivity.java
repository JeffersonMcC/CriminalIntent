package com.bignerdranch2nded.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Jeffrow on 8/27/2016.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity{


    protected abstract Fragment createFragment();   //used to instantiate the fragment
    // Subclasses of 'SingleFragmentActivity' will implement this method to return an instance of the
    // fragment that the activity is hosting
    private static final String TAG = "SingleFragmentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            //Log.i(TAG, "In conditional statement for fragment");
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}
