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

public class RecyclerViewShowAdapter extends RecyclerView.Adapter<RecyclerViewShowAdapter.MyViewHolder> {

    private List<RecyclerViewShowClass> dataList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, reports;

        public MyViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.date);
            reports = (TextView) view.findViewById(R.id.report);
        }
    }


    public RecyclerViewShowAdapter(List<RecyclerViewShowClass> moviesList) {
        this.dataList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_show, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RecyclerViewShowClass data = dataList.get(position);
        holder.date.setText(data.getDate());
        holder.reports.setText(data.getReport());


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}