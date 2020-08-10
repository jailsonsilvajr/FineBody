package com.jjsj.finebodyapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jjsj.finebodyapp.database.entitys.Measure;
import com.jjsj.finebodyapp.repository.Repository;

public class ViewModelEditMeasure extends ViewModel {

    private MutableLiveData<Boolean> updateMeasure;

    public LiveData<Boolean> observerUpdateMeasure(){

        if(this.updateMeasure == null) this.updateMeasure = new MutableLiveData<>();
        return this.updateMeasure;
    }

    public void editMeasure(Measure newMeasure){

        Repository.getInstance().putOneMeasure(newMeasure, this.updateMeasure);
    }
}
