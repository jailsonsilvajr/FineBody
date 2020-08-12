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

public class ViewModelGraph extends ViewModel {

    public MutableLiveData<List<Measure>> listMeasure;

    public LiveData<List<Measure>> observerListMeasure(){

        if(this.listMeasure == null) this.listMeasure = new MutableLiveData<>();
        return this.listMeasure;
    }

    public void getListMeasure(String idStudent){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Measure.nameCollection)
                .whereEqualTo(Measure.nameFieldIdStudent, idStudent)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<Measure> measures = new ArrayList<>();
                        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){

                            //Create Measure
                            Measure measure = new Measure(documentSnapshot);
                            //add in list
                            measures.add(measure);
                        }
                        listMeasure.setValue(measures);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        //return null
                        listMeasure.setValue(null);
                    }
                });
    }
}
