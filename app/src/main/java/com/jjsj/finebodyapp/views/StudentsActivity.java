package com.jjsj.finebodyapp.views;

import androidx.annotation.NonNull;
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

import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.database.sqlite.entitys.Coach;
import com.jjsj.finebodyapp.database.sqlite.entitys.Student;
import com.jjsj.finebodyapp.viewmodels.ViewModelStudents;
import com.jjsj.finebodyapp.views.adapter.StudentsAdapter;

import java.util.List;

public class StudentsActivity extends AppCompatActivity {

    private ViewModelStudents viewModelStudents;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        getSupportActionBar().setTitle(getResources().getString(R.string.students));

        this.recyclerView = findViewById(R.id.layout_students_recyclerView);
        this.recyclerView.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(this.layoutManager);

        this.viewModelStudents = new ViewModelProvider(this).get(ViewModelStudents.class);
        this.viewModelStudents.getCoach();
        this.viewModelStudents.getCoachViewModel().observe(this, new Observer<Coach>() {
            @Override
            public void onChanged(Coach coach) {

                getListStudents(coach);
            }
        });
    }

    private void doLogout(){

        this.viewModelStudents.logout();
        openActivityLogin();
        finish();
    }

    private void getListStudents(Coach coach){

        this.viewModelStudents.getStudents(coach);
        this.viewModelStudents.getStudentsViewModel().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {

                adapter = new StudentsAdapter(students);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    public void openActivityLogin(){

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

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
}