package com.jjsj.finebodyapp.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jjsj.finebodyapp.repository.Repository;

public class ViewModelRegister extends AndroidViewModel {

    private LiveData<String> idCoach;
    private String email;
    private String password;
    private String repeatPassword;

    private Repository repository;

    public ViewModelRegister(Application application){

        super(application);
        this.repository = Repository.getInstance(application.getApplicationContext());
    }

    public LiveData<String> getIdCoach() {
        return idCoach;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void doRegister(String email, String password){

        this.idCoach = this.repository.doRegister(email, password);
    }
}
