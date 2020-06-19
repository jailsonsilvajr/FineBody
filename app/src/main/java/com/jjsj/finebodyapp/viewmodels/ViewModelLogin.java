package com.jjsj.finebodyapp.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jjsj.finebodyapp.repository.Repository;

public class ViewModelLogin extends AndroidViewModel {

    private LiveData<String> idCoach;
    private Repository repository;

    public ViewModelLogin(Application application){

        super(application);
        this.repository = Repository.getInstance(application.getApplicationContext());
    }

    public LiveData<String> getIdCoach(){

        return this.idCoach;
    }

    public void checkCredentials(String email, String password){

        this.idCoach = this.repository.checkCredentials(email, password);
    }

    public void insertIdCoachInRepository(String id){

        this.repository.insertIdCoach(id);
    }
}
