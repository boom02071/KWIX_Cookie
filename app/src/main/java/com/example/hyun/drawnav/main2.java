package com.example.hyun.drawnav;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Hyun on 2017-10-27.
 */

public class main2 extends Fragment implements View.OnClickListener{


    Button MenuKorean;    //한식
    Button MenuWestern;   //양식
    Button MenuChicken;   //치킨

    // view pager 이미지 슬라이딩 부분
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;

    private int[] myImageList = new int[]{R.drawable.img1, R.drawable.img2,
            R.drawable.img3,R.drawable.img4};

    // 말풍선 메뉴
    ImageButton AllMenu;         //전체메뉴
    ImageButton KoreanFood;     //한식
    ImageButton WesternFood;    //양식
    ImageButton ChickenFood;    //치킨
    ImageButton ChinaFood;      //중국집
    ImageButton DesertFood;     //분식/디저트
    ImageButton JapanFood;      //일식

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("main");
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MenuKorean= (Button) getView().findViewById(R.id.MenuKorean);
        MenuWestern=(Button)getView().findViewById(R.id.MenuWestern);
        MenuChicken=(Button)getView().findViewById(R.id.MenuChicken);

        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();

        init();

        AllMenu=(ImageButton)getView().findViewById(R.id.AllMenu);             //전체메뉴
        KoreanFood=(ImageButton)getView().findViewById(R.id.KoreanFood);      //한식
        WesternFood=(ImageButton)getView().findViewById(R.id.WesternFood);    //양식
        ChickenFood=(ImageButton)getView().findViewById(R.id.ChickenFood);    //치킨
        ChinaFood=(ImageButton)getView().findViewById(R.id.ChinaFood);        //중국집
        DesertFood=(ImageButton)getView().findViewById(R.id.DesertFood);      //분식/디저트
        JapanFood=(ImageButton)getView().findViewById(R.id.JapanFood);        //일식

        AllMenu.setOnClickListener(this);
        KoreanFood.setOnClickListener(this);
        WesternFood.setOnClickListener(this);
        ChickenFood.setOnClickListener(this);
        ChinaFood.setOnClickListener(this);
        DesertFood.setOnClickListener(this);
        JapanFood.setOnClickListener(this);
        
    }

    private void init() {
        mPager = (ViewPager) getView().findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(getActivity(),imageModelArrayList));

        CirclePageIndicator indicator = (CirclePageIndicator)
                getView().findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES =imageModelArrayList.size();

        // Auto start of viewpager
        final android.os.Handler handler= new android.os.Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);

            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }
            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {
            }
            @Override
            public void onPageScrollStateChanged(int pos) {
            }
        });
    }


    private ArrayList<ImageModel> populateList() {
        ArrayList<ImageModel> list = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            ImageModel imageModel = new ImageModel();
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }

        return list;
    }
    @Override
    public void onClick(View v) {
        if(v.equals(AllMenu)){
            Intent intent = new Intent(getActivity(),list.class);
            intent.putExtra("type","전체");
            startActivity(intent);

        }
        if(v.equals(KoreanFood)){
            Intent intent = new Intent(getActivity(),list.class);
            intent.putExtra("type","한식");
            startActivity(intent);

        }
        if(v.equals(WesternFood)){
            Intent intent = new Intent(getActivity(),list.class);
            intent.putExtra("type","양식");
            startActivity(intent);
        }
        if(v.equals(ChickenFood)){
            Intent intent = new Intent(getActivity(),list.class);
            intent.putExtra("type","치킨");
            startActivity(intent);

        }
        if(v.equals(ChinaFood)){
            Intent intent = new Intent(getActivity(),list.class);
            intent.putExtra("type","중식");
            startActivity(intent);

        }
        if(v.equals(DesertFood)){
            Intent intent = new Intent(getActivity(),list.class);
            intent.putExtra("type","분식/디저트");
            startActivity(intent);

        }
        if(v.equals(JapanFood)){
            Intent intent = new Intent(getActivity(),list.class);
            intent.putExtra("type","일식");
            startActivity(intent);
        }

    }
}
