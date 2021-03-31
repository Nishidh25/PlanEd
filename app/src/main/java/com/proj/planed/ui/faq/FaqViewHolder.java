package com.proj.planed.ui.faq;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proj.planed.R;


public class FaqViewHolder extends RecyclerView.ViewHolder {
    private TextView question, answer;


    public FaqViewHolder(@NonNull View itemView) {
        super(itemView);

        question = itemView.findViewById(R.id.question);
        answer = itemView.findViewById(R.id.answer);

    }

    public void bind(Faq faq) throws Exception {
        question.setText(faq.getQuestion());
        answer.setText(faq.getAnswer());
    }

}
