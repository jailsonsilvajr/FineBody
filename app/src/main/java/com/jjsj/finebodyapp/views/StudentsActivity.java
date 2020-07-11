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

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.database.entitys.Student;
import com.jjsj.finebodyapp.database.firebase.Response;
import com.jjsj.finebodyapp.viewmodels.ViewModelStudents;
import com.jjsj.finebodyapp.views.adapter.StudentsAdapter;

import java.util.ArrayList;
import java.util.List;

public class StudentsActivity extends AppCompatActivity {

    private static final int REQUEST_UPDATE_STUDENT = 1;
    public static final int RESULT_CODE_UPDATE_STUDENT = 2;
    public static final int RESULT_CODE_DELETE_STUDENT = 3;
    private static final int REQUEST_ADD_STUDENT = 4;

    private ViewModelStudents viewModelStudents;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar progressBar;
    private FloatingActionButton floatingActionButton;
    private List<Student> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        getSupportActionBar().setTitle(getResources().getString(R.string.students));
        setupLayout();
        setViewModel();
        getStudentsInDatabase();
    }

    private void setupLayout(){

        this.progressBar = findViewById(R.id.layout_students_progressBar);
        this.recyclerView = findViewById(R.id.layout_students_recyclerView);
        this.recyclerView.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(this.layoutManager);
        this.floatingActionButton = findViewById(R.id.layout_students_floatingActionButton);
        this.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openActivityAddStudent();
            }
        });
    }

    private void openActivityAddStudent(){

        Intent intent = new Intent(this, AddStudentActivity.class);
        startActivityForResult(intent, getRequestAddStudent());
    }

    private ViewModelStudents getViewModel(){

        return this.viewModelStudents;
    }

    private void setViewModel(){

        this.viewModelStudents = new ViewModelProvider(this).get(ViewModelStudents.class);
    }

    //Search all students:
    private void getStudentsInDatabase(){

        showProgressBar(true);
        showRecyclerView(false);

        getViewModel().getStudents();
        getViewModel().observerResponseStudents().observe(this, new Observer<Response>() {
            @Override
            public void onChanged(Response res) {

                showProgressBar(false);
                showRecyclerView(true);
                if(res.getStatus() == 302){

                    setStudents((ArrayList) res.getObject());
                    setAdapter();
                    setRecyclerViewAdapter();
                }else{

                    new MaterialAlertDialogBuilder(StudentsActivity.this)
                            .setTitle(getResources().getString(R.string.error))
                            .setMessage(res.getMessage())
                            .show();
                }
            }
        });
    }

    private void showProgressBar(boolean show){

        this.progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void showRecyclerView(boolean show){

        this.recyclerView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private List<Student> getStudents(){

        return this.students;
    }

    private void setStudents(List<Student> students){

        this.students = students;
    }

    private RecyclerView.Adapter getAdapter(){

        return this.adapter;
    }

    private void setAdapter(){

        this.adapter = new StudentsAdapter(getStudents(), getViewModel(), this, REQUEST_UPDATE_STUDENT);
    }

    private void setRecyclerViewAdapter(){

        this.recyclerView.setAdapter(getAdapter());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == getRequestUpdateStudent()){

            if(resultCode == RESULT_CODE_UPDATE_STUDENT){

                Student updateStudent = (Student) data.getSerializableExtra("student");
                int position = data.getIntExtra("position", 0);

                getStudents().set(position, updateStudent);
                getAdapter().notifyItemChanged(position);
            }else if(resultCode == RESULT_CODE_DELETE_STUDENT){

                int position = data.getIntExtra("position", -1);
                if(position >= 0){

                    getStudents().remove(position);
                    getAdapter().notifyItemRemoved(position);
                }
            }
        }else if(requestCode == getRequestAddStudent() && resultCode == RESULT_OK){

            Student newStudent = (Student) data.getSerializableExtra("student");
            getStudents().add(newStudent);
            getAdapter().notifyDataSetChanged();
        }else{

            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private int getRequestUpdateStudent(){

        return this.REQUEST_UPDATE_STUDENT;
    }

    private int getRequestAddStudent(){

        return REQUEST_ADD_STUDENT;
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

        getViewModel().logout();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}