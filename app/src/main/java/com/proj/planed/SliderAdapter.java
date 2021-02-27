package com.proj.planed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.viewpager.widget.PagerAdapter;


public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context=context;}
    public int[] slide_images={
            R.drawable.icon_list,
            R.drawable.icons_registration,
            R.drawable.icon_stethoscope

    };
    public String[] slide_headings={ "Welcome to PlanEd","Registration","Heading 3"};
    public String[] slide_descriptions={ "Take control of your health and medications","Have you used this app before?","Description 3"};

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view== (RelativeLayout) object;


    }
    public Object instantiateItem(ViewGroup container, int position){

        layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slide_layout,container,false);
        ImageView slideImageView=(ImageView)view.findViewById(R.id.imageView2);
        TextView slideHeading=(TextView)view.findViewById(R.id.textView3);
        TextView slidedescription=(TextView)view.findViewById(R.id.description);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slidedescription.setText(slide_descriptions[position]);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container,int position,Object object){
        container.removeView((RelativeLayout)object);
    }

}
