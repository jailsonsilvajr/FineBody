package com.example.finebody.database.sqlite.entitys;

import android.provider.BaseColumns;

public class Measure implements BaseColumns {

    public static final String TABLE_NAME = "measures";

    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_RIGHT_ARM = "right_arm";
    public static final String COLUMN_LEFT_ARM = "left_arm";
    public static final String COLUMN_WAIST = "waist";
    public static final String COLUMN_HIP = "hip";
    public static final String COLUMN_RIGHT_CALF = "right_calf";
    public static final String COLUMN_LEFT_CALF = "left_calf";
    public static final String COLUMN_ID_STUDENT = "id_student";

    //Query create table Measure
    public static final String SQL_CREATE_TABLE_MEASURE =
            "CREATE TABLE " + Measure.TABLE_NAME + " (" +
                    Measure._ID + " INTEGER PRIMARY KEY," +
                    Measure.COLUMN_DATE + " TEXT," +
                    Measure.COLUMN_WEIGHT + " REAL," +
                    Measure.COLUMN_RIGHT_ARM + " REAL," +
                    Measure.COLUMN_LEFT_ARM + " REAL," +
                    Measure.COLUMN_WAIST + " REAL," +
                    Measure.COLUMN_HIP + " REAL," +
                    Measure.COLUMN_RIGHT_CALF + " REAL," +
                    Measure.COLUMN_LEFT_CALF + " REAL," +
                    Measure.COLUMN_ID_STUDENT + " INTEGER," +
                    "FOREIGN KEY (" + Measure.COLUMN_ID_STUDENT + ") REFERENCES " + Student.TABLE_NAME + " (" + Student._ID + "))";

    //Query delete table Measure
    public static final String SQL_DELETE_TABLE_MEASURE = "DROP TABLE IF EXISTS " + Measure.TABLE_NAME;
}
