package com.jjsj.finebodyapp.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jjsj.finebodyapp.database.entitys.Student;
import com.jjsj.finebodyapp.repository.Repository;

import java.io.IOException;
import java.util.List;

public class ViewModelStudents extends ViewModel {

    private MutableLiveData<List<Student>> listStudentMutableLiveData;
    private LiveData<byte[]> liveDataImageProfile;

    public LiveData<List<Student>> observerListStudent(){

        if(this.listStudentMutableLiveData == null) this.listStudentMutableLiveData = new MutableLiveData<>();
        return this.listStudentMutableLiveData;
    }

    public void getListStudent(String idCoach){

        Repository.getInstance().getAllStudent(idCoach, this.listStudentMutableLiveData);
    }

    public LiveData<byte[]> getLiveDataImageProfile(){

        return this.liveDataImageProfile;
    }

    public void getImageProfile(String path) throws IOException {

        this.liveDataImageProfile = downloadPhoto(path);
    }

    public MutableLiveData<byte[]> downloadPhoto(String path) throws IOException {

        MutableLiveData<byte[]> imgBytes = new MutableLiveData<>();

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference imgReference = storageReference.child(path);

        final long ONE_MEGABYTE = 1024 * 1024;
        imgReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

                imgBytes.setValue(bytes);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                imgBytes.setValue(null);
            }
        });

        return imgBytes;
    }
}
