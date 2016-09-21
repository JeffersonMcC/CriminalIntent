package com.bignerdranch2nded.android.criminalintent;

/**
 * Created by Jeffrow on 8/27/2016.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class CrimeListFragment extends Fragment{
    private static final String SAVED_SUBTITLE_VISIBLE = "visible";
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private boolean mSubtitleVisible; //a member variable to keep track of the subtitle visibility

    private static final String TAG = "CrimeListFragment";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);    //let the FragmentManager know that CrimeListFragment needs to receive menu callbacks
        /*FragmentManager is responsible for calling Fragment.onCreateOptionsMenu(Menu, MenuInflater) when the
        activity receives its onCreateOptionsMenu(...) callback from the OS. You must explicitly tell the
        FragmentManager that your fragment should receive a call to onCreateOptionsMenu(...)*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
       Log.d(TAG, "onCreateView called");
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView)view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(savedInstanceState != null){ /*if the fragment was recreated (i.e. after a rotation), then there will be a boolean extra tied
        to the outState Bundle.*/
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI(); /*trigger a call to updateUI() to reload the crime list. You type this into onResume instead of onStop because you can't
                        assume your activity will be stopped when another activity is front of it. onResume() is the safest place to update
                        a fragment's view*/
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);  /*this is so the visibility setting of the toolbar (true/false)
        stays consistent across rotation*/
    }

    @Override   //create the menu
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if(mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);  //change the text of this menu item from "Show Subtitle" to "Hide Subtitle"
        } else{
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){    //respond when user presses a menu item
        //if a true value is returned, it indicates no further processing is required
        switch (item.getItemId()){
            case R.id.menu_item_new_crime:  //add a new crime to the list of crimes in the CrimeListFragment
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
                startActivity(intent);
                return true;
            case R.id.menu_item_show_subtitle:
                /*trigger a re-creation of the action items when the user presses on the Show Subtitle action item*/
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();  //declares that the action menu has been changed so should be recreated
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle(){  //subtitle will display the number of crimes in CriminalIntent
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getString(R.string.subtitle_format, crimeCount);  //generate subtitle

        if(!mSubtitleVisible){
            subtitle = null;    //don't show subtitle if the member variable for subtitle visibility is false
        }

        AppCompatActivity activity = (AppCompatActivity)getActivity();  /*the activity that is hosting the CrimeListFragment
        is cast to an AppCompatActivity*/
        activity.getSupportActionBar().setSubtitle(subtitle);   //for legacy reasons, the toolbar is still called "action bar"
    }

    private void updateUI(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if(mAdapter == null){
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else{
            mAdapter.setCrimes(crimes);
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();   /*update the subtitle text when returning to CrimeListActivity from CrimePagerActivity/CrimeFragment*/

    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;

        private Crime mCrime;

        public CrimeHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);

            //Log.d(TAG, "CrimeHolder constructor in CrimeHolder class started");

            mTitleTextView = (TextView)itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView)itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox)itemView.findViewById(R.id.list_item_crime_solved_check_box);
            /*Log.d(TAG, "The resource id for the views for the three things in each list items for each crime was set to their " +
                    "respective member variable");
                    */
        }

        public void bindCrime(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }

        @Override
        public void onClick(View v){
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId()); /*getId is a method in 'Crime.java'. The class
            simply makes a random serializable using 'java.util.UUID;'*/
            startActivity(intent);
            /*
            >> User presses a crime list item
            >> CrimeActivity is started
            >> CrimeActivity is a subclass of SingleFragmentActivity
            >> SingleFragmentActivity executes its onCreate method
            >> the onCreate method uses the fragment that was created in CrimeActivity right before the startActivity() invocation in CrimeListFragment
            >> */
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes){
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position){
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);
        }

        @Override
        public int getItemCount(){
            return mCrimes.size();
        }

        public void setCrimes(List<Crime> crimes){
            mCrimes = crimes;
        }
    }
}
