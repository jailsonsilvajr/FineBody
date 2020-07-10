package com.jjsj.finebodyapp.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jjsj.finebodyapp.database.firebase.Response;
import com.jjsj.finebodyapp.preferences.PreferenceLogged;
import com.jjsj.finebodyapp.repository.Repository;

public class ViewModelRegister extends AndroidViewModel {

    private LiveData<Response> responseRegister;
    private Repository repository;
    private PreferenceLogged preference;

    public ViewModelRegister(Application application){

        super(application);
        this.repository = Repository.getInstance(application.getApplicationContext());
        this.preference = new PreferenceLogged(application.getApplicationContext());
    }

    public LiveData<Response> observerResponseRegister() {

        return this.responseRegister;
    }

    public void doRegister(String email, String password){

        this.responseRegister = this.repository.register(email, password);
    }

    public void insertIdCoachInPreferences(String idCoach){

        this.preference.setPreference(idCoach);
    }
}
