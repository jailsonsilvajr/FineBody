package com.jjsj.finebodyapp.database.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jjsj.finebodyapp.database.sqlite.entitys.Coach;
import com.jjsj.finebodyapp.database.sqlite.entitys.Measure;
import com.jjsj.finebodyapp.database.sqlite.entitys.Student;

public class CreateDB extends SQLiteOpenHelper {

    public static final String DB_NAME = "finebody.db";
    private static final int DB_VERSION = 1;

    public CreateDB(Context context){

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Coach.SQL_CREATE_TABLE_COACH);
        db.execSQL(Student.SQL_CREATE_TABLE_STUDENT);
        db.execSQL(Measure.SQL_CREATE_TABLE_MEASURE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(Coach.SQL_DELETE_TABLE_COACH);
        db.execSQL(Student.SQL_DELETE_TABLE_STUDENT);
        db.execSQL(Measure.SQL_DELETE_TABLE_MEASURE);
        onCreate(db);
    }
}
