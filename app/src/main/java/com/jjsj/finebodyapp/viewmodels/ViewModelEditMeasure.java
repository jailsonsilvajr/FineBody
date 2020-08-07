package com.jjsj.finebodyapp.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jjsj.finebodyapp.database.entitys.Measure;

import java.util.HashMap;
import java.util.Map;

public class ViewModelEditMeasure extends ViewModel {

    private MutableLiveData<Measure> measure;

    public LiveData<Measure> observerMeasure(){

        if(this.measure == null) this.measure = new MutableLiveData<>();
        return this.measure;
    }

    public void editMeasure(Measure newMeasure){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Measure.nameCollection)
                .document(newMeasure.getId())
                .set(getMapMeasure(newMeasure))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        measure.setValue(newMeasure);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        measure.setValue(null);
                    }
                });
    }

    private Map<String, Object> getMapMeasure(Measure measure){

        Map<String, Object> data = new HashMap<>();

        if(measure.getId() != null) data.put(Measure.nameFieldId, measure.getId());
        data.put(Measure.nameFieldIdStudent, measure.getIdStudent());
        data.put(Measure.nameFieldDate, measure.getDate());
        data.put(Measure.nameFieldWeight, measure.getWeight());
        data.put(Measure.nameFieldRightArm, measure.getRightArm());
        data.put(Measure.nameFieldLeftArm, measure.getLeftArm());
        data.put(Measure.nameFieldWaist, measure.getWaist());
        data.put(Measure.nameFieldHip, measure.getHip());
        data.put(Measure.nameFieldRightCalf, measure.getRightCalf());
        data.put(Measure.nameFieldLeftCalf, measure.getLeftCalf());

        return data;
    }
}
