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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jjsj.finebodyapp.R;

import com.jjsj.finebodyapp.database.entitys.Student;
import com.jjsj.finebodyapp.database.firebase.Response;
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

        setViewModelProfile();

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        setImageViewProfile(view.findViewById(R.id.layout_profile_imageView_profile));
        setTextViewName(view.findViewById(R.id.layout_profile_textView_name));
        setTextViewGenre(view.findViewById(R.id.layout_profile_textView_genre));
        setTextViewAge(view.findViewById(R.id.layout_profile_textView_age));

        setButtonEdit(view.findViewById(R.id.layout_profile_button_edit));
        getButtonEdit().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openEditProfile(getStudent());
            }
        });

        Bundle extra = getActivity().getIntent().getExtras();
        setStudent((Student) extra.getSerializable("student"));

        setViewsProfileStudent(getStudent());

        return view;
    }

    private void openEditProfile(Student student){

        Intent intent = new Intent(getActivity(), EditProfileActivity.class);
        intent.putExtra("student", getStudent());
        startActivityForResult(intent, getRequestUpdateActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == getRequestUpdateActivity() && resultCode == getActivity().RESULT_OK){

            setStudent((Student) data.getSerializableExtra("student"));
            setViewsProfileStudent(getStudent());

            Bundle extra = getActivity().getIntent().getExtras();
            int position = extra.getInt("studentPosition");

            Intent returnIntent = new Intent();
            returnIntent.putExtra("student", getStudent());
            returnIntent.putExtra("position", position);
            getActivity().setResult(Activity.RESULT_OK, returnIntent);
        }else{

            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setViewsProfileStudent(Student student){

        getTextViewName().setText(student.getName());
        getTextViewGenre().setText(student.getGenre());
        getTextViewAge().setText(Integer.toString(student.getAge()));
        downloadImage(student.getPathPhoto());
    }

    private void downloadImage(String path){

        try {

            getViewModelProfile().downloadImgProfile(path);
            getViewModelProfile().observerImgProfileBytes().observe(getViewLifecycleOwner(), new Observer<byte[]>() {
                @Override
                public void onChanged(byte[] bytes) {

                    if(bytes != null){

                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        getImageViewProfile().setImageBitmap(bitmap);
                    }else if(getStudent().getGenre().equalsIgnoreCase("Masculino")){

                        getImageViewProfile().setImageDrawable(getResources().getDrawable(R.drawable.boy));
                    }else{

                        getImageViewProfile().setImageDrawable(getResources().getDrawable(R.drawable.menina));
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ViewModelProfile getViewModelProfile() {

        return this.viewModelProfile;
    }

    public void setViewModelProfile() {

        this.viewModelProfile = new ViewModelProvider(this).get(ViewModelProfile.class);
    }

    public CircleImageView getImageViewProfile() {

        return this.imageViewProfile;
    }

    public void setImageViewProfile(CircleImageView imageViewProfile) {

        this.imageViewProfile = imageViewProfile;
    }

    public TextView getTextViewName() {

        return this.textViewName;
    }

    public void setTextViewName(TextView textViewName) {

        this.textViewName = textViewName;
    }

    public TextView getTextViewGenre() {

        return this.textViewGenre;
    }

    public void setTextViewGenre(TextView textViewGenre) {

        this.textViewGenre = textViewGenre;
    }

    public TextView getTextViewAge() {

        return this.textViewAge;
    }

    public void setTextViewAge(TextView textViewAge) {

        this.textViewAge = textViewAge;
    }

    public Button getButtonEdit() {

        return this.buttonEdit;
    }

    public void setButtonEdit(Button buttonEdit) {

        this.buttonEdit = buttonEdit;
    }

    public Student getStudent() {

        return this.student;
    }

    public void setStudent(Student student) {

        this.student = student;
    }

    public static int getRequestUpdateActivity() {

        return REQUEST_UPDATE_ACTIVITY;
    }
}