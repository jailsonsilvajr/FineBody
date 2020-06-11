package com.example.finebody.database.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.finebody.database.sqlite.entitys.Measure;
import com.example.finebody.database.sqlite.entitys.Student;

public class CreateDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "finebody.db";
    private static final int DB_VERSION = 1;

    public CreateDB(Context context){

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Student.SQL_CREATE_TABLE_STUDENT);
        db.execSQL(Measure.SQL_CREATE_TABLE_MEASURE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(Student.SQL_DELETE_TABLE_STUDENT);
        db.execSQL(Measure.SQL_DELETE_TABLE_MEASURE);
        onCreate(db);
    }
}
