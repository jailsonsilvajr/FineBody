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
import com.jjsj.finebodyapp.database.entitys.Student;

public class ViewModelStudent extends ViewModel {

    public MutableLiveData<Boolean> deleteStudent;

    public LiveData<Boolean> observerDeleteStudent(){

        if(this.deleteStudent == null) this.deleteStudent = new MutableLiveData<>();
        return this.deleteStudent;
    }

    public void deleteStudent(String idStudent){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Student.nameCollection)
                .document(idStudent)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        deleteStudent.setValue(true);
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
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        deleteStudent.setValue(false);
                    }
                });
    }
}
