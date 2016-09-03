package com.bignerdranch2nded.android.criminalintent;

/**
 * Created by Jeffrow on 8/27/2016.
 */
import android.content.Context;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

//The purpose of this class is to function as a data stash fro Crime
//this class is a singleton. Only one instance of it can be made and it runs as long as the application is still in memory
public class CrimeLab {
    private static CrimeLab sCrimeLab;  //s stands for static variable

    private List<Crime> mCrimes;    //polymorphism. The List will make it easier to write in different implmentations of the list
                                    //interface in the future

    public static CrimeLab get(Context context){    //this line is what makes a singleton.
        if(sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);  //if a single instance of the singleton class has not yet started, then one will be created
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context){
        mCrimes = new ArrayList<>();    //polymorphism in action. Create an array list of unique Crime instances
        for(int i = 0; i < 100; i++){   //create 100 instances of crime
            Crime crime = new Crime();  //Crime is a class. An instance of it is a Crime instance with unique properties that have
                                        //access to Crime methods
            crime.setTitle("Crime #" + i);
            crime.setSolved(i / 2 == 0);
            mCrimes.add(crime);
        }
    }

    public List<Crime> getCrimes(){ //get the list of Crime instances
        return mCrimes;
    }

    public Crime getCrime(UUID id){ //call this method from another class and pass in a serializable argument in order to get
                                    //the details of the crime with the same serializable identifier
                                    //The serializable in this case is a number from 0 to 100 (look in the for loop above)
        for(Crime crime : mCrimes){     //for each crime instance in the list of crimes, if the passed in serializable identifier
                                    // matches the int identifier that was set for a particular crime instance, then return crime object
            if(crime.getId().equals(id)){
                return crime;
            }
        }
        return null;    //return nothing if the passed in serializable didn't match the identifier for any of the Crime objects
    }
}
