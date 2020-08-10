package com.jjsj.finebodyapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jjsj.finebodyapp.database.entitys.Measure;
import com.jjsj.finebodyapp.repository.Repository;

import java.util.List;

public class ViewModelMeasures extends ViewModel {

    private String idStudent;
    private MutableLiveData<List<Measure>> measures;
    private MutableLiveData<Boolean> deleteMeasure;

    public void setIdStudent(String idStudent){

        this.idStudent = idStudent;
    }

    public LiveData<List<Measure>> observerMeasures(){

        if(this.measures == null) this.measures = new MutableLiveData<>();
        return this.measures;
    }

    public LiveData<Boolean> observerDeleteMeasure(){

        if (this.deleteMeasure == null) this.deleteMeasure = new MutableLiveData<>();
        return this.deleteMeasure;
    }

    public Measure getOneMeasure(int position){

        return this.measures.getValue().get(position);
    }

    public void getAllMeasures(){

        Repository.getInstance().getAllMeasure(this.idStudent, this.measures);
    }

    public void deleteMeasure(String idMeasure){

        Repository.getInstance().deleteOneMeasure(idMeasure, this.deleteMeasure);
    }
}
