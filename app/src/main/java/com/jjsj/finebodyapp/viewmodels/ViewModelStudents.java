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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jjsj.finebodyapp.database.entitys.Student;
import com.jjsj.finebodyapp.database.firebase.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewModelStudents extends ViewModel {

    private MutableLiveData<Response> mutableLiveDataResponse;
    private MutableLiveData<byte[]> mutableLiveDataImageProfile;

    public LiveData<Response> getMutableLiveDataResponse() {

        if(this.mutableLiveDataResponse == null) this.mutableLiveDataResponse = new MutableLiveData<>();
        return this.mutableLiveDataResponse;
    }

    public LiveData<byte[]> getMutableLiveDataImageProfile(){

        if(this.mutableLiveDataImageProfile == null) this.mutableLiveDataImageProfile = new MutableLiveData<>();
        return this.mutableLiveDataImageProfile;
    }

    public void getStudents(String idCoach){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Student.nameCollection)
                .whereEqualTo(Student.nameFieldIdCoach, idCoach)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()){

                            List<Student> listStudents = new ArrayList<>();
                            for(QueryDocumentSnapshot document : task.getResult()){

                                //Create student
                                Student student = new Student();
                                student.setId(document.getId());
                                student.setName(document.get(Student.nameFieldName).toString());
                                student.setGenre(document.get(Student.nameFieldGenre).toString());
                                student.setAge(Integer.parseInt(document.get(Student.nameFieldAge).toString()));
                                student.setIdCoach(document.get(Student.nameFieldIdCoach).toString());
                                student.setPathPhoto(document.get(Student.nameFieldPathPhoto).toString());

                                listStudents.add(student);
                            }
                            mutableLiveDataResponse.setValue(new Response(302, "Found", listStudents));
                        }else{

                            mutableLiveDataResponse.setValue(new Response(404, "Error: " + task.getException(), null));
                        }
                    }
                });
    }

    public void getImageProfile(String path) throws IOException {

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference imgReference = storageReference.child(path);

        final long ONE_MEGABYTE = 1024 * 1024;
        imgReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

                mutableLiveDataImageProfile.setValue(bytes);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                mutableLiveDataImageProfile.setValue(null);
            }
        });
    }
}
