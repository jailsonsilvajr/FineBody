package com.jjsj.finebodyapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jjsj.finebodyapp.database.sqlite.entitys.Measure;
import com.jjsj.finebodyapp.repository.Repository;

public class ViewModelAddMeasure extends AndroidViewModel {

    private LiveData<Measure> measure;
    private Repository repository;

    public ViewModelAddMeasure(@NonNull Application application) {

        super(application);
        this.repository = Repository.getInstance(application.getApplicationContext());
    }

    public LiveData<Measure> observeMeasure(){

        return this.measure;
    }

    public void addMeasure(Measure newMeasure){

        this.measure = repository.insertMeasureRepository(newMeasure);
    }
}
