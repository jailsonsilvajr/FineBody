package com.jjsj.finebodyapp.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jjsj.finebodyapp.database.entitys.Measure;

public class ViewModelEditMeasure extends ViewModel {

    private MutableLiveData<Boolean> updateMeasure;

    public LiveData<Boolean> observerUpdateMeasure(){

        if(this.updateMeasure == null) this.updateMeasure = new MutableLiveData<>();
        return this.updateMeasure;
    }

    public void editMeasure(Measure newMeasure){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Measure.nameCollection)
                .document(newMeasure.getId())
                .set(newMeasure.getMapMeasure())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        updateMeasure.setValue(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        updateMeasure.setValue(false);
                    }
                });
    }
}
