package com.jjsj.finebodyapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jjsj.finebodyapp.repository.Repository;

public class ViewModelStudent extends ViewModel {

    public MutableLiveData<Boolean> deleteStudent;
    public MutableLiveData<Boolean> deleteMeasures;

    public LiveData<Boolean> observerDeleteStudent(){

        if(this.deleteStudent == null) this.deleteStudent = new MutableLiveData<>();
        return this.deleteStudent;
    }

    public LiveData<Boolean> observerDeleteMeasures(){

        if(this.deleteMeasures == null) this.deleteMeasures = new MutableLiveData<>();
        return this.deleteMeasures;
    }

    public void deleteStudent(String idStudent){

        Repository.getInstance().deleteOneStudent(idStudent, this.deleteStudent);
    }

    public void deleteMeasures(String idStudent){

        Repository.getInstance().deleteAllMeasure(idStudent, this.deleteMeasures);
    }

    public void deleteImg(String path){

        Repository.getInstance().deleteImg(path);
    }
}
