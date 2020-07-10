package com.jjsj.finebodyapp.database.entitys;

import java.io.Serializable;

public class Measure implements Serializable {

    private String id;
    private String idStudent;
    private String date;
    private float weight;
    private float rightArm;
    private float leftArm;
    private float waist;
    private float hip;
    private float rightCalf;
    private float leftCalf;

    public static final String nameCollection = "measures";
    public static final String nameFieldId = "id";
    public static final String nameFieldIdStudent = "idStudent";
    public static final String nameFieldDate = "date";
    public static final String nameFieldWeight = "weight";
    public static final String nameFieldRightArm = "rightArm";
    public static final String nameFieldLeftArm = "leftArm";
    public static final String nameFieldWaist = "waist";
    public static final String nameFieldHip = "hip";
    public static final String nameFieldRightCalf = "rightCalf";
    public static final String nameFieldLeftCalf = "leftCalf";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
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

    public float getRightArm() {
        return rightArm;
    }

    public void setRightArm(float rightArm) {
        this.rightArm = rightArm;
    }

    public float getLeftArm() {
        return leftArm;
    }

    public void setLeftArm(float leftArm) {
        this.leftArm = leftArm;
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

    public float getRightCalf() {
        return rightCalf;
    }

    public void setRightCalf(float rightCalf) {
        this.rightCalf = rightCalf;
    }

    public float getLeftCalf() {
        return leftCalf;
    }

    public void setLeftCalf(float leftCalf) {
        this.leftCalf = leftCalf;
    }
}
