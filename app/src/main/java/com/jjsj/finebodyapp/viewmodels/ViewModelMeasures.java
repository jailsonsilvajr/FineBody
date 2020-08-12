package com.jjsj.finebodyapp.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.jjsj.finebodyapp.database.entitys.Measure;

import java.util.ArrayList;
import java.util.List;

public class ViewModelMeasures extends ViewModel {

    private String idStudent;
    private MutableLiveData<List<Measure>> measures;
    private MutableLiveData<Boolean> deleteMeasure;

    public void setIdStudent(String idStudent){

        this.idStudent = idStudent;
    }

    public LiveData<List<Measure>> observerMeasures(){

        if(this.measures == null) this.measures = new MutableLiveData<>();
        return this.measures;
    }

    public LiveData<Boolean> observerDeleteMeasure(){

        if (this.deleteMeasure == null) this.deleteMeasure = new MutableLiveData<>();
        return this.deleteMeasure;
    }

    public void getAllMeasures(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Measure.nameCollection)
                .whereEqualTo(Measure.nameFieldIdStudent, this.idStudent)
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
                        measures.setValue(listMeasure);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        //return null
                        measures.setValue(null);
                    }
                });
    }

    public void deleteMeasure(String idMeasure){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Measure.nameCollection)
                .document(idMeasure)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        deleteMeasure.setValue(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        deleteMeasure.setValue(false);
                    }
                });
    }
}
