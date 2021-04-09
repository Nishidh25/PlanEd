package com.proj.planed.ui.faq;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proj.planed.R;
import com.proj.planed.ui.alarms.Alarm;
import com.proj.planed.ui.alarms.AlarmViewHolder;
import com.proj.planed.ui.alarms.OnToggleAlarmListener;

import java.util.ArrayList;
import java.util.List;

public class FaqRecyclerViewAdapter extends RecyclerView.Adapter<FaqViewHolder> {
    private List<Faq> faq;


    public FaqRecyclerViewAdapter() {
        this.faq = new ArrayList<Faq>();
    }

    @NonNull
    @Override
    public FaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq, parent, false);
        return new FaqViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FaqViewHolder holder, int position) {
        Faq faq1 = faq.get(position);
        try {
            holder.bind(faq1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return faq.size();
    }

    public void setFaq(List<Faq> faq) {
        this.faq = faq;
        notifyDataSetChanged();
    }

}

