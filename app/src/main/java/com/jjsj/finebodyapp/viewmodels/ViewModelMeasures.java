package com.jjsj.finebodyapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jjsj.finebodyapp.database.firebase.Response;
import com.jjsj.finebodyapp.repository.Repository;

public class ViewModelMeasures extends AndroidViewModel {

    private Repository repository;
    private LiveData<Response> responseMeasures;

    public ViewModelMeasures(@NonNull Application application) {

        super(application);
        this.repository = Repository.getInstance(application.getApplicationContext());
    }

    public LiveData<Response> observerResponseMeasures(){

        return this.responseMeasures;
    }

    public void getMeasures(String idStudent){

        this.responseMeasures = this.repository.getMeasures(idStudent);
    }
}
