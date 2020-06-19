package com.jjsj.finebodyapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.database.sqlite.entitys.Coach;
import com.jjsj.finebodyapp.database.sqlite.entitys.Student;
import com.jjsj.finebodyapp.viewmodels.ViewModelStudents;

import java.util.List;

public class StudentsActivity extends AppCompatActivity {

    private ViewModelStudents viewModelStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        this.viewModelStudents = new ViewModelProvider(this).get(ViewModelStudents.class);
        this.viewModelStudents.getCoach();
        this.viewModelStudents.getCoachViewModel().observe(this, new Observer<Coach>() {
            @Override
            public void onChanged(Coach coach) {

                getListStudents(coach);
            }
        });
    }

    private void getListStudents(Coach coach){

        this.viewModelStudents.getStudents(coach);
        this.viewModelStudents.getStudentsViewModel().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {


            }
        });
    }
}