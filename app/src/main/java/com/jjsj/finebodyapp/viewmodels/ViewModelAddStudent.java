package com.jjsj.finebodyapp.viewmodels;

import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jjsj.finebodyapp.database.entitys.Student;
import com.jjsj.finebodyapp.repository.Repository;

public class ViewModelAddStudent extends ViewModel {

    private MutableLiveData<String> idStudent;
    private MutableLiveData<Boolean> updateStudent;
    private MutableLiveData<Boolean> uploadPhoto;

    public LiveData<String> observerIdStudent(){

        if(idStudent == null) this.idStudent = new MutableLiveData<>();
        return this.idStudent;
    }

    public LiveData<Boolean> observerUpdateStudent(){

        if(updateStudent == null) this.updateStudent = new MutableLiveData<>();
        return this.updateStudent;
    }

    public  LiveData<Boolean> observerUploadPhoto(){

        if(uploadPhoto == null) this.uploadPhoto = new MutableLiveData<>();
        return this. uploadPhoto;
    }

    public void addStudent(Student newStudent){

        Repository.getInstance().postOneStudent(newStudent, this.idStudent);
    }

    public void updateStudent(Student student){

        Repository.getInstance().putOneStudent(student, this.updateStudent);
    }

    public void doUploadPhoto(ImageView imageView, String path){

        Repository.getInstance().uploadPhoto(imageView, path, this.uploadPhoto);
    }
}
