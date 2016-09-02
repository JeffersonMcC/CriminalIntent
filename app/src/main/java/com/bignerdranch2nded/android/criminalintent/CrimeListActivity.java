package com.bignerdranch2nded.android.criminalintent;

/**
 * Created by Jeffrow on 8/27/2016.
 */
import android.support.v4.app.Fragment;

public class CrimeListActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment(){
        return new CrimeListFragment();
    }
}
