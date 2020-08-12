package com.jjsj.finebodyapp.views.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.database.entitys.Student;

import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentsViewHolder> {

    private List<Student> students;

    public StudentsAdapter(List<Student> students){

        this.students = students;
    }

    @NonNull
    @Override
    public StudentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new StudentsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_students_layout_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final StudentsViewHolder holder, final int position) {

        holder.textView.setText(this.students.get(position).getName());
    }

    @Override
    public int getItemCount() {

        return this.students != null ? students.size() : 0;
    }

    public static class StudentsViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;

        public StudentsViewHolder(View itemView){

            super(itemView);

            this.textView = itemView.findViewById(R.id.layout_students_item_textView);
        }
    }
}
