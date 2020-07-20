package com.jjsj.finebodyapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.jjsj.finebodyapp.database.entitys.Measure;
import com.jjsj.finebodyapp.database.firebase.Response;
import com.jjsj.finebodyapp.repository.Repository;

import java.util.List;

public class ViewModelGraph extends AndroidViewModel {

    private LiveData<Response> responseMeasure;
    private Repository repository;

    public ViewModelGraph(@NonNull Application application) {
        super(application);
        this.repository = Repository.getInstance(application.getApplicationContext());
    }

    public LiveData<Response> observerResponseMeasures(){

        return this.responseMeasure;
    }

    public void getMeasures(String idStudent){

        this.responseMeasure = this.repository.getMeasures(idStudent);
    }
}
