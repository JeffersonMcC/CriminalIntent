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

    public static Intent newIntent(Context packageContext, UUID crimeId){
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

   @Override
    protected Fragment createFragment(){
       UUID crimeId = (UUID)getIntent().getSerializableExtra(EXTRA_CRIME_ID);
       return CrimeFragment.newInstance(crimeId);
   }
}
