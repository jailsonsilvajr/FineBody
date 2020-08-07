package com.jjsj.finebodyapp.views.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jjsj.finebodyapp.R;

import com.jjsj.finebodyapp.database.entitys.Measure;

import java.util.List;

public class MeasuresAdapter extends RecyclerView.Adapter<MeasuresAdapter.MeasuresViewHolder> {

    private List<Measure> measures;

    public MeasuresAdapter(List<Measure> measures){

        this.measures = measures;
    }

    public static class MeasuresViewHolder extends RecyclerView.ViewHolder{

        public TextView date;

        public MeasuresViewHolder(@NonNull View itemView) {

            super(itemView);
            this.date = itemView.findViewById(R.id.layout_measures_item_textView);
        }
    }

    @NonNull
    @Override
    public MeasuresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MeasuresViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_measures_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MeasuresViewHolder holder, int position) {

        holder.date.setText(measures.get(position).getDate());
    }

    @Override
    public int getItemCount() {

        return measures != null ? measures.size() : 0;
    }
}
