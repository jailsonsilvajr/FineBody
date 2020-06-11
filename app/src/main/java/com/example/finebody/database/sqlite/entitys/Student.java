package com.example.finebody.database.sqlite.entitys;

import android.provider.BaseColumns;

public class Student implements BaseColumns {

    public static final String TABLE_NAME = "student";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_GENRE = "genre";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_PATH_PHOTO = "path_photo";
    public static final String COLUMN_PATH_PHOTO1 = "path_photo1";
    public static final String COLUMN_PATH_PHOTO2 = "path_photo2";
    public static final String COLUMN_PATH_PHOTO3 = "path_photo3";
    public static final String COLUMN_PATH_PHOTO4 = "path_photo4";
    public static final String COLUMN_PATH_PHOTO5 = "path_photo5";
    public static final String COLUMN_PATH_PHOTO6 = "path_photo6";
    public static final String COLUMN_PATH_PHOTO7 = "path_photo7";
    public static final String COLUMN_PATH_PHOTO8 = "path_photo8";
    public static final String COLUMN_PATH_PHOTO9 = "path_photo9";
    public static final String COLUMN_PATH_PHOTO10 = "path_photo10";

    //Query create table:
    public static final String SQL_CREATE_TABLE_STUDENT =
            "CREATE TABLE " + Student.TABLE_NAME + " (" +
                    Student._ID + " INTEGER PRIMARY KEY," +
                    Student.COLUMN_NAME + " TEXT," +
                    Student.COLUMN_GENRE + " TEXT," +
                    Student.COLUMN_AGE + " INTEGER," +
                    Student.COLUMN_PATH_PHOTO + " TEXT," +
                    Student.COLUMN_PATH_PHOTO1 + " TEXT," +
                    Student.COLUMN_PATH_PHOTO2 + " TEXT," +
                    Student.COLUMN_PATH_PHOTO3 + " TEXT," +
                    Student.COLUMN_PATH_PHOTO4 + " TEXT," +
                    Student.COLUMN_PATH_PHOTO5 + " TEXT," +
                    Student.COLUMN_PATH_PHOTO6 + " TEXT," +
                    Student.COLUMN_PATH_PHOTO7 + " TEXT," +
                    Student.COLUMN_PATH_PHOTO8 + " TEXT," +
                    Student.COLUMN_PATH_PHOTO9 + " TEXT," +
                    Student.COLUMN_PATH_PHOTO10 + " TEXT)";

    //Query delete table:
    public static final String SQL_DELETE_TABLE_STUDENT =
            "DROP TABLE IF EXISTS " + Student.TABLE_NAME;
}
