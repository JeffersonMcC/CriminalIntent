package com.bignerdranch2nded.android.criminalintent;

/**
 * Created by Jeffrow on 8/27/2016.
 */
import android.support.v4.app.Fragment;
import android.util.Log;

public class CrimeListActivity extends SingleFragmentActivity{

    private static final String TAG = "CrimeListActivity";

    @Override
    protected Fragment createFragment(){
        Log.d(TAG, "createFragment method started");
        return new CrimeListFragment();
    }
}
