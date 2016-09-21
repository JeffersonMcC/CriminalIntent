package com.bignerdranch2nded.android.criminalintent;

/**
 * Created by Jeffrow on 8/27/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bignerdranch2nded.android.criminalintent.database.CrimeBaseHelper;
import com.bignerdranch2nded.android.criminalintent.database.CrimeCursorWrapper;
import com.bignerdranch2nded.android.criminalintent.database.CrimeDbSchema;
import com.bignerdranch2nded.android.criminalintent.database.CrimeDbSchema.CrimeTable;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

//The purpose of this class is to function as a data stash fro Crime
//this class is a singleton. Only one instance of it can be made and it runs as long as the application is still in memory
public class CrimeLab {
    private static final String TAG = "CrimeLab";

    private static CrimeLab sCrimeLab;  //s stands for static variable

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context){    //this line is what makes a singleton.
        if(sCrimeLab == null){
            sCrimeLab = new CrimeLab(context); //if a single instance of the singleton class has not yet started, then one will be created
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context){
        mContext = context.getApplicationContext(); //
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
    }

    public void addCrime(Crime c){
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME, null, values);
        //1st argument: is the table you want to insert

    }

    public List<Crime> getCrimes(){ //query all crimes, walk the cursor, and populate a Crime list
        List<Crime> crimes = new ArrayList<>();

        CrimeCursorWrapper cursor = queryCrimes(null, null);

        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {  //isAfterLast() tells you that your pointer is off the end of the dataset
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally{
            cursor.close();
        }
        return crimes;
    }

    public Crime getCrime(UUID id){ //call this method from another class and pass in a serializable argument in order to get
        //the details of the crime with the same serializable identifier
        CrimeCursorWrapper cursor = queryCrimes(
                CrimeTable.Cols.UUID + " = ? ",
                new String[]{ id.toString() }
        );

        try{
            if(cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        } finally{
            cursor.close(); //make sure to make it a habit to write this
        }
    }

    public void updateCrime(Crime crime){
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);

        //update(String, ContentValues, String, String[])
        mDatabase.update(CrimeTable.NAME, values,
                CrimeTable.Cols.UUID + " = ?",
                new String[] {uuidString});
        /*1st argument: table name
        * 2nd argument: ContentValues
        * 3rd argument: a where a clause
        * 4th argument: specify value for the argument in the where clause (the final String[])*/
    }

    /*writes and updates to databases are done with the assistance of a class called ContentValues. Is specifically designed to store the
    kinds of data SQLite can hold.*/
    private static ContentValues getContentValues(Crime crime){
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);

        return values;
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs){ /*the crime id is passed in as parameter so that the
    SQLiteDatabase.query() method knows where to look*/
        /*reading data from SQLite is done using the query() method. A Cursor is a way to look at a table. Database cursors are called
        *cursors because they always have their finger on a particular place in a query. Is an interface that provides random read-write
        * access to the result set returned by a database query.
         */
        /* some of the methods
        public Cursor query(
            String table,
            String[] columns,
            String where,
            String[] whereArgs,
            String groupBy,
            String having,
            String orderBy,
            String limit)
         */
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null, //Columns - null selects all columns
                whereClause,
                whereArgs,
                null,   //groupBy
                null,   //having
                null    //orderBy
        );

        return new CrimeCursorWrapper(cursor);
    }
}
