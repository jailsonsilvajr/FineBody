package com.jjsj.finebodyapp.views.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.database.entitys.Student;
import com.jjsj.finebodyapp.viewmodels.ViewModelStudents;
import com.jjsj.finebodyapp.views.StudentActivity;

import java.io.IOException;
import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentsViewHolder> {

    private static int request_update_student;

    private static List<Student> students;
    private ViewModelStudents viewModelStudents;
    private LifecycleOwner lifecycleOwner;

    public StudentsAdapter(List<Student> students, ViewModelStudents viewModelStudents, LifecycleOwner lifecycleOwner, int request_update_student){

        setStudents(students);
        setViewModelStudents(viewModelStudents);
        setLifecycleOwner(lifecycleOwner);
        setRequest_update_student(request_update_student);
    }

    @NonNull
    @Override
    public StudentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new StudentsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_students_layout_items, parent, false), parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull final StudentsViewHolder holder, final int position) {

        holder.getProgressBar().setVisibility(View.VISIBLE);
        holder.getImageView().setVisibility(View.GONE);
        holder.getTextView().setText(getStudents().get(position).getName());
        try {//get img profile

            getViewModelStudents().getImageProfile(students.get(position).getPathPhoto());
            getViewModelStudents().getLiveDataImageProfile().observe(getLifecycleOwner(), new Observer<byte[]>() {
                @Override
                public void onChanged(byte[] bytes) {

                    if(bytes != null){

                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        holder.getImageView().setImageBitmap(bitmap);
                    }else if(getStudents().get(position).getGenre().equals("Feminino")){

                        holder.getImageView().setImageResource(R.drawable.menina);
                    }else{

                        holder.getImageView().setImageResource(R.drawable.boy);
                    }
                    holder.getImageView().setVisibility(View.VISIBLE);
                    holder.getProgressBar().setVisibility(View.GONE);
                }
            });
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {

        return students != null ? students.size() : 0;
    }

    public static int getRequest_update_student() {
        return request_update_student;
    }

    public static void setRequest_update_student(int request_update_student) {
        StudentsAdapter.request_update_student = request_update_student;
    }

    public static List<Student> getStudents() {
        return students;
    }

    public static void setStudents(List<Student> students) {
        StudentsAdapter.students = students;
    }

    public ViewModelStudents getViewModelStudents() {
        return viewModelStudents;
    }

    public void setViewModelStudents(ViewModelStudents viewModelStudents) {
        this.viewModelStudents = viewModelStudents;
    }

    public LifecycleOwner getLifecycleOwner() {
        return lifecycleOwner;
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }

    public static class StudentsViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;
        public ImageView imageView;
        public ProgressBar progressBar;
        public View view;
        public Context context;

        public StudentsViewHolder(View itemView, Context context){

            super(itemView);

            setTextView(itemView.findViewById(R.id.layout_students_item_textView));
            setImageView(itemView.findViewById(R.id.layout_students_item_imageView));
            setProgressBar(itemView.findViewById(R.id.layout_students_item_progressBar));
            setView(itemView);
            setContext(context);
            getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getContext(), StudentActivity.class);
                    intent.putExtra("student", getStudents().get(getAdapterPosition()));
                    intent.putExtra("studentPosition", getAdapterPosition());
                    ((Activity) getContext()).startActivityForResult(intent, getRequest_update_student());
                }
            });
        }

        public TextView getTextView(){

            return this.textView;
        }

        public void setTextView(TextView textView){

            this.textView = textView;
        }

        public ImageView getImageView() {

            return this.imageView;
        }

        public void setImageView(ImageView imageView) {

            this.imageView = imageView;
        }

        public ProgressBar getProgressBar() {
            return progressBar;
        }

        public void setProgressBar(ProgressBar progressBar) {
            this.progressBar = progressBar;
        }

        public View getView() {

            return view;
        }

        public void setView(View view) {

            this.view = view;
        }

        public Context getContext() {

            return context;
        }

        public void setContext(Context context) {

            this.context = context;
        }
    }
}
