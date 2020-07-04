package com.jjsj.finebodyapp.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.database.sqlite.entitys.Student;
import com.jjsj.finebodyapp.views.StudentActivity;

import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentsViewHolder> {

    private List<Student> students;

    public StudentsAdapter(List<Student> students){

        this.students = students;
    }

    public static class StudentsViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;
        public ImageView imageView;
        public View view;
        public Context context;

        public StudentsViewHolder(View itemView, Context context){

            super(itemView);
            this.textView = itemView.findViewById(R.id.layout_students_item_textView);
            this.imageView = itemView.findViewById(R.id.layout_students_item_imageView);
            this.view = itemView;
            this.context = context;
        }
    }

    @NonNull
    @Override
    public StudentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new StudentsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_students_layout_items, parent, false), parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull final StudentsViewHolder holder, final int position) {

        holder.textView.setText(students.get(position).getName());
        if(students.get(position).getGenre().equals("Feminino")){

            holder.imageView.setImageResource(R.drawable.menina);
        }else{

            holder.imageView.setImageResource(R.drawable.boy);
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(holder.context, StudentActivity.class);
                intent.putExtra("student", students.get(position));
                holder.context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return students != null ? students.size() : 0;
    }
}
