package com.jjsj.finebodyapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.database.entitys.Measure;
import com.jjsj.finebodyapp.database.entitys.Student;
import com.jjsj.finebodyapp.database.firebase.Response;
import com.jjsj.finebodyapp.viewmodels.ViewModelStudent;
import com.jjsj.finebodyapp.views.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    private Student student;
    private ViewModelStudent viewModelStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        setViewModelStudent();

        Bundle extras = getIntent().getExtras();
        this.student = (Student) extras.getSerializable("student");
        getSupportActionBar().setTitle(student.getName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Show button
        getSupportActionBar().setHomeButtonEnabled(true); //Activate button

        this.tabLayout = findViewById(R.id.layout_student_tabLayout);
        this.viewPager = findViewById(R.id.layout_student_viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), this.tabLayout.getTabCount());
        this.viewPager.setAdapter(viewPagerAdapter);
        this.viewPager.setCurrentItem(1);

        new TabLayoutMediator(this.tabLayout, this.viewPager, (tab, position) -> {

            switch (position){
                case 0: tab.setText(R.string.profile);
                break;
                case 1: tab.setText(R.string.measures);
                break;
                case 2: tab.setText(R.string.graph);
                break;
                default: tab.setText("TAB " + position);
            }
        }).attach();
    }

    public ViewModelStudent getViewModelStudent() {

        return viewModelStudent;
    }

    public void setViewModelStudent() {

        this.viewModelStudent = new ViewModelProvider(this).get(ViewModelStudent.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_student, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_student_delete: getMeasures(this.student.getId());
            return true;
            case android.R.id.home: finish();
            return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void getMeasures(String idStudent){

        getViewModelStudent().getMeasures(idStudent);
        getViewModelStudent().observerResponseGetMeasures().observe(this, new Observer<Response>() {
            @Override
            public void onChanged(Response response) {

                if(response.getStatus() == 200){

                    deleteMeasures((ArrayList) response.getObject());
                }
            }
        });
    }

    private void deleteMeasures(List<Measure> measures){

        getViewModelStudent().deleteMeasures(measures);
        getViewModelStudent().observerResponseDeleteMeasures().observe(this, new Observer<Response>() {
            @Override
            public void onChanged(Response response) {

                if(response.getStatus() == 200){

                    deleteStudent();
                }
            }
        });
    }

    private void deleteStudent(){

        getViewModelStudent().deleteStudent(this.student.getId());
        getViewModelStudent().observerResponseDeleteStudent().observe(this, new Observer<Response>() {
            @Override
            public void onChanged(Response response) {

                if(response.getStatus() == 200){

                    new MaterialAlertDialogBuilder(StudentActivity.this)
                            .setTitle(getResources().getString(R.string.TitleAlertDeleteStudentSuccess))
                            .setMessage(getResources().getString(R.string.MessageAlertDeleteStudentSuccess))
                            .show();

                    Bundle extra = getIntent().getExtras();
                    int position = extra.getInt("studentPosition");

                    Intent intent = new Intent();
                    intent.putExtra("position", position);
                    setResult(StudentsActivity.RESULT_CODE_DELETE_STUDENT, intent);

                    getViewModelStudent().deleteImg(student.getPathPhoto());
                    finish();
                }else{

                    new MaterialAlertDialogBuilder(StudentActivity.this)
                            .setTitle(getResources().getString(R.string.TitleAlertDeleteStudentFail))
                            .setMessage(getResources().getString(R.string.MessageAlertDeleteStudentFail))
                            .show();
                }
            }
        });
    }
}