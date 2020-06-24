package com.jjsj.finebodyapp.database.sqlite.entitys;

import android.provider.BaseColumns;

public class Student implements BaseColumns {

    private long id_sqlite;
    private String id_firebase;
    private long id_coach_sqlite;
    private String id_coach_firebase;
    private String name;
    private String genre;
    private int age;
    private String path_photo;
    private String path_photo1;
    private String path_photo2;
    private String path_photo3;
    private String path_photo4;
    private String path_photo5;
    private String path_photo6;
    private String path_photo7;
    private String path_photo8;
    private String path_photo9;
    private String path_photo10;

    public static final String TABLE_NAME = "students";

    public static final String COLUMN_ID_SQLITE = "id_sqlite";
    public static final String COLUMN_ID_FIREBASE = "id_firebase";
    public static final String COLUMN_ID_COACH_SQLITE = "id_coach_sqlite";
    public static final String COLUMN_ID_COACH_FIREBASE = "id_coach_firebase";
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
                    Student.COLUMN_ID_SQLITE + " INTEGER PRIMARY KEY," +
                    Student.COLUMN_ID_FIREBASE + " TEXT," +
                    Student.COLUMN_ID_COACH_SQLITE + " INTEGER," +
                    Student.COLUMN_ID_COACH_FIREBASE + " TEXT," +
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

    public long getId_sqlite() {
        return id_sqlite;
    }

    public void setId_sqlite(long id_sqlite) {
        this.id_sqlite = id_sqlite;
    }

    public String getId_firebase() {
        return id_firebase;
    }

    public void setId_firebase(String id_firebase) {
        this.id_firebase = id_firebase;
    }

    public long getId_coach_sqlite() {
        return id_coach_sqlite;
    }

    public void setId_coach_sqlite(long id_coach_sqlite) {
        this.id_coach_sqlite = id_coach_sqlite;
    }

    public String getId_coach_firebase() {
        return id_coach_firebase;
    }

    public void setId_coach_firebase(String id_coach_firebase) {
        this.id_coach_firebase = id_coach_firebase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPath_photo() {
        return path_photo;
    }

    public void setPath_photo(String path_photo) {
        this.path_photo = path_photo;
    }

    public String getPath_photo1() {
        return path_photo1;
    }

    public void setPath_photo1(String path_photo1) {
        this.path_photo1 = path_photo1;
    }

    public String getPath_photo2() {
        return path_photo2;
    }

    public void setPath_photo2(String path_photo2) {
        this.path_photo2 = path_photo2;
    }

    public String getPath_photo3() {
        return path_photo3;
    }

    public void setPath_photo3(String path_photo3) {
        this.path_photo3 = path_photo3;
    }

    public String getPath_photo4() {
        return path_photo4;
    }

    public void setPath_photo4(String path_photo4) {
        this.path_photo4 = path_photo4;
    }

    public String getPath_photo5() {
        return path_photo5;
    }

    public void setPath_photo5(String path_photo5) {
        this.path_photo5 = path_photo5;
    }

    public String getPath_photo6() {
        return path_photo6;
    }

    public void setPath_photo6(String path_photo6) {
        this.path_photo6 = path_photo6;
    }

    public String getPath_photo7() {
        return path_photo7;
    }

    public void setPath_photo7(String path_photo7) {
        this.path_photo7 = path_photo7;
    }

    public String getPath_photo8() {
        return path_photo8;
    }

    public void setPath_photo8(String path_photo8) {
        this.path_photo8 = path_photo8;
    }

    public String getPath_photo9() {
        return path_photo9;
    }

    public void setPath_photo9(String path_photo9) {
        this.path_photo9 = path_photo9;
    }

    public String getPath_photo10() {
        return path_photo10;
    }

    public void setPath_photo10(String path_photo10) {
        this.path_photo10 = path_photo10;
    }
}
