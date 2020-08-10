package com.jjsj.finebodyapp.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.jjsj.finebodyapp.viewmodels.ViewModelAddStudent;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddStudentActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private CircleImageView circleImageView;
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
        setViewModelAddStudent();
        getViewModelAddStudent().observerIdStudent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if(s != null){

                    student.setId(s);
                    getViewModelAddStudent().updateStudent(student);
                }else{

                    new MaterialAlertDialogBuilder(AddStudentActivity.this)
                            .setTitle(getResources().getString(R.string.TitleAlertAddStudentFail))
                            .setMessage(getResources().getString(R.string.MessageAlertAddStudentFail))
                            .show();
                    showView(false, getProgressBar());
                    showView(true, getButtonSave());
                }
            }
        });

        getViewModelAddStudent().observerUpdateStudent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if(aBoolean){

                    getViewModelAddStudent().doUploadPhoto(getCircleImageView(), student.getPathPhoto());
                }
            }
        });

        getViewModelAddStudent().observerUploadPhoto().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if(aBoolean){

                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

        getCircleImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                takePicture();
            }
        });
        getButtonSave().setOnClickListener(new View.OnClickListener() {
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

        showView(false, getButtonSave());
        showView(true, getProgressBar());

        PreferenceLogged preferenceLogged = new PreferenceLogged(this);
        this.student = new Student(
                null,
                getTextInputLayoutName().getEditText().getText().toString(),
                getSpinnerGenre().getSelectedItem().toString(),
                Integer.parseInt(getSpinnerAge().getSelectedItem().toString()),
                preferenceLogged.getPreference(),
                "images/"
        );
        getViewModelAddStudent().addStudent(this.student);
    }

    private void takePicture(){

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){

            Uri photoUri = data.getData();
            if(photoUri != null){

                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                    getCircleImageView().setImageBitmap(bitmap);
                }catch (Exception e){

                    e.printStackTrace();
                }
            }
        }else super.onActivityResult(requestCode, resultCode, data);
    }

    private void setViews(){

        setCircleImageView(findViewById(R.id.layout_add_student_imageView));
        setTextInputLayoutName(findViewById(R.id.layout_add_student_textInput_name));
        setSpinnerGenre(findViewById(R.id.layout_add_student_spinner_genre));
        setSpinnerAge(findViewById(R.id.layout_add_student_spinner_age));
        setButtonSave(findViewById(R.id.layout_add_student_button_save));
        setProgressBar(findViewById(R.id.layout_add_student_progressBar));

        ArrayAdapter<CharSequence> adapterGenre = ArrayAdapter.createFromResource(this, R.array.genres, android.R.layout.simple_spinner_item);
        adapterGenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getSpinnerGenre().setAdapter(adapterGenre);

        List<String> ages = new ArrayList<>();
        for(int i = 1; i <= 100; i++) ages.add(Integer.toString(i));
        ArrayAdapter<String> adapterAge = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ages);
        adapterAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getSpinnerAge().setAdapter(adapterAge);
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ViewModelAddStudent getViewModelAddStudent() {
        return viewModelAddStudent;
    }

    public void setViewModelAddStudent() {
        this.viewModelAddStudent = new ViewModelProvider(this).get(ViewModelAddStudent.class);
    }

    public CircleImageView getCircleImageView() {
        return circleImageView;
    }

    public void setCircleImageView(CircleImageView circleImageView) {
        this.circleImageView = circleImageView;
    }

    public TextInputLayout getTextInputLayoutName() {
        return textInputLayoutName;
    }

    public void setTextInputLayoutName(TextInputLayout textInputLayoutName) {
        this.textInputLayoutName = textInputLayoutName;
    }

    public Spinner getSpinnerGenre() {
        return spinnerGenre;
    }

    public void setSpinnerGenre(Spinner spinnerGenre) {
        this.spinnerGenre = spinnerGenre;
    }

    public Spinner getSpinnerAge() {
        return spinnerAge;
    }

    public void setSpinnerAge(Spinner spinnerAge) {
        this.spinnerAge = spinnerAge;
    }

    public Button getButtonSave() {
        return buttonSave;
    }

    public void setButtonSave(Button buttonSave) {
        this.buttonSave = buttonSave;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }
}