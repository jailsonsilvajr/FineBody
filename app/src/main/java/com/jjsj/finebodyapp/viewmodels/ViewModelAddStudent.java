package com.jjsj.finebodyapp.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.jjsj.finebodyapp.database.entitys.Student;

public class ViewModelAddStudent extends ViewModel {

    private MutableLiveData<String> idStudent;

    public LiveData<String> observerIdStudent(){

        if(this.idStudent == null) this.idStudent = new MutableLiveData<>();
        return this.idStudent;
    }

    public void addStudent(Student student){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Student.nameCollection)
                .add(student.getMapStudent())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        //return id
                        String id = documentReference.getId();
                        student.setId(id);

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection(Student.nameCollection)
                                .document(student.getId())
                                .set(student.getMapStudent());

                        idStudent.setValue(id);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        //return null
                        idStudent.setValue(null);
                    }
                });
    }
}
