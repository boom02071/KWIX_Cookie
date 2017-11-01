package com.example.hyun.drawnav;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapOverlay;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapCalloutOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
/*상세보기 페이지*/

public class menulist extends NMapActivity implements NMapView.OnMapStateChangeListener,NMapView.OnMapViewTouchEventListener,NMapOverlayManager.OnCalloutOverlayListener{


    // API-KEY
    public static final String API_KEY = "JKi5hcmwtCpWCd15cJWj";  //<---맨위에서 발급받은 본인 ClientID 넣으세요.
    // 네이버 맵 객체
    NMapView mMapView = null;
    // 맵 컨트롤러
    NMapController mMapController = null;
    // 맵을 추가할 레이아웃
    LinearLayout MapContainer;
    NMapViewerResourceProvider mMapViewerResourceProvider= null;
    NMapOverlayManager mOverlayManager;
    NMapPOIdataOverlay.OnStateChangeListener onPOIdataStateChangeListener =null;


    //php
    private static String TAG = "phpmenu_MenuList";
    private static final String TAG_JSON="codingstory";
    private static final String TAG_NAME = "name";
    private static final String TAG_TAG1 = "tag1";
    private static final String TAG_SCORE1 = "score1";
    private static final String TAG_TAG2 = "tag2";
    private static final String TAG_SCORE2 = "score2";
    private static final String TAG_TAG3 = "tag3";
    private static final String TAG_SCORE3 = "score3";
    private static final String TAG_SHOP_NAME = "shop_name";
    private static final String TAG_PHOTO = "photo";
    private static final String TAG_PRICE = "price";
    String mJsonString;
    ArrayList<MyItem> food_list = new ArrayList<MyItem>();
    ArrayList<DB> db_list = new ArrayList<DB>();
    ArrayList<Bitmap> picture_list = new ArrayList<Bitmap>();

    //사진
    Bitmap bmimg;

    //
    ProgressDialog progressDialog;

    //이름
    String Shop_name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menulist);


        // 네이버 지도를 넣기 위한 LinearLayout 컴포넌트
        MapContainer = (LinearLayout) findViewById(R.id.MapContainer);

        // 네이버 지도 객체 생성
        mMapView = new NMapView(this);

        // 지도 객체로부터 컨트롤러 추출
        mMapController = mMapView.getMapController();

        // 네이버 지도 객체에 APIKEY 지정
        mMapView.setApiKey(API_KEY);

        // 생성된 네이버 지도 객체를 LinearLayout에 추가시킨다.
        MapContainer.addView(mMapView);

        // 지도를 터치할 수 있도록 옵션 활성화
        mMapView.setClickable(true);

        // 확대/축소를 위한 줌 컨트롤러 표시 옵션 활성화
        mMapView.setBuiltInZoomControls(true, null);

        // 지도에 대한 상태 변경 이벤트 연결
        mMapView.setOnMapStateChangeListener(this);

        //mOverlayManager 객체 생성 지도 위에 표시되는 오버레이 객체들 관리
        mMapViewerResourceProvider = new NMapViewerResourceProvider(this);
        mOverlayManager = new NMapOverlayManager(this,mMapView, mMapViewerResourceProvider);

        //NMapPOIdataOverlay여러개의 오버레이 아이템들을 하나의 오버레이 객체에서 관리
        int markerID = NMapPOIflagType.PIN;

        //POI 데이터 설정
        NMapPOIdata poiData = new NMapPOIdata(1,mMapViewerResourceProvider);

        poiData.beginPOIdata(1);
        poiData.addPOIitem(127.0582915, 37.6201787, "지지고 광운대점",markerID,0);
        poiData.endPOIdata();
        NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData,null);

        // 마커 위치 중심의 화면으로 변경하기 위한 것
        poiDataOverlay.showAllPOIdata(0);



        ///음식점 이름 가져오기
        Intent intent = getIntent();
        Shop_name = intent.getStringExtra("name");

        registDB a = new registDB();
        a.execute("hi");

    }
    /**
     * 지도가 초기화된 후 호출된다.
     * 정상적으로 초기화되면 errorInfo 객체는 null이 전달되며,
     * 초기화 실패 시 errorInfo객체에 에러 원인이 전달된다
     */
    @Override
    public void onMapInitHandler(NMapView mapview, NMapError errorInfo){
        if (errorInfo == null) { // success
            //mMapController.setMapCenter(new NGeoPoint(126.978371, 37.5666091), 11);//좌표
        }
        else {
            android.util.Log.e("NMAP", "onMapINitHandler: error=" + errorInfo.toString());
        }
    }

    /**
     * 지도 레벨 변경 시 호출되며 변경된 지도 레벨이 파라미터로 전달된다.
     */
    @Override
    public void onZoomLevelChange(NMapView mapview, int level) {}

    /**
     * 지도 중심 변경 시 호출되며 변경된 중심 좌표가 파라미터로 전달된다.
     */
    @Override
    public void onMapCenterChange(NMapView mapview, NGeoPoint center) {}

    /**
     * 지도 애니메이션 상태 변경 시 호출된다.
     * animType : ANIMATION_TYPE_PAN or ANIMATION_TYPE_ZOOM
     * animState : ANIMATION_STATE_STARTED or ANIMATION_STATE_FINISHED
     */
    @Override
    public void onAnimationStateChange(
            NMapView arg0, int animType, int animState) {}

    @Override
    public void onMapCenterChangeFine(NMapView arg0) {}

    @Override
    public void onLongPress(NMapView nMapView, MotionEvent motionEvent) {

    }

    @Override
    public void onLongPressCanceled(NMapView nMapView) {

    }

    @Override
    public void onTouchDown(NMapView nMapView, MotionEvent motionEvent) {

    }

    @Override
    public void onTouchUp(NMapView nMapView, MotionEvent motionEvent) {

    }

    @Override
    public void onScroll(NMapView nMapView, MotionEvent motionEvent, MotionEvent motionEvent1) {

    }

    @Override
    public void onSingleTapUp(NMapView nMapView, MotionEvent motionEvent) {

    }

    @Override
    public NMapCalloutOverlay onCreateCalloutOverlay(NMapOverlay nMapOverlay, NMapOverlayItem nMapOverlayItem, Rect rect) {
        return null;
    }


    //메뉴 리스트 부분
    public class MyItem{
        Bitmap image;
        String text = "";
        int price=0;

        public MyItem(Bitmap image,String text, int price){
            super();
            this.image=image;
            this.text = text;
            this.price = price;
        }
    }

    public class DB{
        String text = "";
        String address="";
        int price=0;

        public DB(String text, String address,int price){
            super();
            this.text = text;
            this.address = address;
            this.price = price;
        }
    }


    //메뉴 리스트 부분

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
            textView2.setText(Integer.toString(myItems.get(pos).price));

            TextView textView3 = (TextView)convertView.findViewById(R.id.boon);
            textView3.setText("원");



            return convertView;
        }
    }
    private class back extends AsyncTask<String, Integer,Bitmap> {
        //ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //progressDialog = ProgressDialog.show(menulist.this,"Please wait",null,true,true);
        }
        @Override
        protected Bitmap doInBackground(String... urls) {
            try{

                for(int i=0;i<db_list.size();i++) {
                    //URL myFileUrl = new URL("http://13.59.52.83/" + db_list.get(i).address);
                    URL myFileUrl = new URL("http://13.59.52.83" + db_list.get(i).address);
                    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bmimg = BitmapFactory.decodeStream(is);
                    picture_list.add(bmimg);
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
                food_list.add(new MyItem(picture_list.get(i),db_list.get(i).text,db_list.get(i).price));
            }


            MyListAdapter adapter = new MyListAdapter(menulist.this,food_list,R.layout.list_row);
            ListView listView = (ListView)findViewById(R.id.menu_list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(mItemClickListener);
        }

        AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(menulist.this,star.class);
                intent.putExtra("name",Shop_name);
                startActivityForResult(intent,0);

            }
        };
    }

    private class registDB extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(menulist.this,

                    "Please Wait", null, true, true);

        }

        @Override
        protected String doInBackground(String... strings) {
            String errorString = null;
            String param = "id=" + Shop_name;

            try {

                URL url = new URL("http://13.59.52.83/SelectMenu.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(param.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);


                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();
                return sb.toString().trim();

            }catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();
                return null;

            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mJsonString=s;
            showResult();
        }

        private void showResult(){
            try{
                JSONObject jsonObject = new JSONObject(mJsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
                back task=new back();

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject item = jsonArray.getJSONObject(i);


                    String name = item.getString(TAG_NAME);
                    String tag1 = item.getString(TAG_TAG1);
                    String score1 = item.getString(TAG_SCORE1);
                    String tag2 = item.getString(TAG_TAG2);
                    String score2 = item.getString(TAG_SCORE2);
                    String tag3 = item.getString(TAG_TAG3);
                    String score3 = item.getString(TAG_SCORE3);
                    String shop_name = item.getString(TAG_SHOP_NAME);
                    String photo = item.getString(TAG_PHOTO);
                    String price = item.getString(TAG_PRICE);


                    HashMap<String,String> hashMap = new HashMap<>();


                    hashMap.put(TAG_NAME,name);
                    hashMap.put(TAG_TAG1,tag1);
                    hashMap.put(TAG_SCORE1,score1);
                    hashMap.put(TAG_TAG2,tag2);
                    hashMap.put(TAG_SCORE2,score2);
                    hashMap.put(TAG_TAG3,tag3);
                    hashMap.put(TAG_SCORE3,score3);
                    hashMap.put(TAG_SHOP_NAME,shop_name);
                    hashMap.put(TAG_PHOTO,photo);
                    hashMap.put(TAG_PRICE,price);


                    db_list.add(new DB(name,photo,Integer.parseInt(price)));

                }

                task.execute("");

            }catch (JSONException e) {
                Log.d(TAG, "showResult : ", e);
            }
        }


    }
}
