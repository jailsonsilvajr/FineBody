package com.jjsj.finebodyapp.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;
import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.database.sqlite.entitys.Student;

import java.util.ArrayList;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity {

    private Student student;

    private ImageView imageView_profile;
    private TextInputLayout textInputLayout_name;
    private Spinner spinner_genre;
    private Spinner spinner_age;

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().setTitle(R.string.editProfile);

        Bundle extra = getIntent().getExtras();
        if(extra != null) this.student = (Student) extra.getSerializable("student");
        else student = null;

        this.imageView_profile = findViewById(R.id.layout_edit_profile_imageView);
        this.imageView_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                takePicture();
            }
        });

        this.textInputLayout_name = findViewById(R.id.layout_edit_profile_textInput_name);
        this.textInputLayout_name.getEditText().setText(this.student.getName());

        this.spinner_genre = findViewById(R.id.layout_edit_profile_spinner_genre);
        ArrayAdapter<CharSequence> adapterGenre = ArrayAdapter.createFromResource(this, R.array.genres, android.R.layout.simple_spinner_item);
        adapterGenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner_genre.setAdapter(adapterGenre);

        this.spinner_age = findViewById(R.id.layout_edit_profile_spinner_age);
        List<String> ages = new ArrayList<>();
        for(int i = 1; i <= 100; i++) ages.add(Integer.toString(i));
        ArrayAdapter<String> adapterAge = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ages);
        adapterAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner_age.setAdapter(adapterAge);
    }

    private void takePicture(){

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){

            Uri photoUri = data.getData();
            if(photoUri != null){

                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                    this.imageView_profile.setImageBitmap(bitmap);
                }catch (Exception e){

                    e.printStackTrace();
                }
            }
        }
    }
}