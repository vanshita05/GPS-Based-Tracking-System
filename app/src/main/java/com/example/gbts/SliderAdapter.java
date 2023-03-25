package com.example.gbts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;



    public SliderAdapter(Context context)
    {
        this.context = context;

    }

    public int[] slide_images =
            {
                    R.drawable.hospital,
                    R.drawable.policestation,
                    R.drawable.firestation
            };

    public String[] slide_headings = {

            "NEAR BY HOSPITAL",
            "NEAR BY POLICE STATION",
            "NEAR BY FIRE STATION"
    };

    public String[] Slide_desc = {

            "GPS BASED TRACKING SYSTEM","GPS BASED TRACKING SYSTEM","GPS BASED TRACKING SYSTEM"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }


    public Object instantiateItem(ViewGroup container , int position)
    {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.learn);
        TextView slideHeading = (TextView) view.findViewById(R.id.textView);
        TextView slideDesc = (TextView) view.findViewById(R.id.descl);
        Button skip = (Button) view.findViewById(R.id.Next);



        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDesc.setText(Slide_desc[position]);

        container.addView(view);



        return view;


    }

    public void destroyItem(ViewGroup container, int position , Object object)
    {
        container.removeView((RelativeLayout)object);

    }


}
