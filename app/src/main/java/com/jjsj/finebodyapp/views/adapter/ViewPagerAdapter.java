package com.jjsj.finebodyapp.views.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.jjsj.finebodyapp.views.GraphsFragment;
import com.jjsj.finebodyapp.views.MeasuresFragment;
import com.jjsj.finebodyapp.views.ProfileFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private int numOfTabs;

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int numOfTabs) {

        super(fragmentManager, lifecycle);
        this.numOfTabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0: return new ProfileFragment();
            case 1: return new MeasuresFragment();
            case 2: return new GraphsFragment();
            default: return null;
        }
    }

    @Override
    public int getItemCount() {

        return this.numOfTabs;
    }
}