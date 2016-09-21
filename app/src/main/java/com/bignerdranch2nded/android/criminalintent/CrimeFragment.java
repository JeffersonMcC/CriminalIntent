package com.bignerdranch2nded.android.criminalintent;

/**
 * Created by Jeffrow on 8/2/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.CheckBox;

import java.util.Date;
import java.util.UUID;

//This is the controller object for an individual crime's detail.
//this class's view will be fragment_crime.xml
//this class is a fragment
//this class will be called by CrimeActivity
//CrimeActivity is the hosting activity
//CrimeFragment is a controller object that interacts with model and view objects.
public class CrimeFragment extends Fragment {

    private static final String ARG_CRIME_ID = "crime-id";  //keys are always static
    private static final String DIALOG_DATE = "DialogDate"; //a constant for DatePickerFragment's tag

    private static final int REQUEST_DATE = 0;  //a constant for the request code used use in setTargetFragment

    private Crime mCrime;   //this member variable will hold an isolated instance of Crime

    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    private static final String TAG = "CrimeFragment";
    //when the user presses a crime in the list, this fragment will be called, and this newInstance method is how it will be called
    public static CrimeFragment newInstance(UUID crimeId) { /*
    Attaching arguments to a fragment must be done after the fragment is created but before it is added to an activity.
    To hit this window, Android programmers follow a convention of adding a static method named newInstance() to the Fragment class.
    This method creates the fragment instance and bundles up and sets its arguments.
    Hosting activity calls newInstance() method rather than constructor directly
    */
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();   //all classes have a default constructor
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { //you configure fragment instance here but you create and configure the
        super.onCreate(savedInstanceState);         //fragment's view in onCreateView(...)
        Log.d(TAG, "onCreate() started");
        /*When a fragment needs to access its arguments, it calls the Fragment method getArguments and then one of the type-specific
          "get" methods of Bundle*/
        UUID crimeId = (UUID)getArguments().getSerializable(ARG_CRIME_ID);
        /*
        Context is like access of android activity to the app's resource. It's called Context because it's just that.
        It hooks the component that has a reference to it to the rest of application environment. The Activity class inherits from
        Context. "this" is an example of calling a Context method
        */
        /*getActivity() is used for fragments. 'getActivity()' in a fragment replaces the times you would use 'this' in an activity.
          'getActivity()' returns the Activity to which the fragment is associated. It get's the context of the Activity*/
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Override
    public void onPause(){
        super.onPause();

        CrimeLab.get(getActivity()).updateCrime(mCrime);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        //1st Parameter: pass in the layout resource ID
        //2nd Parameter: view's parent
        //3rd Parameter: tells the layout inflater to add the inflated view to view's parent. You pass in false you will add the
        //               view in the activity's code.

        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        //Log.d(TAG, "getTitle method called");
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton = (Button)v.findViewById(R.id.crime_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() { //shows DatePickerFragment when the date button is pressed
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                /*setTargetFragment() method accepts the fragment
                that will be the target and a request code just like you send in startActivityForResult(...).
                The FragmentManager keeps track of the target fragment and request code.*/
                dialog.show(manager, DIALOG_DATE); /*a method in the DialogFragment library that displays a dialog. Explicitly creates
                                                     a transaction. DIALOG_DATE is just a tag.*/
            }
        });

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                mCrime.setSolved(isChecked);
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        /*override onActivityResult() to retrieve the extra, set the date on the Crime, and refresh the text of the date button*/
        if(resultCode != Activity.RESULT_OK){   /*Activity.RESULT_OK was the resultCode that was passed into the sendResult() method
         in DatePickerFragment*/
            return;
        }

        if(requestCode == REQUEST_DATE){
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
        }
    }

    private void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
    }
}
