package com.jjsj.finebodyapp.database.sqlite.entitys;

import android.provider.BaseColumns;

public class Measure implements BaseColumns {

    private long id;
    private String date;
    private float weight;
    private float right_arm;
    private float left_arm;
    private float waist;
    private float hip;
    private float right_calf;
    private float left_calf;
    private long id_student;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getRight_arm() {
        return right_arm;
    }

    public void setRight_arm(float right_arm) {
        this.right_arm = right_arm;
    }

    public float getLeft_arm() {
        return left_arm;
    }

    public void setLeft_arm(float left_arm) {
        this.left_arm = left_arm;
    }

    public float getWaist() {
        return waist;
    }

    public void setWaist(float waist) {
        this.waist = waist;
    }

    public float getHip() {
        return hip;
    }

    public void setHip(float hip) {
        this.hip = hip;
    }

    public float getRight_calf() {
        return right_calf;
    }

    public void setRight_calf(float right_calf) {
        this.right_calf = right_calf;
    }

    public float getLeft_calf() {
        return left_calf;
    }

    public void setLeft_calf(float left_calf) {
        this.left_calf = left_calf;
    }

    public long getId_student() {
        return id_student;
    }

    public void setId_student(long id_student) {
        this.id_student = id_student;
    }
}
