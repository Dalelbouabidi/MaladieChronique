package com.example.health.ui.calendar;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.example.health.R;

import java.time.LocalDate;
import java.util.ArrayList;

class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    private final ArrayList<Pair<LocalDate, Integer>> days;
    private final OnItemListener onItemListener;

    public CalendarAdapter(ArrayList<Pair<LocalDate, Integer>> days, OnItemListener onItemListener) {
        this.days = days;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        LocalDate date = days.get(position).first;
        int count = days.get(position).second;
        if (date == null) {
            holder.dayOfMonth.setText("");
            holder.dayCount.setVisibility(View.INVISIBLE);
            holder.parentView.setBackgroundColor(Color.WHITE);
        } else {
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));

            if (count >= 1) {
                holder.dayCount.setText(String.valueOf(count));
                holder.dayCount.setVisibility(View.VISIBLE);
            } else {
                holder.dayCount.setVisibility(View.INVISIBLE);
            }

            if (date.equals(CalendarUtils.choisirDate)) {
                holder.parentView.setBackgroundColor(Color.LTGRAY);
            } else {
                holder.parentView.setBackgroundColor(Color.WHITE);
            }
        }

        holder.itemView.setOnClickListener(view -> onItemListener.onItemClick(position, days.get(position).first));

    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public interface OnItemListener {
        void onItemClick(int position, LocalDate date);
    }

    public static class CalendarViewHolder extends RecyclerView.ViewHolder  {
        public final View parentView;
        public final TextView dayOfMonth;
        public final TextView dayCount;

        public CalendarViewHolder(View itemView) {
            super(itemView);
            parentView = itemView.findViewById(R.id.parentView);
            dayOfMonth = itemView.findViewById(R.id.cellDayText);
            dayCount = itemView.findViewById(R.id.cellDayCount);
        }
    }
}