package com.jjsj.finebodyapp.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jjsj.finebodyapp.database.firebase.Response;
import com.jjsj.finebodyapp.preferences.PreferenceLogged;
import com.jjsj.finebodyapp.repository.Repository;

import java.io.IOException;

public class ViewModelStudents extends AndroidViewModel {

    private Repository repository;
    private LiveData<Response> responseStudents;
    private LiveData<byte[]> responseImageProfile;
    private PreferenceLogged preference;

    public ViewModelStudents(Application application){

        super(application);
        this.repository = Repository.getInstance(application.getApplicationContext());
        this.preference = new PreferenceLogged(application.getApplicationContext());
    }

    public LiveData<Response> observerResponseStudents() {

        return this.responseStudents;
    }

    public void getStudents(){

        this.responseStudents = this.repository.getStudents(this.preference.getPreference());
    }

    public LiveData<byte[]> observerResponseImageProfile(){

        return this.responseImageProfile;
    }

    public void getImageProfile(String path) throws IOException {

        this.responseImageProfile = this.repository.downloadPhoto(path);
    }

    public void logout(){

        this.preference.setPreference(null);
    }
}
