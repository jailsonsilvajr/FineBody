package com.jjsj.finebodyapp.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.database.entitys.Student;
import com.jjsj.finebodyapp.utils.KeyName;
import com.jjsj.finebodyapp.utils.RequestCode;
import com.jjsj.finebodyapp.utils.ResultCode;
import com.jjsj.finebodyapp.viewmodels.ViewModelProfile;

public class ProfileFragment extends Fragment {

    private Student student;

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
        this.textViewName = view.findViewById(R.id.layout_profile_textView_name);
        this.textViewGenre = view.findViewById(R.id.layout_profile_textView_genre);
        this.textViewAge = view.findViewById(R.id.layout_profile_textView_age);
        this.buttonEdit = view.findViewById(R.id.layout_profile_button_edit);
        this.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openEditProfile();
            }
        });

        Bundle extra = getActivity().getIntent().getExtras();
        this.student = (Student) extra.getSerializable(KeyName.KEY_NAME_STUDENT);

        setViewsProfileStudent(this.student);

        return view;
    }

    private void openEditProfile(){

        Intent intent = new Intent(getActivity(), EditProfileActivity.class);
        intent.putExtra(KeyName.KEY_NAME_STUDENT, this.student);
        startActivityForResult(intent, RequestCode.REQUEST_CODE_EDIT_STUDENT);
    }

    private void setViewsProfileStudent(Student student){

        this.textViewName.setText(student.getName());
        this.textViewGenre.setText(student.getGenre());
        this.textViewAge.setText(Integer.toString(student.getAge()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == RequestCode.REQUEST_CODE_EDIT_STUDENT && resultCode == ResultCode.RESULT_CODE_STUDENT_UPDATED){

            this.student = (Student) data.getSerializableExtra(KeyName.KEY_NAME_STUDENT);
            setViewsProfileStudent(this.student);

            Intent returnIntent = new Intent();
            returnIntent.putExtra(KeyName.KEY_NAME_STUDENT, this.student);
            getActivity().setResult(ResultCode.RESULT_CODE_STUDENT_UPDATED, returnIntent);
        }else{

            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}