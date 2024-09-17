package com.example.hyun.drawnav;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
/*가게 리스트*/
public class list extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //어레이리스트생성 및 아이템 삽입
        ArrayList<MyItem> list = new ArrayList<MyItem>();

        list.add(new MyItem(R.drawable.img1,"이미지1",3));
        list.add(new MyItem(R.drawable.img2,"이미지2",5));
        list.add(new MyItem(R.drawable.img3,"이미지3",15));
        list.add(new MyItem(R.drawable.img4,"이미지4",25));


        MyListAdapter adapter = new MyListAdapter(this,list,R.layout.list_row);
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
    }

    public class MyItem{
        int image=0;
        String text = "";
        int min=0;

        public MyItem(int image, String text, int min){
            super();
            this.image = image;
            this.text = text;
            this.min = min;
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
            imageView.setImageResource(myItems.get(pos).image);

            TextView textView = (TextView)convertView.findViewById(R.id.text);
            textView.setText(myItems.get(pos).text);

            TextView textView2 = (TextView)convertView.findViewById(R.id.min);
            textView2.setText(Integer.toString(myItems.get(pos).min));

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView fullImage = (ImageView) findViewById(R.id.fullimg);
                    fullImage.setImageResource(myItems.get(pos).image);
                    fullImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            recreate();
                        }
                    });
                }
            });

            return convertView;
        }
    }
}

