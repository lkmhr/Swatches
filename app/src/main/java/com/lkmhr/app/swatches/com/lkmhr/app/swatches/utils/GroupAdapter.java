package com.lkmhr.app.swatches.com.lkmhr.app.swatches.utils;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lkmhr.app.swatches.MainActivity;
import com.lkmhr.app.swatches.R;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ColorViewHolder>{

    List<ColorGroup> colorGroups;
    MainActivity activity;

    public interface OnItemClickListener {
        public void onItemClicked(int position);
    }

    public interface OnItemLongClickListener {
        public boolean onItemLongClicked(int position, ColorGroup colorGroup);
    }

    public GroupAdapter(MainActivity activity, List<ColorGroup> colorGroups){
        this.activity = activity;
        this.colorGroups = colorGroups;
    }

    @Override
    public ColorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new ColorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ColorViewHolder holder, final int position) {
        holder.name.setText(colorGroups.get(position).getName());
        holder.description.setText(colorGroups.get(position).getDescription());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onItemClicked(position);
            }
        });
        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                activity.onItemLongClicked(position, colorGroups.get(position));
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.colorGroups.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ColorViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout card;
        public TextView name;
        public TextView description;

        ColorViewHolder(View itemView) {
            super(itemView);
            this.card = (RelativeLayout) itemView.findViewById(R.id.card);
            this.name = (TextView)itemView.findViewById(R.id.name);
            this.description = (TextView)itemView.findViewById(R.id.desc);
        }
    }

}
