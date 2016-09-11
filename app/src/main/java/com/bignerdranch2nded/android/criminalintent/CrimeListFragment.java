package com.bignerdranch2nded.android.criminalintent;

/**
 * Created by Jeffrow on 8/27/2016.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class CrimeListFragment extends Fragment{
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    private static final String TAG = "CrimeListFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
       Log.d(TAG, "onCreateView called");
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView)view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Log.d(TAG, "updateUI() called");
        updateUI();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume started");
        updateUI(); /*trigger a call to updateUI() to reload the crime list. You type this into onResume instead of onStop because you can't
                        assume your activity will be stopped when another activity is front of it. onResume() is the safest place to update
                        a fragment's view*/
    }

    private void updateUI(){
        Log.d(TAG, "updateUI() started");
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        Log.d(TAG, "updateUI: list of crimes obtained");
        if(mAdapter == null){
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else{
            mAdapter.notifyDataSetChanged();
        }

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
            Log.d(TAG, "onClick() method started and newIntent() in CrimeActivity called");
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId()); /*getId is a method in 'Crime.java'. The class
            simply makes a random serializable using 'java.util.UUID;'*/
            Log.d(TAG, "startActivity() called");
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
    }
}
