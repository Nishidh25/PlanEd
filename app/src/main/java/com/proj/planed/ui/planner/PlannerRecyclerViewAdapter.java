package com.proj.planed.ui.planner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proj.planed.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class PlannerRecyclerViewAdapter extends RecyclerView.Adapter<PlannerViewHolder> {
    private List<Planner> planner;
    private OnTogglePlannerListener listener;



    public PlannerRecyclerViewAdapter(OnTogglePlannerListener listener) {
        this.planner = new ArrayList<Planner>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, parent, false);
        return new PlannerViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PlannerViewHolder holder, int position) {
        Planner plan = planner.get(position);
        try {
            holder.bind(plan);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return planner.size();
    }

    public void getPlans0() {
        this.planner.removeAll(this.planner);
        notifyDataSetChanged();
    }

    public void setPlans(List<Planner> planner) {
        this.planner = planner;
        notifyDataSetChanged();
    }

    @Override
    public void onViewRecycled(@NonNull PlannerViewHolder holder) {
        super.onViewRecycled(holder);
        holder.alarmStarted.setOnCheckedChangeListener(null);
    }


    public Planner getWordAtPosition (int position) {
        return planner.get(position);
    }


}

