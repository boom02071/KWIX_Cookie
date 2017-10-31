package com.example.hyun.drawnav;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

/**
 * Created by Hyun on 2017-10-27.
 */

public class hashtag extends AppCompatActivity implements View.OnClickListener {
    ToggleButton MenuKorean;    //한식
    ToggleButton MenuWestern;   //양식
    ToggleButton MenuChicken;   //치킨
    ToggleButton MenuChina;     //중식
    ToggleButton MenuDesert;    //분식/디저트
    ToggleButton MenuMeat;      //족발/보쌈
    ToggleButton MenuJapan;     //일식/돈까스

    ToggleButton TasteSweet;    //달콤한
    ToggleButton TasteSpicy;    //매운
    ToggleButton TasteClean;    //담백한
    ToggleButton TasteNutty;    //고소한
    ToggleButton TasteSalty;    //짠
    ToggleButton TasteFresh;    //상큼한

    Button BtnSave;             //저장
    Button BtnCancel;           //취소

    int check1 = 0, check2 = 0, check3 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        setTitle("해쉬태그 설정");

        MenuKorean=(ToggleButton)findViewById(R.id.MenuKorean);
        MenuWestern=(ToggleButton)findViewById(R.id.MenuWestern);
        MenuChicken=(ToggleButton)findViewById(R.id.MenuChicken);
        MenuChina=(ToggleButton)findViewById(R.id.MenuChina);
        MenuDesert=(ToggleButton)findViewById(R.id.MenuDesert);
        MenuMeat=(ToggleButton)findViewById(R.id.MenuMeat);
        MenuJapan=(ToggleButton)findViewById(R.id.MenuJapan);

        TasteSweet=(ToggleButton)findViewById(R.id.TasteSweet);
        TasteSpicy=(ToggleButton)findViewById(R.id.TasteSpicy);
        TasteClean=(ToggleButton)findViewById(R.id.TasteClean);
        TasteNutty=(ToggleButton)findViewById(R.id.TasteNutty);
        TasteSalty=(ToggleButton)findViewById(R.id.TasteSalty);
        TasteFresh=(ToggleButton)findViewById(R.id.TasteFresh);

        BtnSave=(Button)findViewById(R.id.BtnSave);
        BtnCancel=(Button)findViewById(R.id.BtnCancel);

        BtnSave.setOnClickListener(this);
        BtnCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.equals(BtnSave)){
            //각 버튼이 눌렸는지 확인
            if(MenuKorean.isChecked()) {
                //한식 체크
            }
            if(MenuWestern.isChecked()) {
                //양식 체크
            }
            if(MenuChicken.isChecked()) {
                //치킨 체크
            }
            if(MenuChina.isChecked()) {
                //중식 체크
            }
            if(MenuDesert.isChecked()) {
                //디저트/분식 체크
            }
            if(MenuMeat.isChecked()) {
                //족발/보쌈 체크
            }
            if(MenuJapan.isChecked()) {
                //일식 체크
            }
            if(TasteSweet.isChecked()) {            //달콤함 체크
                check1 = 1;
            }
            if(TasteSpicy.isChecked()) {            //매운맛 체크
                //if(check1 != 0) check2 = 2;
                //else            check1 = 2;
            }
            if(TasteClean.isChecked()) {            //담백함 체크
                //if(check1 != 0 && check2 != 0)  check3 = 3;
                //else if(check1 != 0)            check2 = 3;
                //else                            check1 = 3;
            }
            if(TasteNutty.isChecked()) {            //고소함 체크
                //if(check1 != 0 && check2 != 0)  check3 = 4;
                //else if(check1 != 0)            check2 = 4;
                //else                            check1 = 4;
            }
            if(TasteSalty.isChecked()) {            //짠맛 체크
                //if(check1 != 0 && check2 != 0)  check3 = 5;
                //else if(check1 != 0)            check2 = 5;
                //else                            check1 = 5;
            }
            if(TasteFresh.isChecked()) {            //상큼함 체크
                //if(check1 != 0 && check2 != 0)  check3 = 6;
                //else if(check1 != 0)            check2 = 6;
                //else                            check1 = 6;
            }
        }
        if(v.equals(BtnCancel)){
            //이전 화면으로 이동
        }
    }
}
