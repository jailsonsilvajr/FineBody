package com.jjsj.finebodyapp.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jjsj.finebodyapp.database.entitys.Student;

public class ViewModelEditStudent extends ViewModel {

    private MutableLiveData<Boolean> studentUpdated;

    public LiveData<Boolean> observerStudentUpdated(){

        if(this.studentUpdated == null) this.studentUpdated = new MutableLiveData<>();
        return this.studentUpdated;
    }

    public void updateStudent(Student student){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Student.nameCollection)
                .document(student.getId())
                .set(student.getMapStudent())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        studentUpdated.setValue(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        studentUpdated.setValue(false);
                    }
                });
    }
}
