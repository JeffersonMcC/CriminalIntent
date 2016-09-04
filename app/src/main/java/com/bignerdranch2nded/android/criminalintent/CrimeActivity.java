package com.bignerdranch2nded.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.UUID;

/*
-this class will inflate the activity_fragmentent.xml view
-once this class inflates that view, this class will use the the FragmentManager to inflate the content_crime view
-content_crime will define the spot where the CrimeFragment's view will appear
-the CrimeFragment's view is fragment_crime
-the CrimeFragment will then call an instance of Crime
-the CrimeFragment will make changes to the EditText that is appears in fragment_crime by using a method in the Crime class
*/
public class CrimeActivity extends SingleFragmentActivity {

    private static final String EXTRA_CRIME_ID = "com.bignerdranch2nded.android.criminalintent.crime_id";
    //using the package name prevents name collisions with extras from other apps
    /*An activity may be started from several different places, so you should define keys for extras on the activites that retrieve
    * use them.*/

    //This method will be called by the event listener in 'CrimeListFragment' and it will pass in the UUID
    /*This static method allows me to create an 'Intent' properly configured with the extras 'CrimeActivity' will need*/
    public static Intent newIntent(Context packageContext, UUID crimeId){   //start CrimeFragment with an intent
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        /*This is an explicit intent. An intent is an object that a component can use to communicate with the OS
        First parameter: tells the 'ActivityManager' which application package the activity class can be found in
        Second parameter: specifies the activity class that the ActivityManager should start
        */
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

   @Override    //overriding the 'createFragment()' in 'singFragmentActivity.java'
    protected Fragment createFragment(){
       UUID crimeId = (UUID)getIntent().getSerializableExtra(EXTRA_CRIME_ID);   //this serializable was created above
       return CrimeFragment.newInstance(crimeId);   //this is invoking the
   }
}
