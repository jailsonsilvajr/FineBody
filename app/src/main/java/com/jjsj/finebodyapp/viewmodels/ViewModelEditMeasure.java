package com.jjsj.finebodyapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jjsj.finebodyapp.database.entitys.Measure;
import com.jjsj.finebodyapp.database.firebase.Response;
import com.jjsj.finebodyapp.repository.Repository;

public class ViewModelEditMeasure extends AndroidViewModel {

    private LiveData<Response> responseMeasure;
    private Repository repository;

    public ViewModelEditMeasure(@NonNull Application application) {

        super(application);
        this.repository = Repository.getInstance(application.getApplicationContext());
    }

    public LiveData<Response> observerResponseMeasure(){

        return this.responseMeasure;
    }

    public void editMeasure(Measure measure){

        this.responseMeasure = this.repository.updateMeasure(measure);
    }
}
