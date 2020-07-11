package com.jjsj.finebodyapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jjsj.finebodyapp.database.entitys.Measure;
import com.jjsj.finebodyapp.database.firebase.Response;
import com.jjsj.finebodyapp.repository.Repository;

import java.util.List;

public class ViewModelStudent extends AndroidViewModel {

    private Repository repository;
    private LiveData<Response> responseGetMeasures;
    private LiveData<Response> responseDeleteStudent;
    private LiveData<Response> responseDeleteMeasures;

    public ViewModelStudent(@NonNull Application application) {

        super(application);
        this.repository = Repository.getInstance(application.getApplicationContext());
    }

    public LiveData<Response> observerResponseGetMeasures() {

        return this.responseGetMeasures;
    }

    public LiveData<Response> observerResponseDeleteStudent(){

        return this.responseDeleteStudent;
    }

    public LiveData<Response> observerResponseDeleteMeasures() {

        return this.responseDeleteMeasures;
    }

    public void getMeasures(String idStudent){

        this.responseGetMeasures = this.repository.getMeasures(idStudent);
    }

    public void deleteStudent(String idStudent){

        this.responseDeleteStudent = this.repository.deleteStudent(idStudent);
    }

    public void deleteMeasures(List<Measure> measures){

        this.responseDeleteMeasures = this.repository.deleteAllMeasures(measures);
    }

    public void deleteImg(String path){

        this.repository.deleteImg(path);
    }
}
