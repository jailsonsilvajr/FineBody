package com.jjsj.finebodyapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jjsj.finebodyapp.database.sqlite.entitys.Measure;
import com.jjsj.finebodyapp.database.sqlite.entitys.Student;
import com.jjsj.finebodyapp.repository.Repository;

import java.util.List;

public class ViewModelMeasures extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Measure>> measures;

    public ViewModelMeasures(@NonNull Application application) {

        super(application);
        this.repository = Repository.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Measure>> observerMeasures(){

        return this.measures;
    }

    public void getListMeasures(Student student){

        this.measures = this.repository.getMeasuresRepository(student);
    }
}
