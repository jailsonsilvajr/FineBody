package com.jjsj.finebodyapp.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jjsj.finebodyapp.database.sqlite.CreateDB;
import com.jjsj.finebodyapp.database.sqlite.entitys.Coach;
import com.jjsj.finebodyapp.database.sqlite.entitys.Measure;
import com.jjsj.finebodyapp.database.sqlite.entitys.Student;
import com.jjsj.finebodyapp.preferences.PreferenceFirstLogin;
import com.jjsj.finebodyapp.preferences.PreferenceLogged;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repository {

    private static Repository repository;
    private static CreateDB db_sqlite;
    private static PreferenceLogged preferenceLogged;
    private static PreferenceFirstLogin preferenceFirstLogin;

    private Repository(){}

    public static Repository getInstance(Context context){

        if(repository == null){

            repository = new Repository();
            preferenceLogged = new PreferenceLogged(context);
            preferenceFirstLogin = new PreferenceFirstLogin(context);
            db_sqlite = new CreateDB(context);
        }

        return repository;
    }

    public void insertIdCoach(String id_firebase){

        SQLiteDatabase database = db_sqlite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Coach.NAME_COLUMN_ID_FIREBASE, id_firebase);
        database.insert(Coach.TABLE_NAME, null, values);
        database.close();
    }

    public MutableLiveData<Coach> getCoach(){

        MutableLiveData<Coach> result = new MutableLiveData<>();
        Cursor cursor;
        SQLiteDatabase database = db_sqlite.getReadableDatabase();
        cursor = database.query(Coach.TABLE_NAME, null, null, null, null, null, null);
        Coach coach = new Coach();
        if(cursor.moveToFirst()){

            coach.setId_sqlite(cursor.getLong(cursor.getColumnIndex(Coach.NAME_COLUMN_ID_SQLITE)));
            coach.setIdCoachFirebase(cursor.getString(cursor.getColumnIndex(Coach.NAME_COLUMN_ID_FIREBASE)));
        }
        result.setValue(coach);
        return result;
    }

    public MutableLiveData<List<Student>> getStudentsRepository(Coach coach) {

        final MutableLiveData<List<Student>> result = new MutableLiveData<>();
        final List<Student> studentsList = new ArrayList<>();

        if(preferenceFirstLogin.getPreference()){

            changePreferenceFirstLogin();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(Student.TABLE_NAME)
                    .whereEqualTo(Student.COLUMN_ID_COACH_FIREBASE, coach.getId_firebase())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if(task.isSuccessful()){

                                for(QueryDocumentSnapshot document : task.getResult()){

                                    Student student = new Student();
                                    student.setId_sqlite(Long.parseLong(document.get(Student.COLUMN_ID_SQLITE).toString()));
                                    student.setId_firebase(document.getId());
                                    student.setId_coach_sqlite(Long.parseLong(document.get(Student.COLUMN_ID_COACH_SQLITE).toString()));
                                    student.setId_coach_firebase(document.get(Student.COLUMN_ID_COACH_FIREBASE).toString());
                                    student.setName(document.get(Student.COLUMN_NAME).toString());
                                    student.setGenre(document.get(Student.COLUMN_GENRE).toString());
                                    student.setAge(Integer.parseInt(document.get(Student.COLUMN_AGE).toString()));
                                    student.setPath_photo(document.get(Student.COLUMN_PATH_PHOTO).toString());
                                    student.setPath_photo1(document.get(Student.COLUMN_PATH_PHOTO1).toString());
                                    student.setPath_photo2(document.get(Student.COLUMN_PATH_PHOTO2).toString());
                                    student.setPath_photo3(document.get(Student.COLUMN_PATH_PHOTO3).toString());
                                    student.setPath_photo4(document.get(Student.COLUMN_PATH_PHOTO4).toString());
                                    student.setPath_photo5(document.get(Student.COLUMN_PATH_PHOTO5).toString());
                                    student.setPath_photo6(document.get(Student.COLUMN_PATH_PHOTO6).toString());
                                    student.setPath_photo7(document.get(Student.COLUMN_PATH_PHOTO7).toString());
                                    student.setPath_photo8(document.get(Student.COLUMN_PATH_PHOTO8).toString());
                                    student.setPath_photo9(document.get(Student.COLUMN_PATH_PHOTO9).toString());
                                    student.setPath_photo10(document.get(Student.COLUMN_PATH_PHOTO10).toString());

                                    studentsList.add(student);
                                }
                            }
                            for(int i = 0; i < studentsList.size(); i++) insertStudentRepository(studentsList.get(i));
                            result.setValue(studentsList);
                        }
                    });
        }else{

            //SQLite
            Cursor cursor;
            SQLiteDatabase database = db_sqlite.getReadableDatabase();
            cursor = database.query(Student.TABLE_NAME, null, null, null, null, null, null);
            if(cursor.getCount() > 0){

                cursor.moveToFirst();
                do{
                    Student newStudent = new Student();

                    newStudent.setId_sqlite(Long.parseLong(cursor.getString(cursor.getColumnIndex(Student.COLUMN_ID_SQLITE))));
                    newStudent.setId_firebase(cursor.getString(cursor.getColumnIndex(Student.COLUMN_ID_FIREBASE)));
                    newStudent.setId_coach_sqlite(Long.parseLong(cursor.getString(cursor.getColumnIndex(Student.COLUMN_ID_COACH_SQLITE))));
                    newStudent.setId_coach_firebase(cursor.getString(cursor.getColumnIndex(Student.COLUMN_ID_COACH_FIREBASE)));
                    newStudent.setName(cursor.getString(cursor.getColumnIndex(Student.COLUMN_NAME)));
                    newStudent.setGenre(cursor.getString(cursor.getColumnIndex(Student.COLUMN_GENRE)));
                    newStudent.setAge(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Student.COLUMN_AGE))));
                    newStudent.setPath_photo(cursor.getString(cursor.getColumnIndex(Student.COLUMN_PATH_PHOTO)));
                    newStudent.setPath_photo1(cursor.getString(cursor.getColumnIndex(Student.COLUMN_PATH_PHOTO1)));
                    newStudent.setPath_photo2(cursor.getString(cursor.getColumnIndex(Student.COLUMN_PATH_PHOTO2)));
                    newStudent.setPath_photo3(cursor.getString(cursor.getColumnIndex(Student.COLUMN_PATH_PHOTO3)));
                    newStudent.setPath_photo4(cursor.getString(cursor.getColumnIndex(Student.COLUMN_PATH_PHOTO4)));
                    newStudent.setPath_photo5(cursor.getString(cursor.getColumnIndex(Student.COLUMN_PATH_PHOTO5)));
                    newStudent.setPath_photo6(cursor.getString(cursor.getColumnIndex(Student.COLUMN_PATH_PHOTO6)));
                    newStudent.setPath_photo7(cursor.getString(cursor.getColumnIndex(Student.COLUMN_PATH_PHOTO7)));
                    newStudent.setPath_photo8(cursor.getString(cursor.getColumnIndex(Student.COLUMN_PATH_PHOTO8)));
                    newStudent.setPath_photo9(cursor.getString(cursor.getColumnIndex(Student.COLUMN_PATH_PHOTO9)));
                    newStudent.setPath_photo10(cursor.getString(cursor.getColumnIndex(Student.COLUMN_PATH_PHOTO10)));

                    studentsList.add(newStudent);
                }while (cursor.moveToNext());
            }
            database.close();
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
        String whereClause = Student.COLUMN_ID_SQLITE + " = ?";
        String[] whereArgs = {Long.toString(student.getId_sqlite())};
        int result = db.update(Student.TABLE_NAME, getValuesStudent(student), whereClause, whereArgs);
        db.close();

        if(result == 0) return false;
        return true;
    }

    public boolean deleteStudentRepository(Student student){

        SQLiteDatabase db = db_sqlite.getWritableDatabase();
        String whereClause = Student.COLUMN_ID_SQLITE + " = ?";
        String[] whereArgs = {Long.toString(student.getId_sqlite())};
        int result = db.delete(Student.TABLE_NAME, whereClause, whereArgs);
        db.close();

        if(result == 0)  return false;
        return true;
    }

    public MutableLiveData<List<Measure>> getMeasuresRepository(Student student) {

        final MutableLiveData<List<Measure>> result = new MutableLiveData<>();
        final List<Measure> measureList = new ArrayList<>();
        Cursor cursor;
        SQLiteDatabase database = db_sqlite.getReadableDatabase();
        String whereArg = Measure.COLUMN_ID_STUDENT_SQLITE + " = ?";
        String[] whereClause = {Long.toString(student.getId_sqlite())};
        cursor = database.query(Measure.TABLE_NAME, null, whereArg, whereClause, null, null, null);
        if(cursor.getCount() > 0){

            cursor.moveToFirst();
            do{

                Measure newMeasure = new Measure();
                newMeasure.setId_sqlite(Long.parseLong(cursor.getString(cursor.getColumnIndex(Measure.COLUMN_ID_SQLITE))));
                newMeasure.setId_firebase(cursor.getString(cursor.getColumnIndex(Measure.COLUMN_ID_FIREBASE)));
                newMeasure.setId_student_sqlite(Long.parseLong(cursor.getString(cursor.getColumnIndex(Measure.COLUMN_ID_STUDENT_SQLITE))));
                newMeasure.setId_student_firebase(cursor.getString(cursor.getColumnIndex(Measure.COLUMN_ID_STUDENT_FIREBASE)));
                newMeasure.setDate(cursor.getString(cursor.getColumnIndex(Measure.COLUMN_DATE)));
                newMeasure.setWeight(Float.parseFloat(cursor.getString(cursor.getColumnIndex(Measure.COLUMN_WEIGHT))));
                newMeasure.setRight_arm(Float.parseFloat(cursor.getString(cursor.getColumnIndex(Measure.COLUMN_RIGHT_ARM))));
                newMeasure.setLeft_arm(Float.parseFloat(cursor.getString(cursor.getColumnIndex(Measure.COLUMN_LEFT_ARM))));
                newMeasure.setWaist(Float.parseFloat(cursor.getString(cursor.getColumnIndex(Measure.COLUMN_WAIST))));
                newMeasure.setHip(Float.parseFloat(cursor.getString(cursor.getColumnIndex(Measure.COLUMN_HIP))));
                newMeasure.setRight_calf(Float.parseFloat(cursor.getString(cursor.getColumnIndex(Measure.COLUMN_RIGHT_CALF))));
                newMeasure.setLeft_calf(Float.parseFloat(cursor.getString(cursor.getColumnIndex(Measure.COLUMN_LEFT_CALF))));

                measureList.add(newMeasure);
            }while(cursor.moveToNext());
            result.setValue(measureList);
        }else{

            //Firebase
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(Measure.TABLE_NAME)
                    .whereEqualTo(Measure.COLUMN_ID_STUDENT_FIREBASE, student.getId_firebase())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if(task.isSuccessful()){

                                for(QueryDocumentSnapshot document : task.getResult()){

                                    Measure newMeasure = new Measure();
                                    newMeasure.setId_sqlite(Long.parseLong(document.get(Measure.COLUMN_ID_SQLITE).toString()));
                                    newMeasure.setId_firebase(document.getId());
                                    newMeasure.setId_student_sqlite(Long.parseLong(document.get(Measure.COLUMN_ID_STUDENT_SQLITE).toString()));
                                    newMeasure.setId_student_firebase(document.get(Measure.COLUMN_ID_STUDENT_FIREBASE).toString());
                                    newMeasure.setDate(document.get(Measure.COLUMN_DATE).toString());
                                    newMeasure.setWeight(Float.parseFloat(document.get(Measure.COLUMN_WEIGHT).toString()));
                                    newMeasure.setRight_arm(Float.parseFloat(document.get(Measure.COLUMN_RIGHT_ARM).toString()));
                                    newMeasure.setLeft_arm(Float.parseFloat(document.get(Measure.COLUMN_LEFT_ARM).toString()));
                                    newMeasure.setWaist(Float.parseFloat(document.get(Measure.COLUMN_WAIST).toString()));
                                    newMeasure.setHip(Float.parseFloat(document.get(Measure.COLUMN_HIP).toString()));
                                    newMeasure.setRight_calf(Float.parseFloat(document.get(Measure.COLUMN_RIGHT_CALF).toString()));
                                    newMeasure.setLeft_calf(Float.parseFloat(document.get(Measure.COLUMN_LEFT_CALF).toString()));

                                    measureList.add(newMeasure);
                                }
                                for(int i = 0; i < measureList.size(); i++) insertMeasureRepository(measureList.get(i));
                                result.setValue(measureList);
                            }
                        }
                    });
        }
        database.close();
        return result;
    }

    public MutableLiveData<Measure> insertMeasureRepository(Measure measure){

        final MutableLiveData<Measure> result = new MutableLiveData<>();
        long newId;

        SQLiteDatabase db = db_sqlite.getWritableDatabase();
        newId = db.insert(Measure.TABLE_NAME, null, getValuesMeasure(measure));
        db.close();

        if(newId > 0){

            measure.setId_sqlite(newId);
            FirebaseFirestore dbFirebase = FirebaseFirestore.getInstance();
            dbFirebase.collection(Measure.TABLE_NAME)
                    .add(getMapMeasure(measure))
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            measure.setId_firebase(documentReference.getId());
                            updateMeasureRepository(measure);
                            result.setValue(measure);
                        }
                    });
        }
        return result;
    }

    public boolean updateMeasureRepository(Measure measure){

        SQLiteDatabase db = db_sqlite.getWritableDatabase();
        String whereClause = Measure.COLUMN_ID_SQLITE + " = ?";
        String[] whereArgs = {Long.toString(measure.getId_sqlite())};
        int result = db.update(Measure.TABLE_NAME, getValuesMeasure(measure), whereClause, whereArgs);
        db.close();

        if(result == 0) return false;
        else{

            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseFirestore.collection(Measure.TABLE_NAME).document(measure.getId_firebase())
                    .set(getMapMeasure(measure));
        }
        return true;
    }

    public boolean deleteMeasureRepository(Measure measure){

        SQLiteDatabase db = db_sqlite.getWritableDatabase();
        String whereClause = Measure.COLUMN_ID_SQLITE + " = ?";
        String[] whereArgs = {Long.toString(measure.getId_sqlite())};
        int result = db.delete(Measure.TABLE_NAME, whereClause, whereArgs);
        db.close();

        if(result == 0) return false;
        else {

            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseFirestore.collection(Measure.TABLE_NAME).document(measure.getId_firebase())
                    .delete();
            return true;
        }
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

    public void deleteDatabaseSQLite(Context context){

        context.deleteDatabase(CreateDB.DB_NAME);
    }

    public void changePreferenceLogged(){

        preferenceLogged.setPreference(!preferenceLogged.getPreference());
    }

    public void changePreferenceFirstLogin(){

        preferenceFirstLogin.setPreference(!preferenceFirstLogin.getPreference());
    }

    private ContentValues getValuesStudent(Student student){

        ContentValues values = new ContentValues();
        values.put(Student.COLUMN_ID_SQLITE, student.getId_sqlite());
        values.put(Student.COLUMN_ID_FIREBASE, student.getId_firebase());
        values.put(Student.COLUMN_ID_COACH_SQLITE, student.getId_coach_sqlite());
        values.put(Student.COLUMN_ID_COACH_FIREBASE, student.getId_coach_firebase());
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

        return values;
    }

    private ContentValues getValuesMeasure(Measure measure){

        ContentValues values = new ContentValues();
        if(measure.getId_sqlite() != 0) values.put(Measure.COLUMN_ID_SQLITE, Long.toString(measure.getId_sqlite()));
        values.put(Measure.COLUMN_ID_FIREBASE, measure.getId_firebase());
        values.put(Measure.COLUMN_ID_STUDENT_SQLITE, Long.toString(measure.getId_student_sqlite()));
        values.put(Measure.COLUMN_ID_STUDENT_FIREBASE, measure.getId_student_firebase());
        values.put(Measure.COLUMN_DATE, measure.getDate());
        values.put(Measure.COLUMN_WEIGHT, measure.getWeight());
        values.put(Measure.COLUMN_RIGHT_ARM, measure.getRight_arm());
        values.put(Measure.COLUMN_LEFT_ARM, measure.getLeft_arm());
        values.put(Measure.COLUMN_WAIST, measure.getWaist());
        values.put(Measure.COLUMN_HIP, measure.getHip());
        values.put(Measure.COLUMN_RIGHT_CALF, measure.getRight_calf());
        values.put(Measure.COLUMN_LEFT_CALF, measure.getLeft_calf());

        return values;
    }

    private Map<String, Object> getMapMeasure(Measure measure){

        Map<String, Object> data = new HashMap<>();
        data.put(Measure.COLUMN_ID_SQLITE, Long.toString(measure.getId_sqlite()));
        data.put(Measure.COLUMN_ID_FIREBASE, measure.getId_firebase());
        data.put(Measure.COLUMN_ID_STUDENT_SQLITE, Long.toString(measure.getId_student_sqlite()));
        data.put(Measure.COLUMN_ID_STUDENT_FIREBASE, measure.getId_student_firebase());
        data.put(Measure.COLUMN_DATE, measure.getDate());
        data.put(Measure.COLUMN_WEIGHT, measure.getWeight());
        data.put(Measure.COLUMN_RIGHT_ARM, measure.getRight_arm());
        data.put(Measure.COLUMN_LEFT_ARM, measure.getLeft_arm());
        data.put(Measure.COLUMN_WAIST, measure.getWaist());
        data.put(Measure.COLUMN_HIP, measure.getHip());
        data.put(Measure.COLUMN_RIGHT_CALF, measure.getRight_calf());
        data.put(Measure.COLUMN_LEFT_CALF, measure.getLeft_calf());

        return data;
    }
}
