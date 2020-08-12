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
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jjsj.finebodyapp.R;

import com.jjsj.finebodyapp.database.entitys.Measure;
import com.jjsj.finebodyapp.database.entitys.Student;
import com.jjsj.finebodyapp.utils.KeyName;
import com.jjsj.finebodyapp.utils.RequestCode;
import com.jjsj.finebodyapp.utils.ResultCode;
import com.jjsj.finebodyapp.viewmodels.ViewModelMeasures;
import com.jjsj.finebodyapp.views.adapter.MeasuresAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MeasuresFragment extends Fragment {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private Student student;
    private ViewModelMeasures viewModelMeasures;

    private List<Measure> measures;
    private int position_last_delete;

    public MeasuresFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle extras = getActivity().getIntent().getExtras();
        this.student = (Student) extras.getSerializable(KeyName.KEY_NAME_STUDENT);

        this.viewModelMeasures = new ViewModelProvider(this).get(ViewModelMeasures.class);
        this.viewModelMeasures.setIdStudent(this.student.getId());
        this.viewModelMeasures.observerMeasures().observe(getViewLifecycleOwner(), new Observer<List<Measure>>() {
            @Override
            public void onChanged(List<Measure> listMeasures) {

                measures = listMeasures;
                progressBar.setVisibility(View.GONE);
                adapter = new MeasuresAdapter(sortMeasures(measures));
                recyclerView.setAdapter(adapter);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
        this.viewModelMeasures.observerDeleteMeasure().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if(aBoolean){

                    measures.remove(position_last_delete);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), R.string.ToastDeleteMeasureSuccess, Toast.LENGTH_LONG).show();
                }else{


                }
            }
        });
        this.viewModelMeasures.getAllMeasures();

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
                intent.putExtra(KeyName.KEY_NAME_MEASURE, measures.get(position));
                startActivityForResult(intent, RequestCode.REQUEST_CODE_EDIT_MEASURE);
            }

            @Override
            public void onLongClick(View view, int position) {

                new MaterialAlertDialogBuilder(getActivity())
                        .setMessage(R.string.question_delete)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                position_last_delete = position;
                                view.findViewById(R.id.layout_measures_item_progressBar).setVisibility(View.VISIBLE);
                                viewModelMeasures.deleteMeasure(measures.get(position).getId());
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

                openAddMeasure();
            }
        });

        return view;
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

    private void openAddMeasure(){

        Intent intent = new Intent(getContext(), AddMeasureActivity.class);
        intent.putExtra(KeyName.KEY_NAME_STUDENT_ID, this.student.getId());
        startActivityForResult(intent, RequestCode.REQUEST_CODE_ADD_MEASURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RequestCode.REQUEST_CODE_ADD_MEASURE && resultCode == ResultCode.RESULT_CODE_MEASURE_ADD){

            Measure newMeasure = (Measure) data.getSerializableExtra(KeyName.KEY_NAME_MEASURE);
            this.measures.add(newMeasure);
            this.adapter.notifyDataSetChanged();
        }else if(requestCode == RequestCode.REQUEST_CODE_EDIT_MEASURE && resultCode == ResultCode.RESULT_CODE_MEASURE_UPDATED){

            Measure updatedMeasure = (Measure) data.getSerializableExtra(KeyName.KEY_NAME_MEASURE);
            for(int i = 0; i < this.measures.size(); i++){

                if(this.measures.get(i).getId().equals(updatedMeasure.getId())){

                    this.measures.set(i, updatedMeasure);
                    this.adapter.notifyDataSetChanged();
                    break;
                }
            }
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