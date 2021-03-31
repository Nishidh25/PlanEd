package com.proj.planed.ui.faq;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.proj.planed.Database;
import java.util.List;

public class FaqRepository {
    private FaqDao faqDao;
    private LiveData<List<Faq>> faqLiveData;

    public FaqRepository(Application application) {
        Database db = Database.getDatabase(application);
        faqDao = db.faqDao();
        faqLiveData = faqDao.getFaq();
    }

    public void insert(Faq faq) {
        Database.databaseWriteExecutor.execute(() -> {
            faqDao.insert(faq);
        });
    }


    public LiveData<List<Faq>> getFaqSearch(String name) {
        faqLiveData = faqDao.getFaqSearch(name);
        return faqLiveData;
    }


    public LiveData<List<Faq>> getAlarmsLiveData() {
        return faqLiveData;
    }
}
