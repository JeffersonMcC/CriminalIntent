package com.bignerdranch2nded.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/*
-this class will inflate the activity_crime.xml view
-once this class inflates that view, this class will use the the FragmentManager to inflate the content_crime view
-content_crime will define the spot where the CrimeFragment's view will appear
-the CrimeFragment's view is fragment_crime
-the CrimeFragment will then call an instance of Crime
-the CrimeFragment will make changes to the EditText that is appears in fragment_crime by using a method in the Crime class
*/
public class CrimeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);   /*ask the FragmentManager for the fragment with a
                                                                             container view ID*/

        if (fragment == null) {
            fragment = new CrimeFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

}
