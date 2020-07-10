package com.jjsj.finebodyapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.database.entitys.Student;
import com.jjsj.finebodyapp.views.adapter.ViewPagerAdapter;

public class StudentActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_student, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_student_delete: deleteStudent();
            return true;
            case android.R.id.home: finish();
            return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void deleteStudent(){}
}