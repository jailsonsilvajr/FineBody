package com.jjsj.finebodyapp.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.database.entitys.Student;
import com.jjsj.finebodyapp.preferences.PreferenceLogged;
import com.jjsj.finebodyapp.utils.KeyName;
import com.jjsj.finebodyapp.utils.RequestCode;
import com.jjsj.finebodyapp.utils.ResultCode;
import com.jjsj.finebodyapp.viewmodels.ViewModelStudents;
import com.jjsj.finebodyapp.views.adapter.StudentsAdapter;

import java.util.List;

public class StudentsActivity extends AppCompatActivity {

    private ViewModelStudents viewModelStudents;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar progressBar;
    private FloatingActionButton floatingActionButton;

    private List<Student> listStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        getSupportActionBar().setTitle(getResources().getString(R.string.students));
        this.progressBar = findViewById(R.id.layout_students_progressBar);
        this.recyclerView = findViewById(R.id.layout_students_recyclerView);
        this.recyclerView.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(this.layoutManager);
        setClickItemRecyclerView();

        this.viewModelStudents = new ViewModelProvider(this).get(ViewModelStudents.class);
        this.viewModelStudents.observerListStudent().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {

                setVisibilityProgressBar(View.GONE);
                if(students != null){

                    insertListInRecyclerView(students);
                }else{


                }
                setVisibilityRecyclerView(View.VISIBLE);
            }
        });
        this.viewModelStudents.getListStudent();

        this.floatingActionButton = findViewById(R.id.layout_students_floatingActionButton);
        setClickFloatingAction();
    }

    private void setClickItemRecyclerView(){

        this.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, this.recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Intent intent = new Intent(StudentsActivity.this, StudentActivity.class);
                intent.putExtra(KeyName.KEY_NAME_STUDENT, listStudent.get(position));
                startActivityForResult(intent, RequestCode.REQUEST_CODE_STUDENT);
            }

            @Override
            public void onLongClick(View view, int position) {


            }
        }));
    }

    private void setClickFloatingAction(){

        this.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StudentsActivity.this, AddStudentActivity.class);
                startActivityForResult(intent, RequestCode.REQUEST_CODE_ADD_STUDENT);
            }
        });
    }

    private void insertListInRecyclerView(List<Student> listStudent){

        this.listStudent = listStudent;
        if(this.adapter == null){

            this.adapter = new StudentsAdapter(this.listStudent);
            this.recyclerView.setAdapter(this.adapter);
        }else{

            this.adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RequestCode.REQUEST_CODE_ADD_STUDENT && resultCode == ResultCode.RESULT_CODE_STUDENT_ADD){

            Student newStudent = (Student) data.getSerializableExtra(KeyName.KEY_NAME_STUDENT);
            this.listStudent.add(newStudent);
            this.adapter.notifyDataSetChanged();
        }else if(requestCode == RequestCode.REQUEST_CODE_STUDENT){

            if(resultCode == ResultCode.RESULT_CODE_STUDENT_UPDATED){

                Student updatedStudent = (Student) data.getSerializableExtra(KeyName.KEY_NAME_STUDENT);
                for(int i = 0; i < this.listStudent.size(); i++){

                    if(this.listStudent.get(i).getId().equals(updatedStudent.getId())){

                        this.listStudent.set(i, updatedStudent);
                        this.adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }else if(resultCode == ResultCode.RESULT_CODE_STUDENT_DELETED){

                String idStudent = data.getStringExtra(KeyName.KEY_NAME_STUDENT_ID);
                for(int i = 0; i < this.listStudent.size(); i++){

                    if(this.listStudent.get(i).getId().equals(idStudent)){

                        this.listStudent.remove(i);
                        this.adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
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

    private void setVisibilityProgressBar(int visibility){

        this.progressBar.setVisibility(visibility);
    }

    private void setVisibilityRecyclerView(int visibility){

        this.recyclerView.setVisibility(visibility);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private RecyclerTouchListener.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final RecyclerTouchListener.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

        public interface ClickListener {
            void onClick(View view, int position);

            void onLongClick(View view, int position);
        }
    }
}