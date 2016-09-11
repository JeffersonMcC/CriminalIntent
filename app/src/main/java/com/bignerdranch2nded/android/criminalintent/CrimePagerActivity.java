package com.bignerdranch2nded.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Jeffrow on 9/10/2016.
 */
public class CrimePagerActivity extends FragmentActivity {
    private static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id";

    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    public static Intent newIntent(Context packageContext, UUID crimeId){
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID)getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        mViewPager = (ViewPager)findViewById(R.id.activity_crime_pager_view_pager); //find the ViewPager in the activity's view

        mCrimes = CrimeLab.get(this).getCrimes();   //get data set from CrimeLab (the list of crimes)
        FragmentManager fragmentManager = getSupportFragmentManager();  //get activity's instance of FragmentManager
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager){   /*set the adapter to be an unnamed instance of
        FragmentStatePage. Creating the FragmentStatePagerAdapter requires the FragmentManager. FragmentStatePagerAdapter is the agent
        managing the conversation with ViewPager. */
            @Override
            public Fragment getItem(int position){ //fetches the Crime instance for the given position in the dataset.
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId()); //create a and return a properly configured CrimeFragment
            }

            @Override
            public int getCount(){ //returns the number of items in the array list
                return mCrimes.size();
            }
        });

        //set the ViewPager's current item to the index of the selected crime
        for(int i = 0; i < mCrimes.size(); i++){    //loop through and check each crime's id
            if(mCrimes.get(i).getId().equals(crimeId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
