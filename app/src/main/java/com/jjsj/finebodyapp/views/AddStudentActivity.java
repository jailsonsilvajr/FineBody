package com.jjsj.finebodyapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.database.entitys.Student;
import com.jjsj.finebodyapp.preferences.PreferenceLogged;
import com.jjsj.finebodyapp.utils.KeyName;
import com.jjsj.finebodyapp.utils.ResultCode;
import com.jjsj.finebodyapp.viewmodels.ViewModelAddStudent;

import java.util.ArrayList;
import java.util.List;

public class AddStudentActivity extends AppCompatActivity {

    private TextInputLayout textInputLayoutName;
    private Spinner spinnerGenre;
    private Spinner spinnerAge;
    private Button buttonSave;
    private ProgressBar progressBar;

    private ViewModelAddStudent viewModelAddStudent;

    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        setViews();

        this.viewModelAddStudent = new ViewModelProvider(this).get(ViewModelAddStudent.class);
        this.viewModelAddStudent.observerIdStudent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if(s != null){

                    student.setId(s);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(KeyName.KEY_NAME_STUDENT, student);
                    setResult(ResultCode.RESULT_CODE_STUDENT_ADD, returnIntent);
                    finish();
                }else{

                    new MaterialAlertDialogBuilder(AddStudentActivity.this)
                            .setTitle(getResources().getString(R.string.TitleAlertAddStudentFail))
                            .setMessage(getResources().getString(R.string.MessageAlertAddStudentFail))
                            .show();
                    showView(false, progressBar);
                    showView(true, buttonSave);
                }

            }
        });
    }

    private void setViews(){

        this.textInputLayoutName = findViewById(R.id.layout_add_student_textInput_name);
        this.spinnerGenre = findViewById(R.id.layout_add_student_spinner_genre);
        this.spinnerAge = findViewById(R.id.layout_add_student_spinner_age);
        this.buttonSave = findViewById(R.id.layout_add_student_button_save);
        this.progressBar = findViewById(R.id.layout_add_student_progressBar);

        ArrayAdapter<CharSequence> adapterGenre = ArrayAdapter.createFromResource(this, R.array.genres, android.R.layout.simple_spinner_item);
        adapterGenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerGenre.setAdapter(adapterGenre);

        List<String> ages = new ArrayList<>();
        for(int i = 1; i <= 100; i++) ages.add(Integer.toString(i));
        ArrayAdapter<String> adapterAge = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ages);
        adapterAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerAge.setAdapter(adapterAge);

        this.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addStudent();
            }
        });
    }

    private void showView(boolean show, View view){

        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void addStudent(){

        showView(false, this.buttonSave);
        showView(true, this.progressBar);

        PreferenceLogged preferenceLogged = new PreferenceLogged(this);
        this.student = new Student(
                null,
                this.textInputLayoutName.getEditText().getText().toString(),
                this.spinnerGenre.getSelectedItem().toString(),
                Integer.parseInt(this.spinnerAge.getSelectedItem().toString()),
                preferenceLogged.getPreference()
        );
        this.viewModelAddStudent.addStudent(this.student);
    }
}