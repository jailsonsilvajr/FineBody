package com.jjsj.finebodyapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jjsj.finebodyapp.database.entitys.Measure;
import com.jjsj.finebodyapp.database.firebase.Response;
import com.jjsj.finebodyapp.repository.Repository;

public class ViewModelAddMeasure extends AndroidViewModel {

    private LiveData<Response> responseMeasure;
    private Repository repository;

    public ViewModelAddMeasure(@NonNull Application application) {

        super(application);
        this.repository = Repository.getInstance(application.getApplicationContext());
    }

    public LiveData<Response> observerResponseMeasure(){

        return this.responseMeasure;
    }

    public void addMeasure(Measure newMeasure){

        this.responseMeasure = repository.insertMeasure(newMeasure);
    }

    public void updateMeasure(Measure measure){

        this.repository.updateMeasure(measure);
    }
}
