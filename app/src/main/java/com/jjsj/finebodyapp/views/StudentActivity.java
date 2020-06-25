package com.jjsj.finebodyapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.views.adapter.ViewPagerAdapter;

public class StudentActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

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
}