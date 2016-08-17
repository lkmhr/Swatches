package com.lkmhr.app.swatches.com.lkmhr.app.swatches.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lkmhr.app.swatches.ColorActivity;
import com.lkmhr.app.swatches.R;

import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorViewHolder>{

    List<Color> colors;
    ColorActivity activity;

    public interface OnItemClickListener {
        public void onItemClicked(int position);
    }

    public interface OnItemLongClickListener {
        public boolean onItemLongClicked(int position, Color color);
    }

    public ColorAdapter(ColorActivity activity, List<Color> colors){
        this.colors = colors;
        this.activity = activity;
    }

    @Override
    public ColorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_color_layout, parent, false);
        return new ColorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ColorViewHolder holder, final int position) {
//        holder.colorName.setText(colors.get(position).getName());
        holder.colorCode.setText(colors.get(position).getHexCode());
        if(!colors.get(position).getHexCode().equals(""))
            holder.colorView.setBackgroundColor(android.graphics.Color.parseColor(colors.get(position).getHexCode()));
        else
            holder.colorView.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onItemClicked(position);
            }
        });
        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                activity.onItemLongClicked(position, colors.get(position));
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ColorViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout card;
        TextView colorView;
//        TextView colorName;
        TextView colorCode;

        ColorViewHolder(View itemView) {
            super(itemView);
            card = (RelativeLayout)itemView.findViewById(R.id.card);
//            colorName = (TextView)itemView.findViewById(R.id.name);
            colorCode = (TextView)itemView.findViewById(R.id.code);
            colorView = (TextView) itemView.findViewById(R.id.color);
        }
    }
}
