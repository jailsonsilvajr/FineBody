package com.jjsj.finebodyapp.repository;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jjsj.finebodyapp.database.sqlite.CreateDB;
import com.jjsj.finebodyapp.database.sqlite.entitys.Coach;
import com.jjsj.finebodyapp.database.sqlite.entitys.Measure;
import com.jjsj.finebodyapp.database.sqlite.entitys.Student;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Repository {

    private static Repository repository;
    private static SharedPreferences preferences;
    private static CreateDB db_sqlite;

    private static final String keyPreferenceFirstLogin = "keyFirstlogin";

    private Repository(){}

    public static Repository getInstance(Context context){

        if(repository == null){

            repository = new Repository();

            File dbFile = context.getDatabasePath(CreateDB.DB_NAME);
            if(dbFile.exists()){

                context.deleteDatabase(CreateDB.DB_NAME);//REMOVEEEEEEEERRR
                db_sqlite = new CreateDB(context);
                preferences = context.getSharedPreferences(keyPreferenceFirstLogin, Context.MODE_PRIVATE);
            }else{

                //List<Student> listStudents = getStudentsFirebase(id_coach);
                //List<Measure> listMeasures = getMeasuresFirebase();
                db_sqlite = new CreateDB(context);
                //povoar database sqlite
            }
        }

        return repository;
    }

    public void insertIdCoach(String id){

        SQLiteDatabase database = db_sqlite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Coach.NAME_COLUMN_ID_FIREBASE, id);
        database.insert(Coach.TABLE_NAME, null, values);
        database.close();

        setPreferenceFirstLogin(true);
    }

    public MutableLiveData<Coach> getCoach(){

        MutableLiveData<Coach> result = new MutableLiveData<>();
        Cursor cursor;
        SQLiteDatabase database = db_sqlite.getReadableDatabase();
        cursor = database.query(Coach.TABLE_NAME, null, null, null, null, null, null);
        Coach coach = new Coach();
        if(cursor.moveToFirst()){

            coach.setId(cursor.getLong(0));
            coach.setIdCoach(cursor.getString(1));
        }
        result.setValue(coach);
        return result;
    }

    private void setPreferenceFirstLogin(boolean x){

        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(keyPreferenceFirstLogin, x);
        editor.apply();
    }

    private boolean getPreferenceFirstLogin(){

        return preferences.getBoolean(keyPreferenceFirstLogin, false);
    }

    public MutableLiveData<List<Student>> getStudentsRepository(Coach coach) {

        final MutableLiveData<List<Student>> result = new MutableLiveData<>();
        final List<Student> studentsList = new ArrayList<>();

        if(getPreferenceFirstLogin()){

            setPreferenceFirstLogin(false);
            //Firebase
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            CollectionReference collectionReference = firebaseFirestore.collection("students");
            Query query = collectionReference.whereEqualTo("id_coach", coach.getIdCoach());
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if(task.isSuccessful()){

                        for(QueryDocumentSnapshot documentSnapshot : task.getResult()){

                            Student student = new Student();
                            student.setId(Long.parseLong(documentSnapshot.get("id").toString()));
                            student.setName(documentSnapshot.get("name").toString());
                            student.setGenre(documentSnapshot.get("genre").toString());
                            student.setAge(Integer.parseInt(documentSnapshot.get("age").toString()));
                            student.setPath_photo(documentSnapshot.get("path_photo").toString());
                            student.setPath_photo1(documentSnapshot.get("path_photo1").toString());
                            student.setPath_photo2(documentSnapshot.get("path_photo2").toString());
                            student.setPath_photo3(documentSnapshot.get("path_photo3").toString());
                            student.setPath_photo4(documentSnapshot.get("path_photo4").toString());
                            student.setPath_photo5(documentSnapshot.get("path_photo5").toString());
                            student.setPath_photo6(documentSnapshot.get("path_photo6").toString());
                            student.setPath_photo7(documentSnapshot.get("path_photo7").toString());
                            student.setPath_photo8(documentSnapshot.get("path_photo8").toString());
                            student.setPath_photo9(documentSnapshot.get("path_photo9").toString());
                            student.setPath_photo10(documentSnapshot.get("path_photo10").toString());
                            student.setId_coach(documentSnapshot.get("id_coach").toString());

                            studentsList.add(student);
                        }
                    }
                    result.setValue(studentsList);
                }
            });
        }else{

            //SQLite
            Cursor cursor;
            SQLiteDatabase database = db_sqlite.getReadableDatabase();
            String whereClause = Coach._ID + " = ?";
            String[] whereArgs = {Long.toString(coach.getId())};
            cursor = database.query(Student.TABLE_NAME, null, whereClause, whereArgs, null, null, null);
            if(cursor.moveToFirst()){

                do{

                    Student newStudent = new Student();
                    newStudent.setId(Long.getLong(cursor.getString(0)));
                    newStudent.setName(cursor.getString(1));
                    newStudent.setGenre(cursor.getString(2));
                    newStudent.setAge(Integer.getInteger(cursor.getString(3)));
                    newStudent.setPath_photo(cursor.getString(4));
                    newStudent.setPath_photo1(cursor.getString(5));
                    newStudent.setPath_photo2(cursor.getString(6));
                    newStudent.setPath_photo3(cursor.getString(7));
                    newStudent.setPath_photo4(cursor.getString(8));
                    newStudent.setPath_photo5(cursor.getString(9));
                    newStudent.setPath_photo6(cursor.getString(10));
                    newStudent.setPath_photo7(cursor.getString(11));
                    newStudent.setPath_photo8(cursor.getString(12));
                    newStudent.setPath_photo9(cursor.getString(13));
                    newStudent.setPath_photo10(cursor.getString(14));
                    newStudent.setId_coach(cursor.getString(15));

                    studentsList.add(newStudent);

                }while (cursor.moveToNext());
                database.close();
            }
            result.setValue(studentsList);
        }

        return result;
    }

    public long insertStudentRepository(Student student){

        long newId;
        SQLiteDatabase db = db_sqlite.getWritableDatabase();
        newId = db.insert(Student.TABLE_NAME, null, getValuesStudent(student));
        db.close();
        return newId;
    }

    public boolean updateStudentRepository(Student student){

        SQLiteDatabase db = db_sqlite.getWritableDatabase();
        String whereClause = Student._ID + " = ?";
        String[] whereArgs = {Long.toString(student.getId())};
        int result = db.update(Student.TABLE_NAME, getValuesStudent(student), whereClause, whereArgs);
        db.close();

        if(result == 0) return false;
        return true;
    }

    public boolean deleteStudentRepository(Student student){

        SQLiteDatabase db = db_sqlite.getWritableDatabase();
        String whereClause = Student._ID + " = ?";
        String[] whereArgs = {Long.toString(student.getId())};
        int result = db.delete(Student.TABLE_NAME, whereClause, whereArgs);
        db.close();

        if(result == 0)  return false;
        return true;
    }

    public MutableLiveData<List<Measure>> getMeasuresRepository(Student student) {


        return null;
    }

    public long insertMeasureRepository(Measure measure){

        long newId;
        SQLiteDatabase db = db_sqlite.getWritableDatabase();
        newId = db.insert(Measure.TABLE_NAME, null, getValuesMeasure(measure));
        db.close();
        return newId;

    }

    public boolean updateMeasureRepository(Measure measure){

        SQLiteDatabase db = db_sqlite.getWritableDatabase();
        String whereClause = Measure._ID + " = ?";
        String[] whereArgs = {Long.toString(measure.getId())};
        int result = db.update(Measure.TABLE_NAME, getValuesMeasure(measure), whereClause, whereArgs);
        db.close();

        if(result == 0) return false;
        return true;
    }

    public boolean deleteMeasureRepository(Measure measure){

        SQLiteDatabase db = db_sqlite.getWritableDatabase();
        String whereClause = Measure._ID + " = ?";
        String[] whereArgs = {Long.toString(measure.getId())};
        int result = db.delete(Measure.TABLE_NAME, whereClause, whereArgs);
        db.close();

        if(result == 0) return false;
        return true;
    }

    public MutableLiveData<String> checkCredentials(String email, String password){ //Only check email and password

        final MutableLiveData<String> result = new MutableLiveData<>();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) result.setValue(task.getResult().getUser().getUid());
                        else result.setValue(null);
                    }
                });

        return result;
    }

    public MutableLiveData<String> doRegister(String email, String password){

        final MutableLiveData<String> idCoach = new MutableLiveData<>();
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            String id = task.getResult().getUser().getUid();
                            idCoach.setValue(id);
                        }else{

                            idCoach.setValue(null);
                        }
                    }
                });

        return idCoach;
    }

    public void doLogout(Context context){

        context.deleteDatabase(CreateDB.DB_NAME);
        setPreferenceFirstLogin(true);
    }

    private ContentValues getValuesStudent(Student student){

        ContentValues values = new ContentValues();
        if(student.getId() != 0) values.put(Student._ID, student.getId());
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
