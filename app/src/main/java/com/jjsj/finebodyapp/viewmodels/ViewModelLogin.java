package com.jjsj.finebodyapp.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jjsj.finebodyapp.database.firebase.Response;
import com.jjsj.finebodyapp.preferences.PreferenceLogged;
import com.jjsj.finebodyapp.repository.Repository;

public class ViewModelLogin extends AndroidViewModel {

    private LiveData<Response> responseLogin;
    private Repository repository;
    private PreferenceLogged preference;

    public ViewModelLogin(Application application){

        super(application);
        this.repository = Repository.getInstance(application.getApplicationContext());
        this.preference = new PreferenceLogged(application.getApplicationContext());
    }

    public LiveData<Response> observerResponseLogin(){

        return this.responseLogin;
    }

    public void doLogin(String email, String password){

        this.responseLogin = this.repository.login(email, password);
    }

    public void insertIdCoachInPreferences(String idCoach){

        this.preference.setPreference(idCoach);
    }
}
