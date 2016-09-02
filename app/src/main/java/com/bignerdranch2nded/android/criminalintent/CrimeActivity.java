package com.bignerdranch2nded.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/*
-this class will inflate the activity_fragmentent.xml view
-once this class inflates that view, this class will use the the FragmentManager to inflate the content_crime view
-content_crime will define the spot where the CrimeFragment's view will appear
-the CrimeFragment's view is fragment_crime
-the CrimeFragment will then call an instance of Crime
-the CrimeFragment will make changes to the EditText that is appears in fragment_crime by using a method in the Crime class
*/
public class CrimeActivity extends SingleFragmentActivity {

   @Override
    protected Fragment createFragment(){
       return new CrimeFragment();
   }
}
