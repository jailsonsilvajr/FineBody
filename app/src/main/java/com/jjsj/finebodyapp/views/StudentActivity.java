package com.jjsj.finebodyapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.database.entitys.Student;
import com.jjsj.finebodyapp.viewmodels.ViewModelStudent;
import com.jjsj.finebodyapp.views.adapter.ViewPagerAdapter;

public class StudentActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    private Student student;
    private ViewModelStudent viewModelStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        this.viewModelStudent = new ViewModelProvider(this).get(ViewModelStudent.class);

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

    private void setObservers(){

        //Observer delete Student
        this.viewModelStudent.observerDeleteStudent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if(aBoolean){

                    new MaterialAlertDialogBuilder(StudentActivity.this)
                            .setTitle(getResources().getString(R.string.TitleAlertDeleteStudentSuccess))
                            .setMessage(getResources().getString(R.string.MessageAlertDeleteStudentSuccess))
                            .show();
                    viewModelStudent.deleteImg(student.getPathPhoto());
                    viewModelStudent.deleteMeasures(student.getId());
                }else{


                }
            }
        });

        //Observer delete Measure
        this.viewModelStudent.observerDeleteMeasures().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_student, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_student_delete: {

                this.viewModelStudent.deleteStudent(this.student.getId());
                setObservers();
            }
            return true;
            case android.R.id.home: finish();
            return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
}