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
import com.jjsj.finebodyapp.utils.KeyName;
import com.jjsj.finebodyapp.utils.ResultCode;
import com.jjsj.finebodyapp.viewmodels.ViewModelEditMeasure;

public class EditMeasureActivity extends AppCompatActivity {

    private final String KEY_NEW_MEASURE = "com.jjsj.finebodyapp.new_measure";

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
        this.measure = (Measure) extras.getSerializable(KeyName.KEY_NAME_MEASURE);

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

        this.viewModelEditMeasure = new ViewModelProvider(this).get(ViewModelEditMeasure.class);
        this.viewModelEditMeasure.observerUpdateMeasure().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if(aBoolean) {

                    Intent intent = new Intent();
                    intent.putExtra(KeyName.KEY_NAME_MEASURE, measure);
                    setResult(ResultCode.RESULT_CODE_MEASURE_UPDATED, intent);
                    finish();
                }
                else{

                    progressBar.setVisibility(View.GONE);
                    button_save.setVisibility(View.VISIBLE);
                    new MaterialAlertDialogBuilder(EditMeasureActivity.this)
                            .setTitle(getResources().getString(R.string.TitleAlertEditMeasureFail))
                            .setMessage(getResources().getString(R.string.MessageAlertEditMeasureFail))
                            .show();
                }
            }
        });
    }

    private void saveMeasure(){

        this.button_save.setVisibility(View.GONE);
        this.progressBar.setVisibility(View.VISIBLE);

        Measure measure = new Measure(
                this.measure.getId(),
                this.measure.getIdStudent(),
                this.textInputLayout_date.getEditText().getText().toString(),
                Float.parseFloat(this.textInputLayout_weight.getEditText().getText().toString()),
                Float.parseFloat(this.textInputLayout_right_arm.getEditText().getText().toString()),
                Float.parseFloat(this.textInputLayout_left_arm.getEditText().getText().toString()),
                Float.parseFloat(this.textInputLayout_waist.getEditText().getText().toString()),
                Float.parseFloat(this.textInputLayout_hip.getEditText().getText().toString()),
                Float.parseFloat(this.textInputLayout_right_calf.getEditText().getText().toString()),
                Float.parseFloat(this.textInputLayout_left_calf.getEditText().getText().toString())
        );
        this.viewModelEditMeasure.editMeasure(measure);
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