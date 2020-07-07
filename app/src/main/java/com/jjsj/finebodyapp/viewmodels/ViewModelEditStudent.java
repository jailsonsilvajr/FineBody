package com.jjsj.finebodyapp.viewmodels;

import android.app.Application;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jjsj.finebodyapp.database.sqlite.entitys.Student;
import com.jjsj.finebodyapp.repository.Repository;

public class ViewModelEditStudent extends AndroidViewModel {

    private Repository repository;
    private LiveData<Boolean> upload;

    public ViewModelEditStudent(@NonNull Application application) {

        super(application);
        this.repository = Repository.getInstance(application.getApplicationContext());
    }

    public LiveData<Boolean> observerUpload(){

        return this.upload;
    }

    public void doUpload(ImageView imageView, String path, Student newStudent){

        
    }

}
