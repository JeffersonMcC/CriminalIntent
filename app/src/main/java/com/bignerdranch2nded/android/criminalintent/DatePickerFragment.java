package com.bignerdranch2nded.android.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Jeffrow on 9/10/2016.
 */
/*this fragment will create date picker dialog that will display in a consistent manner across all Android versions using the
AppCompat library. The AlertDialog that will do this will use android.support.v7.app.AlertDialog.
When you use an AlertDialog, wrap it in an instance of DialogFragment (subclass of Fragment).*/
public class DatePickerFragment extends DialogFragment{ //this fragment will be hosted by CrimePagerActivity
//like all fragments, instances of DialogFragment are managed by the FragmentManager of the hosting activity
    public static final String EXTRA_DATE = "com.bignerdranch2nded.android.criminalintent.date";

    private static final String ARG_DATE = "date";

    private DatePicker mDatePicker;

    public static DatePickerFragment newInstance(Date date){    /*creating and setting fragment arguments is typically done in
    newInstance() method that replaces the fragment constructor*/
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        /*DatePickerFragment needs to initialize the DatePicker using the information held in the Date. DatePicker requires integers
        *  for the month, day, and year. Date is more of a timestamp and cannot provide integers like this directly.*/
        Date date = (Date)getArguments().getSerializable(ARG_DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);

        mDatePicker = (DatePicker)v.findViewById(R.id.dialog_date_date_picker);
        mDatePicker.init(year, month, day, null);

        return new AlertDialog.Builder(getActivity())   /*AlertDialog.Builder provides a fluent interface for constructing an
        AlertDialog instance. Passing in Context returns an instance of AlertDialog.Builder*/
                .setView(v) //inflated view from dialog_date layout
                .setTitle(R.string.date_picker_title)   //these are AlertDialog.Builder methods
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                    /*a positive button is what the user should press to accept what dialog presents or to take the dialog's primary
                    action*/
                    @Override
                    public void onClick(DialogInterface dialog, int which){ /*when the user presses the positive button, retrieve the
                    data from the date picker*/
                        int year = mDatePicker.getYear();
                        int month = mDatePicker.getMonth();
                        int day = mDatePicker.getDayOfMonth();
                        Date date = new GregorianCalendar(year, month, day).getTime();
                        sendResult(Activity.RESULT_OK, date);   //makes use of the sendResult method below
                    }
                })
                .create();  //returns the configured AlertDialog instance
    }

    private void sendResult(int resultCode, Date date){ //this sends data to CrimeFragment
        /*this method creates an intent, puts the date on it as an extra, and then calls CrimeFragment.onActivityResult(...)*/
        if(getTargetFragment() == null){
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        //FragmentManger keeps track of the target fragment and request code
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);   //resultCode determine what action to take
       /*Activity.onActivityResult(...) is the method that the ActivityManager calls on the parent activity after the child activity
       dies. After the activity has received the call, the activity's FragmentManager then calls Fragment.onActivityResult() on the
       appropriate fragment.*/
    }
}
