package com.jjsj.finebodyapp.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jjsj.finebodyapp.database.entitys.Measure;

public class ViewModelAddMeasure extends ViewModel {

    private MutableLiveData<String> idMeasure;

    public LiveData<String> observerIdMeasure(){

        if(this.idMeasure == null) this.idMeasure = new MutableLiveData<>();
        return this.idMeasure;
    }

    public void addMeasure(Measure newMeasure){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Measure.nameCollection)
                .add(newMeasure.getMapMeasure())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        //return idMeasure
                        idMeasure.setValue(documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        //return null
                        idMeasure.setValue(null);
                    }
                });
    }
}
