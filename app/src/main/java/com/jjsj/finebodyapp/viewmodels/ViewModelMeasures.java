package com.jjsj.finebodyapp.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jjsj.finebodyapp.database.entitys.Measure;

import java.util.ArrayList;
import java.util.List;

public class ViewModelMeasures extends ViewModel {

    private String idStudent;
    private MutableLiveData<List<Measure>> measures;

    public void setIdStudent(String idStudent){

        this.idStudent = idStudent;
    }

    public LiveData<List<Measure>> observerMeasures(){

        if(this.measures == null) this.measures = new MutableLiveData<>();
        return this.measures;
    }

    public Measure getOneMeasure(int position){

        return this.measures.getValue().get(position);
    }

    public void getMeasures(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Measure.nameCollection)
                .whereEqualTo(Measure.nameFieldIdStudent, this.idStudent)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()){

                            List<Measure> listMeasure = new ArrayList<>();
                            for(QueryDocumentSnapshot document : task.getResult()){

                                Measure measure = new Measure();
                                measure.setId(document.getId());
                                measure.setIdStudent(document.get(Measure.nameFieldIdStudent).toString());
                                measure.setDate(document.get(Measure.nameFieldDate).toString());
                                measure.setWeight(Float.parseFloat(document.get(Measure.nameFieldWeight).toString()));
                                measure.setRightArm(Float.parseFloat(document.get(Measure.nameFieldRightArm).toString()));
                                measure.setLeftArm(Float.parseFloat(document.get(Measure.nameFieldLeftArm).toString()));
                                measure.setWaist(Float.parseFloat(document.get(Measure.nameFieldWaist).toString()));
                                measure.setHip(Float.parseFloat(document.get(Measure.nameFieldHip).toString()));
                                measure.setRightCalf(Float.parseFloat(document.get(Measure.nameFieldRightCalf).toString()));
                                measure.setLeftCalf(Float.parseFloat(document.get(Measure.nameFieldLeftCalf).toString()));

                                listMeasure.add(measure);
                            }
                            measures.setValue(listMeasure);
                        }else{


                        }
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

                        getMeasures();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                    }
                });
    }
}
