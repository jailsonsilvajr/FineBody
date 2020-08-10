package com.jjsj.finebodyapp.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.database.entitys.Student;
import com.jjsj.finebodyapp.preferences.PreferenceLogged;
import com.jjsj.finebodyapp.viewmodels.ViewModelStudents;
import com.jjsj.finebodyapp.views.adapter.StudentsAdapter;

import java.util.List;

public class StudentsActivity extends AppCompatActivity {

    private static final int REQUEST_ACTIVITY_STUDENT = 1;
    private static final int REQUEST_ACTIVITY_ADD_STUDENT = 2;

    private ViewModelStudents viewModelStudents;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar progressBar;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        getSupportActionBar().setTitle(getResources().getString(R.string.students));

        this.viewModelStudents = new ViewModelProvider(this).get(ViewModelStudents.class);
        setObserverListStudent();
        getListStudent();

        this.progressBar = findViewById(R.id.layout_students_progressBar);
        this.recyclerView = findViewById(R.id.layout_students_recyclerView);
        this.recyclerView.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(this.layoutManager);

        setVisibilityProgressBar(View.VISIBLE);

        this.floatingActionButton = findViewById(R.id.layout_students_floatingActionButton);
        this.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openActivityAddStudent();
            }
        });
    }

    private void getListStudent(){

        this.viewModelStudents.getListStudent(new PreferenceLogged(this).getPreference());
    }

    private void setObserverListStudent(){

        this.viewModelStudents.observerListStudent().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> listStudent) {

                setVisibilityProgressBar(View.GONE);

                if(listStudent != null){

                    insertItemInRecycler(listStudent);
                }else{

                    //?????????????????????????????????????
                }
            }
        });
    }

    private void openActivityAddStudent(){

        Intent intent = new Intent(this, AddStudentActivity.class);
        startActivityForResult(intent, REQUEST_ACTIVITY_ADD_STUDENT);
    }

    private void setVisibilityProgressBar(int visibility){

        this.progressBar.setVisibility(visibility);
        this.recyclerView.setVisibility(visibility == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    private void insertItemInRecycler(List<Student> listStudent){

        this.adapter = new StudentsAdapter(listStudent, this.viewModelStudents, this, REQUEST_ACTIVITY_STUDENT);
        this.recyclerView.setAdapter(this.adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode == REQUEST_ACTIVITY_ADD_STUDENT || requestCode == REQUEST_ACTIVITY_STUDENT) && resultCode == RESULT_OK){

            getListStudent();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_students, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_students_logout:
                doLogout();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void doLogout(){

        new PreferenceLogged(this).setPreference(null);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}