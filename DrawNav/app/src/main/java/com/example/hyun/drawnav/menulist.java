package com.example.hyun.drawnav;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
/*상세보기 페이지*/

public class menulist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menulist);


        //어레이리스트생성 및 아이템 삽입
        ArrayList<MyItem> food_list = new ArrayList<MyItem>();

        food_list.add(new MyItem("이미지1",3000));
        food_list.add(new MyItem("이미지2",5000));
        food_list.add(new MyItem("이미지3",15000));
        food_list.add(new MyItem("이미지4",25000));


        MyListAdapter adapter = new MyListAdapter(this,food_list,R.layout.list_row);
        ListView listView = (ListView)findViewById(R.id.menu_list);
        listView.setAdapter(adapter);
    }

    public class MyItem{
        String text = "";
        int price=0;

        public MyItem(String text, int price){
            super();
            this.text = text;
            this.price = price;
        }
    }

    public class MyListAdapter extends BaseAdapter {
        Context context;
        LayoutInflater inflater;
        ArrayList<MyItem> myItems;
        int layout;

        public MyListAdapter(Context context, ArrayList<MyItem> myItems,
                             int layout) {
            this.context=context;
            this.myItems=myItems;
            this.inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.layout=layout;
        }

        public int getCount(){
            return myItems.size();
        }

        public Object getItem(int position){
            return myItems.get(position).text;
        }

        public long getItemId(int position)
        {
            return position;
        }

        public View getView(int position, View convertView,
                            ViewGroup parent){
            final int pos = position;
            if(convertView==null)
                convertView = inflater.inflate(layout,parent,false);

            ImageView imageView = (ImageView) convertView.findViewById(R.id.img);
            imageView.setVisibility(View.INVISIBLE);

            TextView textView = (TextView)convertView.findViewById(R.id.text);
            textView.setText(myItems.get(pos).text);

            TextView textView2 = (TextView)convertView.findViewById(R.id.min);
            textView2.setText(Integer.toString(myItems.get(pos).price));

            TextView textView3 = (TextView)convertView.findViewById(R.id.boon);
            textView3.setText("원");



            return convertView;
        }
    }
}
