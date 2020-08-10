package com.jjsj.finebodyapp.database.entitys;

import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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

    public Measure(String id, String idStudent, String date, float weight, float rightArm, float leftArm, float waist, float hip, float rightCalf, float leftCalf) {
        this.id = id;
        this.idStudent = idStudent;
        this.date = date;
        this.weight = weight;
        this.rightArm = rightArm;
        this.leftArm = leftArm;
        this.waist = waist;
        this.hip = hip;
        this.rightCalf = rightCalf;
        this.leftCalf = leftCalf;
    }

    public Measure(DocumentSnapshot documentSnapshot){

        this.id = documentSnapshot.getId();
        this.idStudent = documentSnapshot.get(nameFieldIdStudent).toString();
        this.date = documentSnapshot.get(nameFieldDate).toString();
        this.weight = Float.parseFloat(documentSnapshot.get(nameFieldWeight).toString());
        this.rightArm = Float.parseFloat(documentSnapshot.get(nameFieldRightArm).toString());
        this.leftArm = Float.parseFloat(documentSnapshot.get(nameFieldLeftArm).toString());
        this.waist = Float.parseFloat(documentSnapshot.get(nameFieldWaist).toString());
        this.hip = Float.parseFloat(documentSnapshot.get(nameFieldHip).toString());
        this.rightCalf = Float.parseFloat(documentSnapshot.get(nameFieldRightCalf).toString());
        this.leftCalf = Float.parseFloat(documentSnapshot.get(nameFieldLeftCalf).toString());
    }

    public Map<String, Object> getMapMeasure(){

        Map<String, Object> data = new HashMap<>();
        if(this.id != null) data.put(Measure.nameFieldId, this.id);
        data.put(Measure.nameFieldIdStudent, this.idStudent);
        data.put(Measure.nameFieldDate, this.date);
        data.put(Measure.nameFieldWeight, this.weight);
        data.put(Measure.nameFieldRightArm, this.rightArm);
        data.put(Measure.nameFieldLeftArm, this.leftArm);
        data.put(Measure.nameFieldWaist, this.waist);
        data.put(Measure.nameFieldHip, this.hip);
        data.put(Measure.nameFieldRightCalf, this.rightCalf);
        data.put(Measure.nameFieldLeftCalf, this.leftCalf);

        return data;
    }

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
