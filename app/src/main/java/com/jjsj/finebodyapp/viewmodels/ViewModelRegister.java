package com.jjsj.finebodyapp.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jjsj.finebodyapp.repository.Repository;

public class ViewModelRegister extends AndroidViewModel {

    private LiveData<String> idCoach;

    private Repository repository;

    public ViewModelRegister(Application application){

        super(application);
        this.repository = Repository.getInstance(application.getApplicationContext());
    }

    public LiveData<String> getIdCoach() {
        return idCoach;
    }

    public void doRegister(String email, String password){

        this.idCoach = this.repository.doRegister(email, password);
    }

    public void insertIdCoachInRepository(String id){

        this.repository.insertIdCoach(id);
    }
}
