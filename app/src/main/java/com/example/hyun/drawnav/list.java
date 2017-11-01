package com.example.hyun.drawnav;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hyun.drawnav.menulist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class list extends Activity {

    //php
    private static String TAG = "phptest_MainActivity";
    private static final String TAG_JSON="codingstory";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADDRESS ="address";
    private static final String TAG_WAITTIME = "waittime";
    private static final String TAG_TYPE = "type";
    private static final String TAG_LATITUDE = "latitude";
    private static final String TAG_LONGITUDE = "longitude";
    private static final String TAG_PHOTO = "photo";
    ArrayList<MyItem> list = new ArrayList<MyItem>();
    String mJsonString;

    //type
    String food_type;
    ProgressDialog progressDialog;
    ArrayList<Bitmap> picture_list = new ArrayList<Bitmap>();
    ArrayList<DB> db_list = new ArrayList<DB>();
    Bitmap bmimg;
    String Shop_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //
        Intent intent = getIntent();
        food_type = intent.getStringExtra("type");

        //어레이리스트생성 및 아이템 삽입
        GetData task = new GetData();
        task.execute("http://13.59.52.83/getjson.php");


    }

    public class DB{
        String text = "";
        String address="";
        int min=0;

        public DB(String text, String address,int min){
            super();
            this.text = text;
            this.address = address;
            this.min = min;
        }
    }

    public class MyItem{
        Bitmap image;
        String text = "";
        int min=0;

        public MyItem(Bitmap image, String text, int min){
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
            imageView.setImageBitmap(myItems.get(pos).image);

            TextView textView = (TextView)convertView.findViewById(R.id.text);
            textView.setText(myItems.get(pos).text);

            TextView textView2 = (TextView)convertView.findViewById(R.id.min);
            textView2.setText(Integer.toString(myItems.get(pos).min));

            return convertView;
        }
    }

    private class GetData extends AsyncTask<String,Void,String>{

        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(list.this,"Please wait",null,true,true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d(TAG,"response - "+s);


            mJsonString=s;
            showResult();

        }

        @Override
        protected String doInBackground(String... strings) {
            String serverURL = strings[0];

            try{
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                inputStream.close();
                bufferedReader.close();

                return sb.toString().trim();
            }catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }
        }

        private void showResult(){
            back task=new back();
            try{
                JSONObject jsonObject = new JSONObject(mJsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject item = jsonArray.getJSONObject(i);


                    String name = item.getString(TAG_NAME);
                    String address = item.getString(TAG_ADDRESS);
                    String waittime = item.getString(TAG_WAITTIME);
                    String type = item.getString(TAG_TYPE);
                    String latitude = item.getString(TAG_LATITUDE);
                    String longitude = item.getString(TAG_LONGITUDE);
                    String photo = item.getString(TAG_PHOTO);

                    HashMap<String,String> hashMap = new HashMap<>();


                    hashMap.put(TAG_NAME,name);
                    hashMap.put(TAG_ADDRESS,address);
                    hashMap.put(TAG_WAITTIME,waittime);
                    hashMap.put(TAG_TYPE,type);
                    hashMap.put(TAG_LATITUDE,latitude);
                    hashMap.put(TAG_LONGITUDE,longitude);
                    hashMap.put(TAG_PHOTO,photo);

                    if(!food_type.equals("전체")&&!type.equals(food_type)) continue;

                    //이부분
                    db_list.add(new DB(name,photo,Integer.parseInt(waittime)));

                }

                task.execute("");

            }catch (JSONException e) {
                Log.d(TAG, "showResult : ", e);
            }
        }
    }


    private class back extends AsyncTask<String, Integer,Bitmap> {
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected Bitmap doInBackground(String... urls) {
            try{

                //이부분
                for(int i=0;i<db_list.size();i++) {
                    URL myFileUrl = new URL("http://13.59.52.83" + db_list.get(i).address);
                    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bmimg = BitmapFactory.decodeStream(is);
                    picture_list.add(bmimg);
                    is.close();

                }

            }catch(IOException e){
                e.printStackTrace();
            }
            return bmimg;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            progressDialog.dismiss();

            for(int i=0;i<picture_list.size();i++){
                list.add(new MyItem(picture_list.get(i),db_list.get(i).text,db_list.get(i).min));
            }

            MyListAdapter adapter = new MyListAdapter(list.this,list,R.layout.list_row);
            ListView listView = (ListView)findViewById(R.id.list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(mItemClickListener);
        }
    }

    AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Shop_name = list.get(i).text;

            Intent intent = new Intent(list.this,menulist.class);
            intent.putExtra("name",Shop_name);
            startActivityForResult(intent,0);

        }
    };

}

