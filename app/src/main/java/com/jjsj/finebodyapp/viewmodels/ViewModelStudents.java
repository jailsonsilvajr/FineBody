package com.jjsj.finebodyapp.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jjsj.finebodyapp.database.sqlite.entitys.Coach;
import com.jjsj.finebodyapp.database.sqlite.entitys.Student;
import com.jjsj.finebodyapp.repository.Repository;

import java.util.List;

public class ViewModelStudents extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Student>> students;
    private LiveData<Coach> coach;

    public ViewModelStudents(Application application){

        super(application);
        this.repository = Repository.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Student>> getStudentsViewModel() {

        return this.students;
    }

    public void getStudents(Coach coach){

        this.students = this.repository.getStudentsRepository(coach);
    }

    public LiveData<Coach> getCoachViewModel() {

        return coach;
    }

    public void getCoach(){

        this.coach = this.repository.getCoach();
    }

    public void logout(){

        this.repository.doLogout(getApplication().getApplicationContext());
    }
}
