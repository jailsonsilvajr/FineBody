package com.jjsj.finebodyapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jjsj.finebodyapp.database.entitys.Measure;
import com.jjsj.finebodyapp.repository.Repository;

import java.util.List;

public class ViewModelGraph extends ViewModel {

    public MutableLiveData<List<Measure>> listMeasure;

    public LiveData<List<Measure>> observerListMeasure(){

        if(this.listMeasure == null) this.listMeasure = new MutableLiveData<>();
        return this.listMeasure;
    }

    public void getListMeasure(String idStudent){

        Repository.getInstance().getAllMeasure(idStudent, this.listMeasure);
    }
}
