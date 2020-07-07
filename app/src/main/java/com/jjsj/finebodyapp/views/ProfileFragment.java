package com.jjsj.finebodyapp.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.database.sqlite.entitys.Student;
import com.jjsj.finebodyapp.viewmodels.ViewModelProfile;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private Student student;

    private CircleImageView imageView_profile;
    private TextView textView_name;
    private TextView textView_genre;
    private TextView textView_age;

    private ViewModelProfile viewModelProfile;

    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.viewModelProfile = new ViewModelProvider(this).get(ViewModelProfile.class);

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        this.imageView_profile = view.findViewById(R.id.layout_profile_imageView_profile);
        this.textView_name = view.findViewById(R.id.layout_profile_textView_name);
        this.textView_genre = view.findViewById(R.id.layout_profile_textView_genre);
        this.textView_age = view.findViewById(R.id.layout_profile_textView_age);

        Bundle extra = getActivity().getIntent().getExtras();
        if(extra != null) {

            this.student = (Student) extra.getSerializable("student");
            this.textView_name.setText(this.student.getName());
            this.textView_genre.setText(this.student.getGenre());
            this.textView_age.setText(Integer.toString(this.student.getAge()));

            try {
                this.viewModelProfile.downloadImgProfile(this.student.getPath_photo());
                this.viewModelProfile.observerImgProfileBytes().observe(getViewLifecycleOwner(), new Observer<byte[]>() {
                    @Override
                    public void onChanged(byte[] bytes) {

                        if(bytes != null){

                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            imageView_profile.setImageBitmap(bitmap);
                        }else if(student.getGenre().equalsIgnoreCase("Masculino")){

                            imageView_profile.setImageDrawable(getResources().getDrawable(R.drawable.boy));
                        }else{

                            imageView_profile.setImageDrawable(getResources().getDrawable(R.drawable.menina));
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{

            this.student = null;
        }

        return view;
    }
}