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
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.database.entitys.Student;
import com.jjsj.finebodyapp.utils.KeyName;
import com.jjsj.finebodyapp.utils.ResultCode;
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
        this.viewModelStudent.observerDeleteStudent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if(aBoolean){

                    Toast.makeText(getApplicationContext(), R.string.MessageAlertDeleteStudentSuccess, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.putExtra(KeyName.KEY_NAME_STUDENT_ID, student.getId());
                    setResult(ResultCode.RESULT_CODE_STUDENT_DELETED, intent);
                    finish();
                }else{

                    Toast.makeText(getApplicationContext(), R.string.MessageAlertDeleteStudentFail, Toast.LENGTH_LONG).show();
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        this.student = (Student) extras.getSerializable(KeyName.KEY_NAME_STUDENT);
        getSupportActionBar().setTitle(this.student.getName());

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
            }
            return true;
            case android.R.id.home: finish();
            return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
}