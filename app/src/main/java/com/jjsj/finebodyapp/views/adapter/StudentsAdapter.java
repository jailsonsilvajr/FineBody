package com.jjsj.finebodyapp.views.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.database.sqlite.entitys.Student;

import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentsViewHolder> {

    private List<Student> students;

    public StudentsAdapter(List<Student> students){

        this.students = students;
    }

    public static class StudentsViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;
        public ImageView imageView;

        public StudentsViewHolder(View itemView){

            super(itemView);
            this.textView = itemView.findViewById(R.id.layout_students_item_textView);
            this.imageView = itemView.findViewById(R.id.layout_students_item_imageView);
        }
    }

    @NonNull
    @Override
    public StudentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new StudentsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_students_layout_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StudentsViewHolder holder, int position) {

        holder.textView.setText(students.get(position).getName());
        if(students.get(position).getGenre().equals("Feminino")){

            holder.imageView.setImageResource(R.drawable.menina);
        }else{

            holder.imageView.setImageResource(R.drawable.boy);
        }
    }

    @Override
    public int getItemCount() {
        return students != null ? students.size() : 0;
    }
}
