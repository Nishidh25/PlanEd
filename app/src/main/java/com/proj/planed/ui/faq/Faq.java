package com.proj.planed.ui.faq;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "faq_table")
public class Faq {

    @PrimaryKey
    @NonNull
    private int faqId;

    private String question, answer;


    public Faq(int faqId, String question, String answer) {
        this.faqId = faqId;
        this.question= question;
        this.answer = answer;

    }

    public int getFaqId() {
        return faqId;
    }
    public String getQuestion() {
        return question;
    }
    public String getAnswer() {
        return answer;
    }

}
