package com.jjsj.finebodyapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputLayout;
import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.database.sqlite.entitys.Measure;
import com.jjsj.finebodyapp.database.sqlite.entitys.Student;
import com.jjsj.finebodyapp.viewmodels.ViewModelEditMeasure;

public class EditMeasureActivity extends AppCompatActivity {

    private Student student;
    private Measure measure;
    private TextInputLayout textInputLayout_date;
    private TextInputLayout textInputLayout_weight;
    private TextInputLayout textInputLayout_right_arm;
    private TextInputLayout textInputLayout_left_arm;
    private TextInputLayout textInputLayout_waist;
    private TextInputLayout textInputLayout_hip;
    private TextInputLayout textInputLayout_right_calf;
    private TextInputLayout textInputLayout_left_calf;
    private Button button_save;
    private ProgressBar progressBar;

    private ViewModelEditMeasure viewModelEditMeasure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_measure);

        getSupportActionBar().setTitle(getResources().getString(R.string.edit_datas));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Show button
        getSupportActionBar().setHomeButtonEnabled(true); //Activate button

        Bundle extras = getIntent().getExtras();
        if(extras != null) {

            this.student = (Student) extras.getSerializable("student");
            this.measure = (Measure) extras.getSerializable("measure");
        }
        else{

            this.student = null;
            this.measure = null;
        }

        this.textInputLayout_date = findViewById(R.id.layout_edit_measures_textInput_date);
        this.textInputLayout_date.getEditText().setText(this.measure.getDate());

        this.textInputLayout_weight = findViewById(R.id.layout_edit_measures_textInput_weight);
        this.textInputLayout_weight.getEditText().setText(Float.toString(this.measure.getWeight()));

        this.textInputLayout_right_arm = findViewById(R.id.layout_edit_measures_textInput_right_arm);
        this.textInputLayout_right_arm.getEditText().setText(Float.toString(this.measure.getRight_arm()));

        this.textInputLayout_left_arm = findViewById(R.id.layout_edit_measures_textInput_left_arm);
        this.textInputLayout_left_arm.getEditText().setText(Float.toString(this.measure.getLeft_arm()));

        this.textInputLayout_waist = findViewById(R.id.layout_edit_measures_textInput_waist);
        this.textInputLayout_waist.getEditText().setText(Float.toString(this.measure.getWaist()));

        this.textInputLayout_hip = findViewById(R.id.layout_edit_measures_textInput_hip);
        this.textInputLayout_hip.getEditText().setText(Float.toString(this.measure.getHip()));

        this.textInputLayout_right_calf = findViewById(R.id.layout_edit_measures_textInput_right_calf);
        this.textInputLayout_right_calf.getEditText().setText(Float.toString(this.measure.getRight_calf()));

        this.textInputLayout_left_calf = findViewById(R.id.layout_edit_measures_textInput_left_calf);
        this.textInputLayout_left_calf.getEditText().setText(Float.toString(this.measure.getLeft_calf()));

        this.button_save = findViewById(R.id.layout_edit_measures_button_save);
        this.button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveMeasure();
            }
        });
        this.progressBar = findViewById(R.id.layout_edit_measures_progressBar);
        this.progressBar.setVisibility(View.GONE);
        this.viewModelEditMeasure = new ViewModelProvider(this).get(ViewModelEditMeasure.class);
    }

    private void saveMeasure(){

        this.button_save.setVisibility(View.GONE);
        this.progressBar.setVisibility(View.VISIBLE);

        Measure measure = new Measure();
        measure.setId_sqlite(this.measure.getId_sqlite());
        measure.setId_firebase(this.measure.getId_firebase());
        measure.setId_student_sqlite(this.student.getId_sqlite());
        measure.setId_student_firebase(this.student.getId_firebase());

        measure.setDate(this.textInputLayout_date.getEditText().getText().toString());
        measure.setWeight(Float.parseFloat(this.textInputLayout_weight.getEditText().getText().toString()));
        measure.setRight_arm(Float.parseFloat(this.textInputLayout_right_arm.getEditText().getText().toString()));
        measure.setLeft_arm(Float.parseFloat(this.textInputLayout_left_arm.getEditText().getText().toString()));
        measure.setWaist(Float.parseFloat(this.textInputLayout_waist.getEditText().getText().toString()));
        measure.setHip(Float.parseFloat(this.textInputLayout_hip.getEditText().getText().toString()));
        measure.setRight_calf(Float.parseFloat(this.textInputLayout_right_calf.getEditText().getText().toString()));
        measure.setLeft_calf(Float.parseFloat(this.textInputLayout_left_calf.getEditText().getText().toString()));

        this.viewModelEditMeasure.editMeasure(measure);
        this.viewModelEditMeasure.observeMeasure().observe(this, new Observer<Measure>() {
            @Override
            public void onChanged(Measure measure) {

                if(measure != null) finish();
                else{

                    progressBar.setVisibility(View.GONE);
                    button_save.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home: finish();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
}