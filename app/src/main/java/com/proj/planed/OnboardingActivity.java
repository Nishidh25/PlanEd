package com.proj.planed;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;

public class OnboardingActivity extends AppCompatActivity {


    FirebaseAuth mAuth;

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private SliderAdapter sliderAdapter;
    private TextView[] mdots;
    private Button mNextBtn;
    private Button mNextBtn2;
    private Button mBackBtn;
    private int mCurrentPage;
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {


        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            mCurrentPage = i;
            if (i == 0) {

                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(false);
                mNextBtn2.setEnabled(false);
                mBackBtn.setVisibility(View.INVISIBLE);
                mNextBtn2.setVisibility(View.INVISIBLE);
                mNextBtn.setText("Get Started");
                mBackBtn.setText("");
                mNextBtn.setOnClickListener(view -> mSlideViewPager.setCurrentItem(mCurrentPage + 1));

            } else if (i == mdots.length - 1) {
                mNextBtn.setEnabled(true);
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mNextBtn2.setVisibility(View.VISIBLE);
                mNextBtn2.setEnabled(true);

                mBackBtn.setVisibility(View.VISIBLE);
                mNextBtn.setText("No");
                mNextBtn2.setText("Yes");
                mBackBtn.setText("Back");

                //yes - ask for login
                mNextBtn2.setOnClickListener(view -> {

                    Intent intent = new Intent(getApplicationContext(), FirebaseActivity.class);
                    intent.putExtra("Type", "Login");
                    startActivity(intent);

                    //We need this to go back
                    //OnboardingActivity.this.finish();
                });

                //no - new user , register
                mNextBtn.setOnClickListener(view -> {

                    Intent intent = new Intent(getApplicationContext(), FirebaseActivity.class);
                    intent.putExtra("Type", "Register");
                    startActivity(intent);

                    //We need this to go back
                    //OnboardingActivity.this.finish();
                });


            } else {
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mNextBtn2.setEnabled(false);
                mNextBtn2.setVisibility(View.INVISIBLE);
                mBackBtn.setVisibility(View.VISIBLE);
                mNextBtn.setText("Next");
                mBackBtn.setText("Back");
                mNextBtn.setOnClickListener(view -> mSlideViewPager.setCurrentItem(mCurrentPage + 1));


            }
        }

        @Override
        public void onPageScrollStateChanged(int sitate) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        mAuth = FirebaseAuth.getInstance();
        mSlideViewPager = findViewById(R.id.ViewPager);
        mDotLayout = findViewById(R.id.LinearLayout);
        sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);
        mNextBtn2 = findViewById(R.id.next);
        mNextBtn = findViewById(R.id.next2);
        mBackBtn = findViewById(R.id.previous);
        addDotsIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSlideViewPager.setCurrentItem(mCurrentPage + 1);
            }
        });

        mNextBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FirebaseActivity.class));
            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSlideViewPager.setCurrentItem(mCurrentPage - 1);
            }
        });
    }

    public void addDotsIndicator(int position) {

        mdots = new TextView[3];
        mDotLayout.removeAllViews();
        for (int i = 0; i < mdots.length; i++) {
            mdots[i] = new TextView(this);
            mdots[i].setText(Html.fromHtml("&#8226;"));
            mdots[i].setTextSize(25);
            //mdots[i].setTextColor(getColor(R.color.colorTransparentWhite));
            mDotLayout.addView(mdots[i]);
        }
        if (mdots.length > 0) {
            mdots[position].setTextColor(getColor(R.color.navyblue));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        //if the user is already signed in
        //we will close this activity
        //and take the user to profile activity
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, HomeActivity.class));
            OnboardingActivity.this.finish();
        }
    }


}
