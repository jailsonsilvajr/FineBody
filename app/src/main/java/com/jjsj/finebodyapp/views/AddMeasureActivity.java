package com.jjsj.finebodyapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.database.sqlite.entitys.Measure;
import com.jjsj.finebodyapp.database.sqlite.entitys.Student;
import com.jjsj.finebodyapp.viewmodels.ViewModelAddMeasure;

public class AddMeasureActivity extends AppCompatActivity {

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

    private ViewModelAddMeasure viewModelAddMeasures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_measure);

        getSupportActionBar().setTitle(getResources().getString(R.string.new_datas));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Show button
        getSupportActionBar().setHomeButtonEnabled(true); //Activate button

        Bundle extras = getIntent().getExtras();
        if(extras != null) this.student = (Student) extras.getSerializable("student");
        else this.student = null;

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

        this.viewModelAddMeasures = new ViewModelProvider(this).get(ViewModelAddMeasure.class);


    }

    private void saveMeasure(){

        Measure measure = new Measure();
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

        this.viewModelAddMeasures.addMeasure(measure);
        this.viewModelAddMeasures.observeMeasure().observe(this, new Observer<Measure>() {
            @Override
            public void onChanged(Measure measure) {


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