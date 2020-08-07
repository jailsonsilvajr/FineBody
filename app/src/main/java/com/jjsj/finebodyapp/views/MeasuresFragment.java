package com.jjsj.finebodyapp.views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jjsj.finebodyapp.R;

import com.jjsj.finebodyapp.database.entitys.Measure;
import com.jjsj.finebodyapp.database.entitys.Student;
import com.jjsj.finebodyapp.viewmodels.ViewModelMeasures;
import com.jjsj.finebodyapp.views.adapter.MeasuresAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MeasuresFragment extends Fragment {

    private final int REQUEST_EDIT_MEASURE = 0;
    private final int REQUEST_ADD_MEASURE = 1;

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
        this.student = (Student) extras.getSerializable("student");

        View view = inflater.inflate(R.layout.fragment_measures, container, false);
        this.progressBar = view.findViewById(R.id.layout_measures_progressBar);
        this.recyclerView = view.findViewById(R.id.layout_measures_recyclerView);
        this.recyclerView.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(getContext());
        this.recyclerView.setLayoutManager(this.layoutManager);
        this.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), this.recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Intent intent = new Intent(getContext(), EditMeasureActivity.class);
                intent.putExtra("measure", viewModelMeasures.getOneMeasure(position));
                startActivityForResult(intent, REQUEST_EDIT_MEASURE);
            }

            @Override
            public void onLongClick(View view, int position) {

                new MaterialAlertDialogBuilder(getActivity())
                        .setMessage(R.string.question_delete)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                view.findViewById(R.id.layout_measures_item_progressBar).setVisibility(View.VISIBLE);
                                viewModelMeasures.deleteMeasure(viewModelMeasures.getOneMeasure(position).getId());
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
        }));

        FloatingActionButton floatingActionButton = view.findViewById(R.id.layout_measures_floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openAddMeasure(student);
            }
        });

        this.viewModelMeasures = new ViewModelProvider(this).get(ViewModelMeasures.class);
        this.viewModelMeasures.setIdStudent(this.student.getId());
        this.viewModelMeasures.observerMeasures().observe(getViewLifecycleOwner(), new Observer<List<Measure>>() {
            @Override
            public void onChanged(List<Measure> measures) {

                progressBar.setVisibility(View.GONE);
                adapter = new MeasuresAdapter(sortMeasures(measures));
                recyclerView.setAdapter(adapter);
            }
        });
        getMeasures();

        return view;
    }

    private void getMeasures(){

        this.progressBar.setVisibility(View.VISIBLE);
        this.viewModelMeasures.getMeasures();
    }

    private List<Measure> sortMeasures(List<Measure> measures){

        Collections.sort(measures, new Comparator<Measure>() {
            @Override
            public int compare(Measure measure, Measure t1) {

                try {

                    Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(measure.getDate());
                    Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(t1.getDate());
                    return date1.compareTo(date2);
                } catch (ParseException e) {

                    e.printStackTrace();
                }
                return 0;
            }
        });
        return measures;
    }

    private void openAddMeasure(Student student){

        Intent intent = new Intent(getContext(), AddMeasureActivity.class);
        intent.putExtra("student", student);
        startActivityForResult(intent, REQUEST_ADD_MEASURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode == REQUEST_EDIT_MEASURE || requestCode == REQUEST_ADD_MEASURE) && resultCode == getActivity().RESULT_OK){

            getMeasures();
        }
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
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