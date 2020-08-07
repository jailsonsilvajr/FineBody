package com.jjsj.finebodyapp.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jjsj.finebodyapp.database.entitys.Measure;

public class ViewModelAddMeasure extends ViewModel {

    private MutableLiveData<Measure> measure;

    public LiveData<Measure> observerMeasure(){

        if(this.measure == null) this.measure = new MutableLiveData<>();
        return this.measure;
    }

    public void addMeasure(Measure newMeasure){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Measure.nameCollection)
                .add(newMeasure)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                        if(task.isSuccessful()){

                            newMeasure.setId(task.getResult().getId());
                            measure.setValue(newMeasure);
                        }else{

                            measure.setValue(null);
                        }
                    }
                });
    }
}
