package com.proj.planed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;


public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context=context;
    }
    public int[] slide_images={
            R.drawable.icon_list,
            R.drawable.icons_registration,
            R.drawable.icon_stethoscope

    };

    // Change Headings and description here
    public String[] slide_headings={ "Welcome to PlanEd","Heading 2","Registration"};
    public String[] slide_descriptions={ "Take control of your health and medications","Description 2","Have you used this app before?"};

    // Page count based on headings length
    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view== object;

    }

    @NonNull
    public Object instantiateItem(@NonNull ViewGroup container, int position){

        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slide_layout,container,false);
        ImageView slideImageView= view.findViewById(R.id.imageView2);
        TextView slideHeading= view.findViewById(R.id.textView3);
        TextView slidedescription= view.findViewById(R.id.description);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slidedescription.setText(slide_descriptions[position]);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object){
        container.removeView((ConstraintLayout)object);
    }

}
