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
        newId = db.insert(Student.TABLE_NAME, null, getValuesStudent(student));
        db.close();

        if(newId  != -1){

            //Insert in firebase <-------------------------------------------------------------------------------
        }

        return newId;
    }

    //updateStudent
    public boolean updateStudent(Student student){

        SQLiteDatabase db = db_sqlite.getWritableDatabase();
        String whereClause = Student._ID + " = ?";
        String[] whereArgs = {Integer.toString(student.getId())};
        if(db.update(Student.TABLE_NAME, getValuesStudent(student), whereClause, whereArgs) == 0) {

            db.close();
            return false;
        }
        db.close();

        //Update in firebase <-------------------------------------------------------------------------------

        return true;
    }

    //deleteStudent
    public boolean deleteStudent(Student student){

        SQLiteDatabase db = db_sqlite.getWritableDatabase();
        String whereClause = Student._ID + " = ?";
        String[] whereArgs = {Integer.toString(student.getId())};
        if(db.delete(Student.TABLE_NAME, whereClause, whereArgs) == 0) {

            db.close();
            return false;
        }
        db.close();

        //Delete in firebase <-------------------------------------------------------------------------------

        return true;
    }

    //saveMeasure
    public long insertMeasure(Measure measure){

        long newId;
        SQLiteDatabase db = db_sqlite.getWritableDatabase();
        newId = db.insert(Measure.TABLE_NAME, null, getValuesMeasure(measure));
        db.close();

        if(newId != -1){

            //Insert in firebase
        }

        return newId;

    }

    //updateMeasure
    public boolean updateMeasure(Measure measure){

        SQLiteDatabase db = db_sqlite.getWritableDatabase();
        String whereClause = Measure._ID + " = ?";
        String[] whereArgs = {Integer.toString(measure.getId())};
        int result = db.update(Measure.TABLE_NAME, getValuesMeasure(measure), whereClause, whereArgs);
        db.close();

        if(result == 0) return false;
        return true;
    }
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

    private ContentValues getValuesStudent(Student student){

        ContentValues values = new ContentValues();
        values.put(Student.COLUMN_NAME, student.getName());
        values.put(Student.COLUMN_GENRE, student.getGenre());
        values.put(Student.COLUMN_AGE, student.getAge());
        values.put(Student.COLUMN_PATH_PHOTO, student.getPath_photo());
        values.put(Student.COLUMN_PATH_PHOTO1, student.getPath_photo1());
        values.put(Student.COLUMN_PATH_PHOTO2, student.getPath_photo2());
        values.put(Student.COLUMN_PATH_PHOTO3, student.getPath_photo3());
        values.put(Student.COLUMN_PATH_PHOTO4, student.getPath_photo4());
        values.put(Student.COLUMN_PATH_PHOTO5, student.getPath_photo5());
        values.put(Student.COLUMN_PATH_PHOTO6, student.getPath_photo6());
        values.put(Student.COLUMN_PATH_PHOTO7, student.getPath_photo7());
        values.put(Student.COLUMN_PATH_PHOTO8, student.getPath_photo8());
        values.put(Student.COLUMN_PATH_PHOTO9, student.getPath_photo9());
        values.put(Student.COLUMN_PATH_PHOTO10, student.getPath_photo10());
        values.put(Student.COLUMN_ID_COACH, student.getId_coach());

        return values;
    }

    private ContentValues getValuesMeasure(Measure measure){

        ContentValues values = new ContentValues();
        values.put(Measure.COLUMN_DATE, measure.getDate());
        values.put(Measure.COLUMN_WEIGHT, measure.getWeight());
        values.put(Measure.COLUMN_RIGHT_ARM, measure.getRight_arm());
        values.put(Measure.COLUMN_LEFT_ARM, measure.getLeft_arm());
        values.put(Measure.COLUMN_WAIST, measure.getWaist());
        values.put(Measure.COLUMN_HIP, measure.getHip());
        values.put(Measure.COLUMN_RIGHT_CALF, measure.getRight_calf());
        values.put(Measure.COLUMN_LEFT_CALF, measure.getLeft_calf());
        values.put(Measure.COLUMN_ID_STUDENT, measure.getId_student());

        return values;
    }
}
