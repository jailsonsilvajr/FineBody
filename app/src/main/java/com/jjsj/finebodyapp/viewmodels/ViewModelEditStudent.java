package com.jjsj.finebodyapp.viewmodels;

import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jjsj.finebodyapp.database.entitys.Student;
import com.jjsj.finebodyapp.repository.Repository;

import java.io.IOException;

public class ViewModelEditStudent extends ViewModel {

    private MutableLiveData<Boolean> uploadImage;
    private MutableLiveData<byte[]> downloadImage;
    private MutableLiveData<Boolean> updateStudent;

    public LiveData<Boolean> observerUpdateStudent(){

        if(this.updateStudent == null) this.updateStudent = new MutableLiveData<>();
        return this.updateStudent;
    }

    public void updateStudent(Student student){

        Repository.getInstance().putOneStudent(student, this.updateStudent);
    }

    public LiveData<Boolean> observerUploadImage(){

        if(this.uploadImage == null) this.uploadImage = new MutableLiveData<>();
        return this.uploadImage;
    }

    public void doUpload(ImageView imageView, String path){

        Repository.getInstance().uploadPhoto(imageView, path, this.uploadImage);
    }

    public LiveData<byte[]> observerDownloadImage(){

        if(this.downloadImage == null) this.downloadImage = new MutableLiveData<>();
        return this.downloadImage;
    }

    public void downloadImage(String path) throws IOException {

        Repository.getInstance().downloadPhoto(path, this.downloadImage);
    }
}
