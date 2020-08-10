package com.jjsj.finebodyapp.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.database.entitys.Student;
import com.jjsj.finebodyapp.viewmodels.ViewModelProfile;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    public static final int REQUEST_UPDATE_ACTIVITY = 1;

    private Student student;

    private CircleImageView imageViewProfile;
    private TextView textViewName;
    private TextView textViewGenre;
    private TextView textViewAge;
    private Button buttonEdit;

    private ViewModelProfile viewModelProfile;

    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.viewModelProfile = new ViewModelProvider(this).get(ViewModelProfile.class);

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        this.imageViewProfile = view.findViewById(R.id.layout_profile_imageView_profile);
        this.textViewName = view.findViewById(R.id.layout_profile_textView_name);
        this.textViewGenre = view.findViewById(R.id.layout_profile_textView_genre);
        this.textViewAge = view.findViewById(R.id.layout_profile_textView_age);
        this.buttonEdit = view.findViewById(R.id.layout_profile_button_edit);
        this.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openEditProfile(student);
            }
        });

        Bundle extra = getActivity().getIntent().getExtras();
        this.student = (Student) extra.getSerializable("student");
        setViewsProfileStudent(this.student);

        return view;
    }

    private void openEditProfile(Student student){

        Intent intent = new Intent(getActivity(), EditProfileActivity.class);
        intent.putExtra("student", student);
        startActivityForResult(intent, REQUEST_UPDATE_ACTIVITY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == REQUEST_UPDATE_ACTIVITY && resultCode == getActivity().RESULT_OK){

            this.student = (Student) data.getSerializableExtra("student");
            setViewsProfileStudent(this.student);
            getActivity().setResult(Activity.RESULT_OK);
        }else{

            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setViewsProfileStudent(Student student){

        this.textViewName.setText(student.getName());
        this.textViewGenre.setText(student.getGenre());
        this.textViewAge.setText(Integer.toString(student.getAge()));
        downloadImage(student.getPathPhoto());
    }

    private void downloadImage(String path){

        try {

            this.viewModelProfile.observerImgProfileBytes().observe(getViewLifecycleOwner(), new Observer<byte[]>() {
                @Override
                public void onChanged(byte[] bytes) {

                    if(bytes != null){

                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        imageViewProfile.setImageBitmap(bitmap);
                    }else if(student.getGenre().equalsIgnoreCase("Masculino")){

                        imageViewProfile.setImageDrawable(getResources().getDrawable(R.drawable.boy));
                    }else{

                        imageViewProfile.setImageDrawable(getResources().getDrawable(R.drawable.menina));
                    }
                }
            });
            this.viewModelProfile.downloadImgProfile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}