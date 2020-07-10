package com.jjsj.finebodyapp.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.database.entitys.Student;
import com.jjsj.finebodyapp.database.firebase.Response;
import com.jjsj.finebodyapp.viewmodels.ViewModelEditStudent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity {

    private Student student;

    private ImageView imageViewProfile;
    private TextInputLayout textInputLayoutName;
    private Spinner spinnerGenre;
    private Spinner spinnerAge;
    private Button buttonSave;
    private ProgressBar progressBar;
    private ViewModelEditStudent viewModelEditStudent;

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().setTitle(R.string.editProfile);

        setViewModelEditStudent();

        Bundle extra = getIntent().getExtras();
        setStudent((Student) extra.getSerializable("student"));

        setViewsEditProfile();
    }

    private void setViewsEditProfile(){

        setImageViewProfile(findViewById(R.id.layout_edit_profile_imageView));
        getImageViewProfile().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                takePicture();
            }
        });
        try {

            this.viewModelEditStudent.downloadImage(student.getPathPhoto());
            this.viewModelEditStudent.observerDownloadImage().observe(this, new Observer<byte[]>() {
                @Override
                public void onChanged(byte[] bytes) {

                    if(bytes != null){

                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        getImageViewProfile().setImageBitmap(bitmap);
                    }else if(getStudent().getGenre().equals(getResources().getString(R.string.female))){

                        getImageViewProfile().setImageResource(R.drawable.menina);
                    }else{

                        getImageViewProfile().setImageResource(R.drawable.boy);
                    }
                }
            });
        } catch (IOException e) {

            e.printStackTrace();
        }

        setTextInputLayoutName(findViewById(R.id.layout_edit_profile_textInput_name));
        getTextInputLayoutName().getEditText().setText(getStudent().getName());

        setSpinnerGenre(findViewById(R.id.layout_edit_profile_spinner_genre));
        ArrayAdapter<CharSequence> adapterGenre = ArrayAdapter.createFromResource(this, R.array.genres, android.R.layout.simple_spinner_item);
        adapterGenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getSpinnerGenre().setAdapter(adapterGenre);
        if(getStudent().getGenre().equals("Masculino")) getSpinnerGenre().setSelection(0);
        else getSpinnerGenre().setSelection(1);

        setSpinnerAge(findViewById(R.id.layout_edit_profile_spinner_age));
        List<String> ages = new ArrayList<>();
        for(int i = 1; i <= 100; i++) ages.add(Integer.toString(i));
        ArrayAdapter<String> adapterAge = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ages);
        adapterAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getSpinnerAge().setAdapter(adapterAge);
        getSpinnerAge().setSelection(getStudent().getAge() - 1);

        setButtonSave(findViewById(R.id.layout_edit_profile_button_save));
        getButtonSave().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadPhoto();
            }
        });

        setProgressBar(findViewById(R.id.layout_edit_profile_progressBar));
        getProgressBar().setVisibility(View.GONE);
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
                    getImageViewProfile().setImageBitmap(bitmap);
                }catch (Exception e){

                    e.printStackTrace();
                }
            }
        }else super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadPhoto(){

        getProgressBar().setVisibility(View.VISIBLE);
        getButtonSave().setVisibility(View.GONE);

        getViewModelEditStudent().doUpload(getImageViewProfile(), getStudent().getPathPhoto());
        getViewModelEditStudent().observerUploadImage().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if(aBoolean){

                    updateStudent();
                }else{

                    new MaterialAlertDialogBuilder(EditProfileActivity.this)
                            .setTitle(getResources().getString(R.string.error))
                            .setMessage(getResources().getString(R.string.MessageAlertEditProfileFail))
                            .show();

                    getProgressBar().setVisibility(View.GONE);
                    getButtonSave().setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void updateStudent(){

        Student newStudent = new Student();
        newStudent.setId(getStudent().getId());
        newStudent.setIdCoach(getStudent().getIdCoach());
        newStudent.setName(getTextInputLayoutName().getEditText().getText().toString());
        newStudent.setGenre(getSpinnerGenre().getSelectedItem().toString());
        newStudent.setAge(Integer.parseInt(getSpinnerAge().getSelectedItem().toString()));
        newStudent.setPathPhoto(getStudent().getPathPhoto());

        getViewModelEditStudent().updateStudent(newStudent);
        getViewModelEditStudent().observerResponseUpdateStudent().observe(this, new Observer<Response>() {
            @Override
            public void onChanged(Response response) {

                if(response.getStatus() == 200){

                    setStudent(newStudent);

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("student", getStudent());
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }else{

                    new MaterialAlertDialogBuilder(EditProfileActivity.this)
                            .setTitle(getResources().getString(R.string.error))
                            .setMessage(getResources().getString(R.string.MessageAlertEditProfileFail))
                            .show();

                    getProgressBar().setVisibility(View.GONE);
                    getButtonSave().setVisibility(View.VISIBLE);
                }
            }
        });

    }

    public Student getStudent() {

        return this.student;
    }

    public void setStudent(Student student) {

        this.student = student;
    }

    public ImageView getImageViewProfile() {

        return this.imageViewProfile;
    }

    public void setImageViewProfile(ImageView imageViewProfile) {

        this.imageViewProfile = imageViewProfile;
    }

    public TextInputLayout getTextInputLayoutName() {

        return this.textInputLayoutName;
    }

    public void setTextInputLayoutName(TextInputLayout textInputLayoutName) {

        this.textInputLayoutName = textInputLayoutName;
    }

    public Spinner getSpinnerGenre() {

        return this.spinnerGenre;
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

        return this.buttonSave;
    }

    public void setButtonSave(Button buttonSave) {

        this.buttonSave = buttonSave;
    }

    public ProgressBar getProgressBar() {

        return this.progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {

        this.progressBar = progressBar;
    }

    public ViewModelEditStudent getViewModelEditStudent() {

        return this.viewModelEditStudent;
    }

    public void setViewModelEditStudent() {

        this.viewModelEditStudent = new ViewModelProvider(this).get(ViewModelEditStudent.class);
    }

    public static int getRequestImageCapture() {

        return REQUEST_IMAGE_CAPTURE;
    }
}