package com.bignerdranch2nded.android.criminalintent.database;

/**
 * Created by Jeffrow on 9/11/2016.
 */
//this class is a simple database schema that states the tables name and columns
public class CrimeDbSchema {
    public static final class CrimeTable{
        /*CrimeTable class only exists to define the String constants needed to describe the moving pieces of your table definition*/
        public static final String NAME = "crimes"; //name of the table in the database

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title"; //can refer to this column as CrimeTable.Cols.TITLE
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
        }
    }
}
