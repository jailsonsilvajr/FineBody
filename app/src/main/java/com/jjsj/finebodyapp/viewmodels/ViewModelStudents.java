package com.jjsj.finebodyapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import com.jjsj.finebodyapp.database.entitys.Student;
import com.jjsj.finebodyapp.preferences.PreferenceLogged;

import java.util.ArrayList;
import java.util.List;

public class ViewModelStudents extends AndroidViewModel {

    private MutableLiveData<List<Student>> listStudent;
    private String idCoach;

    public ViewModelStudents(Application application){
        super(application);
        this.idCoach = new PreferenceLogged(application.getApplicationContext()).getPreference();
    }

    public LiveData<List<Student>> observerListStudent(){

        if(this.listStudent == null) this.listStudent = new MutableLiveData<>();
        return this.listStudent;
    }

    public void getListStudent(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Student.nameCollection)
                .whereEqualTo(Student.nameFieldIdCoach, this.idCoach)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<Student> students = new ArrayList<>();
                        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){

                            //Create Student
                            Student student = new Student(documentSnapshot);
                            //add in list
                            students.add(student);
                        }
                        //return list
                        listStudent.setValue(students);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        //return null
                        listStudent.setValue(null);
                    }
                });
    }
}
