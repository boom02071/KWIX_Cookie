package com.example.hyun.drawnav;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Hyun on 2017-10-13.
 * 이미지뷰 슬라이딩 페이지
 */

public class SlidingImage_Adapter extends PagerAdapter {


    private ArrayList<ImageModel> imageModelArrayList;
    private LayoutInflater inflater;
    private Context context;


    public SlidingImage_Adapter(Context context, ArrayList<ImageModel> imageModelArrayList) {
        this.context = context;
        this.imageModelArrayList = imageModelArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imageModelArrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        final View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
        imageView.setImageResource(imageModelArrayList.get(position).getImage_drawable());


        imageLayout.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                if(position ==0){
                    Intent i = new Intent(context.getApplicationContext(),Dialog.class);
                    context.startActivity(i);
                }
                else if(position==1)
                {
                    Intent i = new Intent(context.getApplicationContext(),Dialog.class);
                    context.startActivity(i);
                }
                else{
                    Intent i = new Intent(context.getApplicationContext(),Dialog.class);
                    context.startActivity(i);
                }
            }
        });
        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }



}