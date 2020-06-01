/**
 * Created by arjun on 06-02-2019.
 */
package com.planetandroid.arjun.communique;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.List;

import static java.lang.Math.abs;

public class RecyclerViewReportAdapter extends RecyclerView.Adapter<RecyclerViewReportAdapter.MyViewHolder> {

    private List<RecyclerViewAdapterClass> dataList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.hours);
            year = (TextView) view.findViewById(R.id.perc);
            image = (ImageView) view.findViewById(R.id.flag);
        }
    }


    public RecyclerViewReportAdapter(List<RecyclerViewAdapterClass> moviesList) {
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

        Float diff = abs(Float.valueOf(movie.getHours()) - Float.valueOf(movie.getPercentage()));
        if(diff >= 10)
            holder.image.setImageResource(R.drawable.flag_red);
        else if( diff >= 5)
            holder.image.setImageResource(R.drawable.flag_orange);
        else
            holder.image.setImageResource(R.drawable.flag_green);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}