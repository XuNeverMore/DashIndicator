package com.nevermore.myapplication;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nevermore.dashindicator.DashIndicator;

public class MainActivity extends AppCompatActivity {


    private int[] colors = new int[]{Color.BLACK,Color.BLUE,Color.RED};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        DashIndicator dashIndicator = (DashIndicator) findViewById(R.id.dash_indicator);

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return colors.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view==o;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                TextView textView = new TextView(MainActivity.this);
                textView.setBackgroundColor(colors[position]);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(48);
                textView.setText(String.valueOf(position+1));
                textView.setTextColor(Color.WHITE);
                container.addView(textView);
                return textView;
            }
        });

        dashIndicator.setUpWithViewPager(viewPager);
    }
}
