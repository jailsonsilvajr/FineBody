package com.jjsj.finebodyapp.repository;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jjsj.finebodyapp.database.entitys.Measure;
import com.jjsj.finebodyapp.database.entitys.Student;
import com.jjsj.finebodyapp.database.firebase.FireRequests;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class Repository {

    private static Repository repository;
    private static FireRequests fireRequests;

    private Repository(){}

    public static Repository getInstance(){

        if(repository == null){

            repository = new Repository();
            fireRequests = new FireRequests();
        }
        return repository;
    }

    public void getAllStudent(String idCoach, MutableLiveData<List<Student>> mutableLiveData){

        fireRequests.getAllStudent(idCoach, mutableLiveData);
    }

    public MutableLiveData<Student> getOneStudent(String idStudent){

        return fireRequests.getOneStudent(idStudent);
    }

    public void postOneStudent(Student student, MutableLiveData<String> mutableLiveData){

        fireRequests.postOneStudent(student, mutableLiveData);
    }

    public void deleteOneStudent(String idStudent, MutableLiveData<Boolean> mutableLiveData){

        fireRequests.deleteOneStudent(idStudent, mutableLiveData);
    }

    public void putOneStudent(Student student, MutableLiveData<Boolean> mutableLiveData){

        fireRequests.putOneStudent(student, mutableLiveData);
    }

    public void getAllMeasure(String idStudent, MutableLiveData<List<Measure>> mutableLiveData){

        fireRequests.getAllMeasure(idStudent, mutableLiveData);
    }

    public MutableLiveData<Measure> getOneMeasure(String idMeasure){

        return fireRequests.getOneMeasure(idMeasure);
    }

    public void postOneMeasure(Measure measure, MutableLiveData<String> mutableLiveData){

        fireRequests.postOneMeasure(measure, mutableLiveData);
    }

    public void deleteOneMeasure(String idMeasure, MutableLiveData<Boolean> mutableLiveData){

        fireRequests.deleteOneMeasure(idMeasure, mutableLiveData);
    }

    public void deleteAllMeasure(String idStudent, MutableLiveData<Boolean> mutableLiveData){

        fireRequests.deleteAllMeasure(idStudent, mutableLiveData);
    }

    public void putOneMeasure(Measure measure, MutableLiveData<Boolean> mutableLiveData){

        fireRequests.putOneMeasure(measure, mutableLiveData);
    }

    public void downloadPhoto(String path, MutableLiveData<byte[]> mutableLiveData) throws IOException {

        fireRequests.downloadPhoto(path, mutableLiveData);
    }

    public void deleteImg(String path){

        FireRequests fireRequests = new FireRequests();
        fireRequests.deleteImg(path);
    }

    public void uploadPhoto(ImageView imageView, String path, MutableLiveData<Boolean> mutableLiveData){

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference imgReference = storageReference.child(path);

        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imgReference.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                mutableLiveData.setValue(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                mutableLiveData.setValue(false);
            }
        });
    }
}
