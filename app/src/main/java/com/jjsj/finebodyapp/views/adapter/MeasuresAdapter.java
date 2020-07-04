package com.jjsj.finebodyapp.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.database.sqlite.entitys.Measure;
import com.jjsj.finebodyapp.repository.Repository;

import java.util.List;

public class MeasuresAdapter extends RecyclerView.Adapter<MeasuresAdapter.MeasuresViewHolder> {

    private List<Measure> measures;

    public MeasuresAdapter(List<Measure> measures){

        this.measures = measures;
    }

    public static class MeasuresViewHolder extends RecyclerView.ViewHolder{

        public TextView date;
        public View view;
        public Context context;
        public ImageView imageView;
        public ProgressBar progressBar;

        public MeasuresViewHolder(@NonNull View itemView, Context context) {

            super(itemView);
            this.context = context;
            this.date = itemView.findViewById(R.id.layout_measures_item_textView);
            this.imageView = itemView.findViewById(R.id.layout_measures_item_imageView);
            this.progressBar = itemView.findViewById(R.id.layout_measures_item_progressBar);
            this.view = itemView;
        }
    }

    @NonNull
    @Override
    public MeasuresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MeasuresViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_measures_item, parent, false), parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull MeasuresViewHolder holder, int position) {

        holder.progressBar.setVisibility(View.GONE);
        holder.date.setText(measures.get(position).getDate());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(holder.context, "Click: " + position, Toast.LENGTH_LONG).show();
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.imageView.setVisibility(View.GONE);
                holder.progressBar.setVisibility(View.VISIBLE);
                boolean result = Repository.getInstance(v.getContext()).deleteMeasureRepository(measures.get(position));
                if(result){

                    holder.progressBar.setVisibility(View.GONE);
                    measures.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, measures.size());
                }else{

                    holder.imageView.setVisibility(View.VISIBLE);
                    holder.progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return measures != null ? measures.size() : 0;
    }
}
