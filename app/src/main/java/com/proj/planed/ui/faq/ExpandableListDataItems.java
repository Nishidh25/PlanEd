package com.proj.planed.ui.faq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataItems {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableDetailList = new HashMap<String, List<String>>();

        List<String> whatisplaned = new ArrayList<String>();
        whatisplaned.add("PlanEd application is an all-purpose reminder/alert system application to help you live independently, be on time with your medication and help you take control of your health and plan your daily life activities.This app is an effort to help you become independent and efficient and improve your lifestyle and health.Some features included are - setting alarm for a pill, planning activities.\n");

        List<String> themes = new ArrayList<String>();
        themes.add("There are 2 themes- dark and light. The dark mode is based on marigold with black colour and the light theme is based on blue with white colour. It is suggested as a contrast to night and day mode. You can select whichever theme you prefer for the application and change anytime.\n");

        List<String> edit = new ArrayList<String>();
        edit.add("1. Select Profile tab from bottom navigation menu\n 2. Click on edit icon\n 3. Change to your desired details\n 4. Click Submit\n");

        List<String> view = new ArrayList<String>();
        view.add("The home page consists of 4 cards which are named respectively according to the features. You can select the pill reminder card to view the alarms and planner card to view the daily activities. Similarly, there are 4 tabs in the bottom navigation menu, you can select one option accordingly.\n");

        List<String> set = new ArrayList<String>();
        set.add("1. Click on the “+” at the bottom of the page \n 2. Set up necessary information such as name, time, frequency of the alarm \n 3. Click “OK” to finish\n");

        List<String> delete = new ArrayList<String>();
        delete.add("You just have to swipe left/ right on the set alarm. It’s as easy as pie! \n");

        expandableDetailList.put("What is PlanED?", whatisplaned);
        expandableDetailList.put("What are themes?", themes);
        expandableDetailList.put("Where do I see my activities/alarms?", view);
        expandableDetailList.put("How do I edit Profile details?", edit);
        expandableDetailList.put("How to set up alarms?", set);
        expandableDetailList.put("How do I delete an alarm?", delete);

        return expandableDetailList;
    }
}

