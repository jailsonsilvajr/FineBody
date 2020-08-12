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
import com.jjsj.finebodyapp.utils.KeyName;
import com.jjsj.finebodyapp.utils.ResultCode;
import com.jjsj.finebodyapp.viewmodels.ViewModelEditStudent;

import java.util.ArrayList;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity {

    private Student student;

    private TextInputLayout textInputLayoutName;
    private Spinner spinnerGenre;
    private Spinner spinnerAge;
    private Button buttonSave;
    private ProgressBar progressBar;
    private ViewModelEditStudent viewModelEditStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().setTitle(R.string.editProfile);

        this.viewModelEditStudent = new ViewModelProvider(this).get(ViewModelEditStudent.class);
        this.viewModelEditStudent.observerStudentUpdated().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if(aBoolean){

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(KeyName.KEY_NAME_STUDENT, student);
                    setResult(ResultCode.RESULT_CODE_STUDENT_UPDATED, returnIntent);
                    finish();
                }else{

                    new MaterialAlertDialogBuilder(EditProfileActivity.this)
                            .setTitle(getResources().getString(R.string.error))
                            .setMessage(getResources().getString(R.string.MessageAlertEditProfileFail))
                            .show();

                    progressBar.setVisibility(View.GONE);
                    buttonSave.setVisibility(View.VISIBLE);
                }
            }
        });

        Bundle extra = getIntent().getExtras();
        this.student = (Student) extra.getSerializable(KeyName.KEY_NAME_STUDENT);

        setViewsEditProfile();
    }

    private void setViewsEditProfile(){

        this.textInputLayoutName = findViewById(R.id.layout_edit_profile_textInput_name);
        this.textInputLayoutName.getEditText().setText(this.student.getName());

        this.spinnerGenre = findViewById(R.id.layout_edit_profile_spinner_genre);
        ArrayAdapter<CharSequence> adapterGenre = ArrayAdapter.createFromResource(this, R.array.genres, android.R.layout.simple_spinner_item);
        adapterGenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerGenre.setAdapter(adapterGenre);
        if(this.student.getGenre().equals("Masculino")) this.spinnerGenre.setSelection(0);
        else this.spinnerGenre.setSelection(1);

        this.spinnerAge = findViewById(R.id.layout_edit_profile_spinner_age);
        List<String> ages = new ArrayList<>();
        for(int i = 1; i <= 100; i++) ages.add(Integer.toString(i));
        ArrayAdapter<String> adapterAge = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ages);
        adapterAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerAge.setAdapter(adapterAge);
        this.spinnerAge.setSelection(this.student.getAge() - 1);

        this.buttonSave = findViewById(R.id.layout_edit_profile_button_save);
        this.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateStudent();
            }
        });

        this.progressBar = findViewById(R.id.layout_edit_profile_progressBar);
        this.progressBar.setVisibility(View.GONE);
    }

    private void updateStudent(){

        this.progressBar.setVisibility(View.VISIBLE);
        this.buttonSave.setVisibility(View.GONE);

        this.student.setName(this.textInputLayoutName.getEditText().getText().toString());
        this.student.setGenre(this.spinnerGenre.getSelectedItem().toString());
        this.student.setAge(Integer.parseInt(this.spinnerAge.getSelectedItem().toString()));

        this.viewModelEditStudent.updateStudent(this.student);
    }
}