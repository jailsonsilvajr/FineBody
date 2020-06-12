package com.example.finebody.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finebody.database.sqlite.CreateDB;
import com.example.finebody.database.sqlite.entitys.Measure;
import com.example.finebody.database.sqlite.entitys.Student;

import java.io.File;
import java.util.List;

public class Repository {

    private static Repository repository;
    private static CreateDB db_sqlite;

    private Repository(){}

    public static Repository getInstance(Context context, int id_coach){

        if(repository == null){

            repository = new Repository();

            File dbFile = context.getDatabasePath(CreateDB.DB_NAME);
            if(dbFile.exists()){

                db_sqlite = new CreateDB(context);
            }else{

                //List<Student> listStudents = getStudentsFirebase(id_coach);
                //List<Measure> listMeasures = getMeasuresFirebase();
                //db_sqlite = new CreateDB(context);
                //povoar database sqlite
            }
        }

        return repository;
    }

    public Cursor getRepositoryStudents() {

        Cursor cursor;

        SQLiteDatabase db = db_sqlite.getReadableDatabase();
        cursor = db.query(Student.TABLE_NAME, null, null, null, null, null, null);

        if(cursor != null) cursor.moveToFirst();
        db.close();
        return cursor;
    }

    public Cursor getRepositoryMeasures(int id_student) {

        Cursor cursor;

        String whereClause = Student._ID + " = ?";
        String[] whereArgs = {Integer.toString(id_student)};

        SQLiteDatabase db = db_sqlite.getReadableDatabase();
        cursor = db.query(Measure.TABLE_NAME, null, whereClause, whereArgs, null, null, Measure.COLUMN_DATE);

        if(cursor != null) cursor.moveToFirst();
        db.close();
        return cursor;
    }

    //insertStudent
    public long insertStudent(Student student){

        long newId;
        SQLiteDatabase db = db_sqlite.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Student.COLUMN_NAME, student.getName());
        values.put(Student.COLUMN_GENRE, student.getGenre());
        values.put(Student.COLUMN_AGE, student.getAge());
        values.put(Student.COLUMN_PATH_PHOTO, student.getPath_photo());
        values.put(Student.COLUMN_PATH_PHOTO1, "");
        values.put(Student.COLUMN_PATH_PHOTO2, "");
        values.put(Student.COLUMN_PATH_PHOTO3, "");
        values.put(Student.COLUMN_PATH_PHOTO4, "");
        values.put(Student.COLUMN_PATH_PHOTO5, "");
        values.put(Student.COLUMN_PATH_PHOTO6, "");
        values.put(Student.COLUMN_PATH_PHOTO7, "");
        values.put(Student.COLUMN_PATH_PHOTO8, "");
        values.put(Student.COLUMN_PATH_PHOTO9, "");
        values.put(Student.COLUMN_PATH_PHOTO10, "");

        newId = db.insert(Student.TABLE_NAME, null, values);
        db.close();

        //Insert in firebase <-------------------------------------------------------------------------------

        return newId;
    }

    //updateStudent
    //deleteStudent

    //saveMeasure
    //updateMeasure
    //deleteMeasure

    //getSharedPreferences(id_coach)
    //saveSharedPreferences(id_coach)
    //deleteSharedPreferences(id_coach)

    public static List<Student> getStudentsFirebase(int id_coach){

        return null;
    }

    public static List<Measure> getMeasuresFirebase(){

        return null;
    }
}
