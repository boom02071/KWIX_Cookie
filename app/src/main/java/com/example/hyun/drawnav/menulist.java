package com.example.hyun.drawnav;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
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
        String text = "";
        int price=0;

        public MyItem(String text, int price){
            super();
            this.text = text;
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
