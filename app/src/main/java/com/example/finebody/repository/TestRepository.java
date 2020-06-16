package com.example.finebody.repository;

import android.content.Context;
import android.util.Log;

import com.example.finebody.database.sqlite.CreateDB;
import com.example.finebody.database.sqlite.entitys.Measure;
import com.example.finebody.database.sqlite.entitys.Student;

public class TestRepository {

    public TestRepository(Context context){

        context.deleteDatabase(CreateDB.DB_NAME);//delete database if exists
        Repository repository = Repository.getInstance(context, 1);//create database

        //insert student:
        Student student = new Student();
        setStudent(student);
        Log.i("Test", "Inserting: " + student.getName());
        long idStudent = repository.insertStudent(student);
        if(idStudent > 0){

            student.setId(idStudent);
            Log.i("Test", "Inserted: " + student.getName() + " with ID: " + idStudent);
        }else{

            student.setId(0);
            Log.i("Test", student.getName() + " was not inserted");
        }

        //update student
        Log.i("Test", "Updating: " + student.getName());
        String nameStudent = student.getName();
        student.setName("Jailson Junior");
        boolean result = repository.updateStudent(student);
        if(result) Log.i("Test", "Updated: " + nameStudent + " to " + student.getName());
        else{

            student.setName(nameStudent);
            Log.i("Test", nameStudent + " was not updated");
        }

        //insert measure
        Measure measure = new Measure();
        setMeasure(measure, student.getId());
        Log.i("Test", "Inserting measure in student: " + student.getName());
        long idMeasure = repository.insertMeasure(measure);
        if(idMeasure > 0){

            measure.setId(idMeasure);
            Log.i("Test", "Inserted measure with ID " + idMeasure + " in student " + student.getName());
        }else{

            measure.setId(0);
            Log.i("Test", "Measure was not inserted");
        }

        //update measure
        String dateMeasure = measure.getDate();
        measure.setDate("15/06/2020");
        Log.i("Test", "Updating measure " + dateMeasure);
        if(repository.updateMeasure(measure)) Log.i("Test", "Updated date to " + measure.getDate());
        else {

            measure.setDate(dateMeasure);
            Log.i("Test", "Measure was not updated");
        }

        //delete measure
        Log.i("Test", "Deleting measure " + measure.getDate());
        if(repository.deleteMeasure(measure)) Log.i("Test", "Deleted measure: " + measure.getDate());
        else Log.i("Test", "Measure was not deleted");

        //delete student
        Log.i("Test", "Deleting student " + student.getName());
        if(repository.deleteStudent(student)) Log.i("Test", "Deleted student " + student.getName());
        else Log.i("Test", "Student was not deleted");
    }

    public void setStudent(Student student){

        student.setName("Jailson Silva");
        student.setGenre("Masculino");
        student.setAge(28);
        student.setPath_photo("");
        student.setPath_photo1("");
        student.setPath_photo2("");
        student.setPath_photo3("");
        student.setPath_photo4("");
        student.setPath_photo5("");
        student.setPath_photo6("");
        student.setPath_photo7("");
        student.setPath_photo8("");
        student.setPath_photo9("");
        student.setPath_photo10("");
        student.setId_coach(1);
    }

    public void setMeasure(Measure measure, long idStudent){

        measure.setDate("14/06/2020");
        measure.setWeight(64);
        measure.setRight_arm(35);
        measure.setLeft_arm(34);
        measure.setWaist(55);
        measure.setHip(60);
        measure.setRight_calf(45);
        measure.setLeft_calf(45);
        measure.setId_student(idStudent);
    }
}
