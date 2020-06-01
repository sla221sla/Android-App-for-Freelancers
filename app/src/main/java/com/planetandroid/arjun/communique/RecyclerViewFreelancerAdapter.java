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


public class RecyclerViewFreelancerAdapter extends RecyclerView.Adapter<RecyclerViewFreelancerAdapter.MyViewHolder> {

    private List<RecyclerViewAdapterClass> dataList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, hours;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            hours = (TextView) view.findViewById(R.id.hours);


        }
    }


    public RecyclerViewFreelancerAdapter(List<RecyclerViewAdapterClass> moviesList) {
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
        String text = movie.getHours() + " - " + movie.getPercentage();
        holder.hours.setText(text);


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}