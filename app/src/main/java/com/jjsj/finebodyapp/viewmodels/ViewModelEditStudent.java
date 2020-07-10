package com.jjsj.finebodyapp.viewmodels;

import android.app.Application;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jjsj.finebodyapp.database.entitys.Student;
import com.jjsj.finebodyapp.database.firebase.Response;
import com.jjsj.finebodyapp.repository.Repository;

import java.io.IOException;

public class ViewModelEditStudent extends AndroidViewModel {

    private Repository repository;
    private LiveData<Boolean> uploadImage;
    private LiveData<byte[]> downloadImage;
    private LiveData<Response> responseUpdateStudent;

    public ViewModelEditStudent(@NonNull Application application) {

        super(application);
        this.repository = Repository.getInstance(application.getApplicationContext());
    }

    public LiveData<Boolean> observerUploadImage(){

        return this.uploadImage;
    }

    public LiveData<byte[]> observerDownloadImage(){

        return this.downloadImage;
    }

    public LiveData<Response> observerResponseUpdateStudent(){

        return this.responseUpdateStudent;
    }

    public void doUpload(ImageView imageView, String path){

        this.uploadImage = this.repository.uploadPhoto(imageView, path);
    }

    public void updateStudent(Student student){

        this.responseUpdateStudent = this.repository.updateStudent(student);
    }

    public void downloadImage(String path) throws IOException {

        this.downloadImage = this.repository.downloadPhoto(path);
    }
}
