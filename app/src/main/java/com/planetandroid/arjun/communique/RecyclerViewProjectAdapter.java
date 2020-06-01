/**
 * Created by arjun on 06-02-2019.
 */
package com.planetandroid.arjun.communique;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.List;

public class RecyclerViewProjectAdapter extends RecyclerView.Adapter<RecyclerViewProjectAdapter.MyViewHolder> {

    private List<RecyclerViewAdapterClass> dataList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.hours);
            year = (TextView) view.findViewById(R.id.perc);
        }
    }


    public RecyclerViewProjectAdapter(List<RecyclerViewAdapterClass> moviesList) {
        this.dataList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_project, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RecyclerViewAdapterClass movie = dataList.get(position);
        holder.title.setText(movie.getTitle());
        String text = "Hours Completed = " + movie.getHours();
        holder.genre.setText(text);
        text = "Work Completed = " + movie.getPercentage() + " %"  ;
        holder.year.setText(text);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}