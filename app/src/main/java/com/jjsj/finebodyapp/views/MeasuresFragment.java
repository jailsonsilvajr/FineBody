package com.jjsj.finebodyapp.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.database.sqlite.entitys.Measure;
import com.jjsj.finebodyapp.database.sqlite.entitys.Student;
import com.jjsj.finebodyapp.viewmodels.ViewModelMeasures;
import com.jjsj.finebodyapp.views.adapter.MeasuresAdapter;

import java.util.List;

public class MeasuresFragment extends Fragment {

    private ProgressBar progressBar;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private Student student;
    private ViewModelMeasures viewModelMeasures;

    public MeasuresFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle extras = getActivity().getIntent().getExtras();
        if(extras != null) this.student = (Student) extras.getSerializable("student");
        else this.student = null;

        this.viewModelMeasures = new ViewModelProvider(this).get(ViewModelMeasures.class);

        View view = inflater.inflate(R.layout.fragment_measures, container, false);
        this.progressBar = view.findViewById(R.id.layout_measures_progressBar);
        this.recyclerView = view.findViewById(R.id.layout_measures_recyclerView);
        this.recyclerView.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(getContext());
        this.recyclerView.setLayoutManager(this.layoutManager);

        FloatingActionButton floatingActionButton = view.findViewById(R.id.layout_measures_floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openAddMeasure(student);
            }
        });

        return view;
    }

    private void openAddMeasure(Student student){

        Intent intent = new Intent(getContext(), AddMeasureActivity.class);
        intent.putExtra("student", student);
        startActivity(intent);
    }

    @Override
    public void onResume() {

        super.onResume();

        this.progressBar.setVisibility(View.VISIBLE);
        this.viewModelMeasures.getListMeasures(this.student);
        this.viewModelMeasures.observerMeasures().observe(getViewLifecycleOwner(), new Observer<List<Measure>>() {
            @Override
            public void onChanged(List<Measure> measures) {

                progressBar.setVisibility(View.GONE);
                adapter = new MeasuresAdapter(measures);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}