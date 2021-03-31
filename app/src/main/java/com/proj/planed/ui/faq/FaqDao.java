package com.proj.planed.ui.faq;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.proj.planed.ui.alarms.Alarm;

import java.util.List;

@Dao
public interface FaqDao {
    @Insert
    void insert(Faq faq);

    @Query("DELETE FROM faq_table")
    void deleteAll();

    @Query("SELECT * FROM faq_table ORDER BY faqId ASC")
    LiveData<List<Faq>> getFaq();

    @Update
    void update(Faq faq);

    @Delete
    void delete(Faq faq);


    @Query("SELECT * FROM faq_table WHERE question LIKE '%' || :text || '%' ORDER BY faqId ASC")
    LiveData<List<Faq>> getFaqSearch(String text);

}
