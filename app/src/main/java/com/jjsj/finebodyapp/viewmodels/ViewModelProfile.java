package com.jjsj.finebodyapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jjsj.finebodyapp.database.firebase.Response;
import com.jjsj.finebodyapp.repository.Repository;

import java.io.IOException;

public class ViewModelProfile extends AndroidViewModel {

    private Repository repository;
    private LiveData<byte[]> imgProfileBytes;
    private LiveData<Response> responseStudent;

    public ViewModelProfile(@NonNull Application application) {

        super(application);
        this.repository = Repository.getInstance(application.getApplicationContext());
    }

    public LiveData<byte[]> observerImgProfileBytes(){

        return this.imgProfileBytes;
    }

    public LiveData<Response> observerResponseStudent(){

        return this.responseStudent;
    }

    public void downloadImgProfile(String path) throws IOException {

        this.imgProfileBytes = this.repository.downloadPhoto(path);
    }

    public void getStudent(String idStudent){

        this.responseStudent = this.repository.getStudent(idStudent);
    }
}
