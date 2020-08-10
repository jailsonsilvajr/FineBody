package com.jjsj.finebodyapp.views;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.database.entitys.Measure;
import com.jjsj.finebodyapp.database.entitys.Student;
import com.jjsj.finebodyapp.viewmodels.ViewModelGraph;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@SuppressLint("SetJavaScriptEnabled")
public class GraphsFragment extends Fragment {

    private ViewModelGraph viewModelGraph;
    private Student student;

    private ProgressBar progressBar;
    private WebView webView;

    private List<Measure> listMeasures;

    public GraphsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle extra = getActivity().getIntent().getExtras();
        this.student = (Student) extra.getSerializable("student");

        this.viewModelGraph = new ViewModelProvider(this).get(ViewModelGraph.class);
        this.viewModelGraph.observerListMeasure().observe(getViewLifecycleOwner(), new Observer<List<Measure>>() {
            @Override
            public void onChanged(List<Measure> measures) {

                if(measures != null){

                    Collections.sort(measures, new Comparator<Measure>() {
                        @Override
                        public int compare(Measure measure, Measure t1) {

                            try {
                                //sort
                                Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(measure.getDate());
                                Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(t1.getDate());
                                return date1.compareTo(date2);
                            } catch (ParseException e) {

                                e.printStackTrace();
                            }
                            return 0;
                        }
                    });
                    listMeasures = measures;
                    webView.loadUrl("file:///android_asset/webView.html");

                    webView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        View view = inflater.inflate(R.layout.fragment_graphs, container, false);
        this.progressBar = view.findViewById(R.id.fragment_graph_progressBar);
        this.webView = view.findViewById(R.id.webView);

        this.webView.addJavascriptInterface(new WebAppInterface(), "Android");
        this.webView.getSettings().setJavaScriptEnabled(true);

        return view;
    }

    @Override
    public void onResume() {

        super.onResume();

        this.webView.setVisibility(View.GONE);
        this.progressBar.setVisibility(View.VISIBLE);
        this.viewModelGraph.getListMeasure(this.student.getId());
    }

    public class WebAppInterface{

        @JavascriptInterface
        public int getSizeMeasures(){

            return listMeasures.size();
        }

        @JavascriptInterface
        public String getDate(int i){

            return listMeasures.get(i).getDate();
        }

        @JavascriptInterface
        public float getWeight(int i){

            return listMeasures.get(i).getWeight();
        }

        @JavascriptInterface
        public float getRightArm(int i){

            return listMeasures.get(i).getRightArm();
        }

        @JavascriptInterface
        public float getLeftArm(int i){

            return listMeasures.get(i).getLeftArm();
        }

        @JavascriptInterface
        public float getWaist(int i){

            return listMeasures.get(i).getWaist();
        }

        @JavascriptInterface
        public float getHip(int i){

            return listMeasures.get(i).getHip();
        }

        @JavascriptInterface
        public float getRightCalf(int i){

            return listMeasures.get(i).getRightCalf();
        }

        @JavascriptInterface
        public float getLeftCalf(int i){

            return listMeasures.get(i).getLeftCalf();
        }
    }
}