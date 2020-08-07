package com.jjsj.finebodyapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputLayout;
import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.database.entitys.Measure;
import com.jjsj.finebodyapp.database.entitys.Student;
import com.jjsj.finebodyapp.database.firebase.Response;
import com.jjsj.finebodyapp.viewmodels.ViewModelEditMeasure;

public class EditMeasureActivity extends AppCompatActivity {

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
        this.measure = (Measure) extras.getSerializable("measure");

        this.textInputLayout_date = findViewById(R.id.layout_edit_measures_textInput_date);
        this.textInputLayout_date.getEditText().setText(this.measure.getDate());

        this.textInputLayout_weight = findViewById(R.id.layout_edit_measures_textInput_weight);
        this.textInputLayout_weight.getEditText().setText(Float.toString(this.measure.getWeight()));

        this.textInputLayout_right_arm = findViewById(R.id.layout_edit_measures_textInput_right_arm);
        this.textInputLayout_right_arm.getEditText().setText(Float.toString(this.measure.getRightArm()));

        this.textInputLayout_left_arm = findViewById(R.id.layout_edit_measures_textInput_left_arm);
        this.textInputLayout_left_arm.getEditText().setText(Float.toString(this.measure.getLeftArm()));

        this.textInputLayout_waist = findViewById(R.id.layout_edit_measures_textInput_waist);
        this.textInputLayout_waist.getEditText().setText(Float.toString(this.measure.getWaist()));

        this.textInputLayout_hip = findViewById(R.id.layout_edit_measures_textInput_hip);
        this.textInputLayout_hip.getEditText().setText(Float.toString(this.measure.getHip()));

        this.textInputLayout_right_calf = findViewById(R.id.layout_edit_measures_textInput_right_calf);
        this.textInputLayout_right_calf.getEditText().setText(Float.toString(this.measure.getRightCalf()));

        this.textInputLayout_left_calf = findViewById(R.id.layout_edit_measures_textInput_left_calf);
        this.textInputLayout_left_calf.getEditText().setText(Float.toString(this.measure.getLeftCalf()));

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

        Measure newMeasure = new Measure();
        newMeasure.setId(this.measure.getId());
        newMeasure.setIdStudent(this.measure.getIdStudent());

        newMeasure.setDate(this.textInputLayout_date.getEditText().getText().toString());
        newMeasure.setWeight(Float.parseFloat(this.textInputLayout_weight.getEditText().getText().toString()));
        newMeasure.setRightArm(Float.parseFloat(this.textInputLayout_right_arm.getEditText().getText().toString()));
        newMeasure.setLeftArm(Float.parseFloat(this.textInputLayout_left_arm.getEditText().getText().toString()));
        newMeasure.setWaist(Float.parseFloat(this.textInputLayout_waist.getEditText().getText().toString()));
        newMeasure.setHip(Float.parseFloat(this.textInputLayout_hip.getEditText().getText().toString()));
        newMeasure.setRightCalf(Float.parseFloat(this.textInputLayout_right_calf.getEditText().getText().toString()));
        newMeasure.setLeftCalf(Float.parseFloat(this.textInputLayout_left_calf.getEditText().getText().toString()));

        this.viewModelEditMeasure.editMeasure(newMeasure);
        this.viewModelEditMeasure.observerResponseMeasure().observe(this, new Observer<Response>() {
            @Override
            public void onChanged(Response res) {

                if(res.getStatus() == 200) {

                    setResult(RESULT_OK);
                    finish();
                }
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