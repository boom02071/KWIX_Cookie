package com.example.hyun.drawnav;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

/*이미지 뷰 클릭시 나오는 커스텀 다이얼로그*/
public class Dialog extends AppCompatActivity {

    //다이알로그
    AlertDialog dlg;
    View dialog;
    TextView shopname;
    TextView xbtn;
    TextView foodname;
    TextView price;
    Button hash1,hash2;
    TextView firstscore, secondscore;
    RatingBar firstStar,secondStar;
    private static ViewPager mPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //다이알로그 정의
        dialog = View.inflate(getApplicationContext(),R.layout.menu_dialog,null);
        dlg = new AlertDialog.Builder(Dialog.this).setView(dialog).create();

        dlg.show();

        xbtn = (TextView) dialog.findViewById(R.id.x);
        xbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
                finish();
            }
        });


        Button morebtn=(Button)dialog.findViewById(R.id.morebtn);
        morebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),menulist.class);
                startActivity(i);
                finish();
            }
        });

        change();

    }//onCreate

    public void change()
    {
        //가게이름
        shopname = (TextView)dialog.findViewById(R.id.shopname);
        shopname.setText("말랑둘기의 카페");
        //음식이름
        foodname = (TextView)dialog.findViewById(R.id.foodname);
        foodname.setText("말랑둘기 스페셜");
        //가격
        price = (TextView)dialog.findViewById(R.id.price);
        price.setText(Integer.toString(2500));
        //해쉬태그
        hash1 = (Button)dialog.findViewById(R.id.hash1);
        hash1.setText("달콤함");
        hash2 = (Button)dialog.findViewById(R.id.hash2);
        hash2.setText("고소함");
        //별점
        firstStar = (RatingBar)dialog.findViewById(R.id.ratingBar);
        firstStar.setRating((float) 3.5);
        secondStar = (RatingBar)dialog.findViewById(R.id.ratingBar2);
        secondStar.setRating((float)4.3);
        //평점
        firstscore = (TextView)dialog.findViewById(R.id.score);
        firstscore.setText(Float.toString((float) 3.5));
        secondscore = (TextView)dialog.findViewById(R.id.score2);
        secondscore.setText(Float.toString((float)4.3));

    }
}
