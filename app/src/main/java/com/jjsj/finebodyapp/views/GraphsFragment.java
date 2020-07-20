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
import com.jjsj.finebodyapp.database.firebase.Response;
import com.jjsj.finebodyapp.viewmodels.ViewModelGraph;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@SuppressLint("SetJavaScriptEnabled")
public class GraphsFragment extends Fragment {

    private ViewModelGraph viewModelGraph;
    private Student student;

    private ProgressBar progressBar;
    private WebView webViewWeight;
    private WebView webViewArm;
    private WebView webViewWaist;

    private List<Measure> measures;

    public GraphsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle extra = getActivity().getIntent().getExtras();
        this.student = (Student) extra.getSerializable("student");

        this.viewModelGraph = new ViewModelProvider(this).get(ViewModelGraph.class);

        View view = inflater.inflate(R.layout.fragment_graphs, container, false);

        this.progressBar = view.findViewById(R.id.fragment_graph_progressBar);
        this.webViewWeight = view.findViewById(R.id.webView_weight);
        this.webViewArm = view.findViewById(R.id.webView_arm);
        this.webViewWaist = view.findViewById(R.id.webView_waist);

        this.webViewWeight.addJavascriptInterface(new WebAppInterface(), "Android");
        this.webViewArm.addJavascriptInterface(new WebAppInterface(), "Android");
        this.webViewWaist.addJavascriptInterface(new WebAppInterface(), "Android");

        this.webViewWeight.getSettings().setJavaScriptEnabled(true);
        this.webViewArm.getSettings().setJavaScriptEnabled(true);
        this.webViewWaist.getSettings().setJavaScriptEnabled(true);

        return view;
    }

    @Override
    public void onResume() {

        super.onResume();

        this.webViewWeight.setVisibility(View.GONE);
        this.webViewArm.setVisibility(View.GONE);
        this.webViewWaist.setVisibility(View.GONE);
        this.progressBar.setVisibility(View.VISIBLE);
        this.viewModelGraph.getMeasures(this.student.getId());
        this.viewModelGraph.observerResponseMeasures().observe(getViewLifecycleOwner(), new Observer<Response>() {
            @Override
            public void onChanged(Response response) {

                if(response.getStatus() == 200){

                    measures = (ArrayList) response.getObject();
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
                    webViewWeight.loadUrl("file:///android_asset/weight_column_chart.html");
                    webViewArm.loadUrl("file:///android_asset/arm_line_chart.html");
                    webViewWaist.loadUrl("file:///android_asset/waist_line_chart.html");

                    webViewWeight.setVisibility(View.VISIBLE);
                    webViewArm.setVisibility(View.VISIBLE);
                    webViewWaist.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    public class WebAppInterface{

        @JavascriptInterface
        public int getLengthMeasures(){

            return measures.size();
        }

        @JavascriptInterface
        public String getDate(int i){

            return measures.get(i).getDate();
        }

        @JavascriptInterface
        public float getWeight(int i){

            return measures.get(i).getWeight();
        }

        @JavascriptInterface
        public float getRightArm(int i){

            return measures.get(i).getRightArm();
        }

        @JavascriptInterface
        public float getLeftArm(int i){

            return measures.get(i).getLeftArm();
        }

        @JavascriptInterface
        public float getWaist(int i){

            return measures.get(i).getWaist();
        }
    }
}