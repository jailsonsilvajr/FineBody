package com.jjsj.finebodyapp.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jjsj.finebodyapp.repository.Repository;

public class ViewModelLogin extends AndroidViewModel {

    private LiveData<String> idCoachViewModel;
    private Repository repository;

    public ViewModelLogin(Application application){

        super(application);
        this.repository = Repository.getInstance(application.getApplicationContext());
    }

    public LiveData<String> getIdCoach(){

        return this.idCoachViewModel;
    }

    public void doLogin(String email, String password){

        this.idCoachViewModel = this.repository.doLogin(email, password);
    }
}
