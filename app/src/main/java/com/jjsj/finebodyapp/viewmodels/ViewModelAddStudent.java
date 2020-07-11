package com.jjsj.finebodyapp.viewmodels;

import android.app.Application;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jjsj.finebodyapp.database.entitys.Student;
import com.jjsj.finebodyapp.database.firebase.Response;
import com.jjsj.finebodyapp.repository.Repository;

public class ViewModelAddStudent extends AndroidViewModel {

    public LiveData<Response> responseAddStudent;
    public LiveData<Response> responseUpdateStudent;
    public LiveData<Boolean> responseUploadPhoto;
    private Repository repository;

    public ViewModelAddStudent(@NonNull Application application) {

        super(application);
        this.repository = Repository.getInstance(application.getApplicationContext());
    }

    public LiveData<Response> observerResponseAddStudent(){

        return this.responseAddStudent;
    }

    public LiveData<Response> observerResponseUpdateStudent(){

        return this.responseUpdateStudent;
    }

    public LiveData<Boolean> observerResponseUploadPhoto(){

        return this.responseUploadPhoto;
    }

    public void addStudent(Student newStudent){

        this.responseAddStudent = this.repository.insertStudent(newStudent);
    }

    public void updateStudent(Student student){

        this.responseUpdateStudent = this.repository.updateStudent(student);
    }

    public void doUpload(ImageView imageView, String path){

        this.responseUploadPhoto = this.repository.uploadPhoto(imageView, path);
    }
}
