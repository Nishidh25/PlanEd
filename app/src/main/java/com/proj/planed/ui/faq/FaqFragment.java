package com.proj.planed.ui.faq;

import android.app.Application;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.proj.planed.NavigationActivity;
import com.proj.planed.R;
import com.proj.planed.ui.alarms.Alarm;
import com.proj.planed.ui.alarms.AlarmRecyclerViewAdapter;
import com.proj.planed.ui.alarms.AlarmRepository;
import com.proj.planed.ui.alarms.AlarmsListViewModel;
import com.proj.planed.ui.alarms.TimePickerUtil;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class FaqFragment extends Fragment {
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";
    FaqRecyclerViewAdapter faqRecyclerViewAdapter;
    FaqRepository faqRepository;
    private LiveData<List<Faq>> faqLiveData;
    RecyclerView faqRecyclerView;
    SearchView searchView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_faq, container, false);
        //final TextView textView = root.findViewById(R.id.text_slideshow);

        //textView.setText("This is faq fragment");






        faqRecyclerViewAdapter = new FaqRecyclerViewAdapter();
        faqRecyclerView = root.findViewById(R.id.fragment_listfaq_recylerView);

        faqRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        faqRecyclerView.setAdapter(faqRecyclerViewAdapter);


        faqRepository = new FaqRepository(getActivity().getApplication());
        faqLiveData = faqRepository.getAlarmsLiveData();

        faqLiveData.observe(getViewLifecycleOwner(), new Observer<List<Faq>>() {
            @Override
            public void onChanged(List<Faq> faq) {
                if (faq != null) {
                    faqRecyclerViewAdapter.setFaq(faq);
                }
            }
        });


        Boolean isFirstRun = getActivity().getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            getActivity().getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                    .putBoolean("isFirstRun", false).apply();

            // To do: Create a string array, get it from strings.xml and insert into db using this function
            createfaq(1, "q1","a1");
            createfaq(2, "q2","a2");
            createfaq(3, "q3","a3");
            createfaq(4, "q4","a4");


        }else {

        }

     /*   searchView = ((FaqActivity) getActivity()).searchView;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(getActivity().getApplicationContext(), newText,Toast.LENGTH_SHORT).show();
                searchFaq(newText);
                return false;
            }
        });
        */




        return root;
    }


    private void createfaq(int id, String q, String a){
        //int faqId = new Random().nextInt(Integer.MAX_VALUE);

        Faq faq = new Faq(
                id,
                q,
                a
        );

        faqRepository.insert(faq);


    }

    public void searchFaq(String searchtxt){

        faqRepository.getFaqSearch(searchtxt).observe(getViewLifecycleOwner(), new Observer<List<Faq>>() {

            @Override
            public void onChanged(List<Faq> faq) {
                if (faq != null) {
                    faqRecyclerViewAdapter.setFaq(faq);
                }
            }
        });
        faqRecyclerViewAdapter.notifyDataSetChanged();



    }

}