package com.jjsj.finebodyapp.database.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jjsj.finebodyapp.database.entitys.Measure;
import com.jjsj.finebodyapp.database.entitys.Student;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class FireRequests {

    //STUDENT:
    public MutableLiveData<Student> getOneStudent(String idStudent){

        MutableLiveData<Student> studentMutableLiveData = new MutableLiveData<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Student.nameCollection)
                .document(idStudent)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        //Create Student:
                        Student student = new Student(documentSnapshot);
                        //return student
                        studentMutableLiveData.setValue(student);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        //return null
                        studentMutableLiveData.setValue(null);
                    }
                });
        return studentMutableLiveData;
    }

    public void getAllStudent(String idCoach, MutableLiveData<List<Student>> mutableLiveData){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Student.nameCollection)
                .whereEqualTo(Student.nameFieldIdCoach, idCoach)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<Student> listStudent = new ArrayList<>();
                        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){

                            //Create Student
                            Student student = new Student(documentSnapshot);
                            //add in list
                            listStudent.add(student);
                        }
                        //return list
                        mutableLiveData.setValue(listStudent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        //return null
                        mutableLiveData.setValue(null);
                    }
                });
    }

    public void postOneStudent(Student student, MutableLiveData<String> mutableLiveData){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Student.nameCollection)
                .add(student.getMapStudent())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        //return id
                        mutableLiveData.setValue(documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        //return null
                        mutableLiveData.setValue(null);
                    }
                });
    }

    public void deleteOneStudent(String idStudent, MutableLiveData<Boolean> mutableLiveData){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Student.nameCollection)
                .document(idStudent)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        mutableLiveData.setValue(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        mutableLiveData.setValue(false);
                    }
                });
    }

    public void putOneStudent(Student student, MutableLiveData<Boolean> mutableLiveData){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Student.nameCollection)
                .document(student.getId())
                .set(student.getMapStudent())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        mutableLiveData.setValue(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        mutableLiveData.setValue(false);
                    }
                });
    }

    //MEASURE:
    public MutableLiveData<Measure> getOneMeasure(String idMeasure){

        MutableLiveData<Measure> measureMutableLiveData = new MutableLiveData<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Measure.nameCollection)
                .document(idMeasure)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        //Create Measure:
                        Measure measure = new Measure(documentSnapshot);
                        //return Measure
                        measureMutableLiveData.setValue(measure);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        //return null
                        measureMutableLiveData.setValue(null);
                    }
                });
        return measureMutableLiveData;
    }

    public void getAllMeasure(String idStudent, MutableLiveData<List<Measure>> mutableLiveData){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Measure.nameCollection)
                .whereEqualTo(Measure.nameFieldIdStudent, idStudent)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<Measure> listMeasure = new ArrayList<>();
                        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){

                            //Create Measure
                            Measure measure = new Measure(documentSnapshot);
                            //add in list
                            listMeasure.add(measure);
                        }
                        mutableLiveData.setValue(listMeasure);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        //return null
                        mutableLiveData.setValue(null);
                    }
                });
    }

    public void postOneMeasure(Measure measure, MutableLiveData<String> mutableLiveData){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Measure.nameCollection)
                .add(measure.getMapMeasure())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        //return idMeasure
                        mutableLiveData.setValue(documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        //return null
                        mutableLiveData.setValue(null);
                    }
                });
    }

    public void deleteOneMeasure(String idMeasure, MutableLiveData<Boolean> mutableLiveData){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Measure.nameCollection)
                .document(idMeasure)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        mutableLiveData.setValue(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        mutableLiveData.setValue(false);
                    }
                });
    }

    public void deleteAllMeasure(String idStudent, MutableLiveData<Boolean> mutableLiveData){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Measure.nameCollection)
                .whereEqualTo(Measure.nameFieldIdStudent, idStudent)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){

                            documentSnapshot.getReference().delete();
                        }
                        mutableLiveData.setValue(true);
                    }
                });
    }

    public void putOneMeasure(Measure measure, MutableLiveData<Boolean> mutableLiveData){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Measure.nameCollection)
                .document(measure.getId())
                .set(measure.getMapMeasure())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        mutableLiveData.setValue(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        mutableLiveData.setValue(false);
                    }
                });
    }

    public void downloadPhoto(String path, MutableLiveData<byte[]> mutableLiveData) throws IOException {

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference imgReference = storageReference.child(path);

        final long ONE_MEGABYTE = 1024 * 1024;
        imgReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

                mutableLiveData.setValue(bytes);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                mutableLiveData.setValue(null);
            }
        });
    }

    public void deleteImg(String path){

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference imgReference = storageReference.child(path);
        imgReference.delete();
    }
}
