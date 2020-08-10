package com.jjsj.finebodyapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jjsj.finebodyapp.database.entitys.Measure;
import com.jjsj.finebodyapp.repository.Repository;

public class ViewModelAddMeasure extends ViewModel {

    private MutableLiveData<String> idMeasure;

    public LiveData<String> observerIdMeasure(){

        if(this.idMeasure == null) this.idMeasure = new MutableLiveData<>();
        return this.idMeasure;
    }

    public void addMeasure(Measure newMeasure){

        Repository.getInstance().postOneMeasure(newMeasure, idMeasure);
    }
}
