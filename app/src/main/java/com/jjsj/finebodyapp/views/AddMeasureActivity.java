package com.jjsj.finebodyapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.database.entitys.Measure;
import com.jjsj.finebodyapp.database.entitys.Student;
import com.jjsj.finebodyapp.viewmodels.ViewModelAddMeasure;

public class AddMeasureActivity extends AppCompatActivity {

    private final String KEY_NEW_MEASURE = "com.jjsj.finebodyapp.new_measure";

    private Student student;
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

    private ViewModelAddMeasure viewModelAddMeasures;
    private Measure measure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_measure);

        getSupportActionBar().setTitle(getResources().getString(R.string.new_datas));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Show button
        getSupportActionBar().setHomeButtonEnabled(true); //Activate button

        Bundle extras = getIntent().getExtras();
        this.student = (Student) extras.getSerializable("student");

        this.textInputLayout_date = findViewById(R.id.layout_add_measures_textInput_date);
        this.textInputLayout_weight = findViewById(R.id.layout_add_measures_textInput_weight);
        this.textInputLayout_right_arm = findViewById(R.id.layout_add_measures_textInput_right_arm);
        this.textInputLayout_left_arm = findViewById(R.id.layout_add_measures_textInput_left_arm);
        this.textInputLayout_waist = findViewById(R.id.layout_add_measures_textInput_waist);
        this.textInputLayout_hip = findViewById(R.id.layout_add_measures_textInput_hip);
        this.textInputLayout_right_calf = findViewById(R.id.layout_add_measures_textInput_right_calf);
        this.textInputLayout_left_calf = findViewById(R.id.layout_add_measures_textInput_left_calf);
        this.button_save = findViewById(R.id.layout_add_measures_button_save);
        this.button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveMeasure();
            }
        });
        this.progressBar = findViewById(R.id.layout_add_measures_progressBar);

        this.viewModelAddMeasures = new ViewModelProvider(this).get(ViewModelAddMeasure.class);
        this.viewModelAddMeasures.observerIdMeasure().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String idMeasure) {

                if(idMeasure != null){

                    measure.setId(idMeasure);
                    Intent intent = new Intent();
                    intent.putExtra(KEY_NEW_MEASURE, measure);
                    setResult(RESULT_OK, intent);
                    finish();
                }else{

                    button_save.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    new MaterialAlertDialogBuilder(AddMeasureActivity.this)
                            .setTitle(getResources().getString(R.string.TitleAlertAddMeasureFail))
                            .setMessage(getResources().getString(R.string.MessageAlertAddMeasureFail))
                            .show();
                }
            }
        });
    }

    private void saveMeasure(){

        this.button_save.setVisibility(View.GONE);
        this.progressBar.setVisibility(View.VISIBLE);

        this.measure = new Measure(
                null,
                this.student.getId(),
                this.textInputLayout_date.getEditText().getText().toString(),
                Float.parseFloat(this.textInputLayout_weight.getEditText().getText().toString()),
                Float.parseFloat(this.textInputLayout_right_arm.getEditText().getText().toString()),
                Float.parseFloat(this.textInputLayout_left_arm.getEditText().getText().toString()),
                Float.parseFloat(this.textInputLayout_waist.getEditText().getText().toString()),
                Float.parseFloat(this.textInputLayout_hip.getEditText().getText().toString()),
                Float.parseFloat(this.textInputLayout_right_calf.getEditText().getText().toString()),
                Float.parseFloat(this.textInputLayout_left_calf.getEditText().getText().toString())
        );
        this.viewModelAddMeasures.addMeasure(measure);
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