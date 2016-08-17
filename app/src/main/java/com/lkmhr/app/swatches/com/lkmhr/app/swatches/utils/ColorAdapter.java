package com.lkmhr.app.swatches.com.lkmhr.app.swatches.utils;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lkmhr.app.swatches.ColorActivity;
import com.lkmhr.app.swatches.R;

import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorViewHolder>{

    List<Color> colors;
    ColorActivity activity;

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
    public void onBindViewHolder(ColorViewHolder holder, int position) {
        holder.colorName.setText(colors.get(position).getName());
        holder.colorCode.setText(colors.get(position).getHexCode());
        if(!colors.get(position).getHexCode().equals(""))
            holder.colorView.setBackgroundColor(android.graphics.Color.parseColor(colors.get(position).getHexCode()));
        else
            holder.colorView.setBackgroundColor(android.graphics.Color.parseColor("#ffffff"));
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
        CardView cv;
        TextView colorView;
        TextView colorName;
        TextView colorCode;

        ColorViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            colorName = (TextView)itemView.findViewById(R.id.name);
            colorCode = (TextView)itemView.findViewById(R.id.code);
            colorView = (TextView) itemView.findViewById(R.id.color);
        }
    }
}
