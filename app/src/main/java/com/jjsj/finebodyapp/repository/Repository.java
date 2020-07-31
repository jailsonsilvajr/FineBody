package com.jjsj.finebodyapp.repository;

import android.content.Context;
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
import com.jjsj.finebodyapp.database.firebase.FireRequests;
import com.jjsj.finebodyapp.database.firebase.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class Repository {

    private static Repository repository;

    private Repository(){}

    public static Repository getInstance(Context context){

        if(repository == null){

            repository = new Repository();
        }
        return repository;
    }

    public MutableLiveData<Response> register(String email, String password){

        FireRequests fireRequests = new FireRequests();
        return fireRequests.registerCoach(email, password);
    }

    public MutableLiveData<Response> getStudent(String idStudent){

        FireRequests fireRequests = new FireRequests();
        return fireRequests.getOneStudent(idStudent);
    }

    public MutableLiveData<Response> getStudents(String idCoach){

        FireRequests fireRequests = new FireRequests();
        return fireRequests.getAllStudent(idCoach);
    }

    public MutableLiveData<Response> insertStudent(com.jjsj.finebodyapp.database.entitys.Student student){

        FireRequests fireRequests = new FireRequests();
        return fireRequests.postOneStudent(student);
    }

    public MutableLiveData<Response> deleteStudent(String idStudent){

        FireRequests fireRequests = new FireRequests();
        return fireRequests.deleteOneStudent(idStudent);
    }

    public MutableLiveData<Response> updateStudent(com.jjsj.finebodyapp.database.entitys.Student student){

        FireRequests fireRequests = new FireRequests();
        return fireRequests.putOneStudent(student);
    }

    public MutableLiveData<Response> getMeasures(String idStudent){

        FireRequests fireRequests = new FireRequests();
        return fireRequests.getAllMeasure(idStudent);
    }

    public MutableLiveData<Response> insertMeasure(com.jjsj.finebodyapp.database.entitys.Measure measure){

        FireRequests fireRequests = new FireRequests();
        return fireRequests.postOneMeasure(measure);
    }

    public MutableLiveData<Response> deleteMeasure(String idMeasure){

        FireRequests fireRequests = new FireRequests();
        return fireRequests.deleteOneMeasure(idMeasure);
    }

    public MutableLiveData<Response> deleteAllMeasures(List<Measure> measures){

        FireRequests fireRequests = new FireRequests();
        return fireRequests.deleteAllMeasure(measures);
    }

    public MutableLiveData<Response> updateMeasure(com.jjsj.finebodyapp.database.entitys.Measure measure){

        FireRequests fireRequests = new FireRequests();
        return fireRequests.putOneMeasure(measure);
    }

    public MutableLiveData<byte[]> downloadPhoto(String path) throws IOException {

        FireRequests fireRequests = new FireRequests();
        return fireRequests.downloadPhoto(path);
    }

    public void deleteImg(String path){

        FireRequests fireRequests = new FireRequests();
        fireRequests.deleteImg(path);
    }

    public MutableLiveData<Boolean> uploadPhoto(ImageView imageView, String path){

        MutableLiveData<Boolean> result = new MutableLiveData<>();

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

                result.setValue(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                result.setValue(false);
            }
        });

        return result;
    }
}
