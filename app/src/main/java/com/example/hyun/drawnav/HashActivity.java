package com.example.hyun.drawnav;

/**
 * Created by kyj on 2017-10-31.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class HashActivity extends AppCompatActivity implements View.OnClickListener {

    ToggleButton TasteSweet;    //달콤한
    ToggleButton TasteSpicy;    //매운
    ToggleButton TasteClean;    //담백한
    ToggleButton TasteNutty;    //고소한
    ToggleButton TasteSalty;    //짠
    ToggleButton TasteFresh;    //상큼한

    //EditText IDTxt;             //ID 입력창

    Button BtnSave;             //저장
    Button BtnCancel;           //취소

    //Button LogBtn;              //db에서 불러온 설정세팅

    private static String TAG="phptest";
    private static final String Tag_JSON="codingstory";
    private static final String Taste1="taste1", Taste2="taste2", Taste3="taste3";
    String mJsonString;
    //private TextView resultTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_hash);

        TasteSweet=(ToggleButton)findViewById(R.id.TasteSweet);
        TasteSpicy=(ToggleButton)findViewById(R.id.TasteSpicy);
        TasteClean=(ToggleButton)findViewById(R.id.TasteClean);
        TasteNutty=(ToggleButton)findViewById(R.id.TasteNutty);
        TasteSalty=(ToggleButton)findViewById(R.id.TasteSalty);
        TasteFresh=(ToggleButton)findViewById(R.id.TasteFresh);

        //IDTxt=(EditText)findViewById(R.id.IDTxt);
        //LogBtn=(Button)findViewById(R.id.LogBtn);

        //resultTxt=(TextView)findViewById(R.id.TestView);//test

        BtnSave=(Button)findViewById(R.id.BtnSave);
        BtnCancel=(Button)findViewById(R.id.BtnCancel);

        BtnSave.setOnClickListener(this);
        BtnCancel.setOnClickListener(this);
        //LogBtn.setOnClickListener(this);

//가져온 아이디 세팅
        Intent intent = getIntent();
        final String idFromIntent = intent.getExtras().getString("userId");
        Log.e("id : ",idFromIntent);
        GetData task = new GetData();
        task.execute(idFromIntent/*IDTxt.getText().toString()*/);


    }


    //검색 및 맛 데이터 저장
    @Override
    public void onClick(View v) {

        if(v.equals(BtnSave)){
            //if(IDTxt&PwdTxt==DB에 있을때)
            //else message : id나 비밀번호를 확인해주세요.
            //각 버튼이 눌렸는지 확인
            int cnt=0;
            String ta1="0", ta2="0", ta3="0";
            if(TasteSweet.isChecked()) {
                //달콤함 체크1
                cnt++;
                ta1="1";
            }
            if(TasteSpicy.isChecked()) {
                //매운맛 체크2
                cnt++;
                if(ta1=="0") ta2="2";
                else ta3="2";
            }
            if(TasteClean.isChecked()) {
                //담백함 체크3
                cnt++;
                if(ta1=="0") ta1="3";
                else if(ta2=="0") ta2="3";
                else ta3="3";
            }
            if(TasteNutty.isChecked()) {
                //고소함 체크4
                cnt++;
                if(ta1=="0") ta1="4";
                else if(ta2=="0") ta2="4";
                else ta3="4";
            }
            if(TasteSalty.isChecked()) {
                //짠맛 체크5
                cnt++;
                if(ta1=="0") ta1="5";
                else if(ta2=="0") ta2="5";
                else ta3="5";
            }
            if(TasteFresh.isChecked()) {
                //상큼함 체크6
                cnt++;
                if(ta1=="0") ta1="6";
                else if(ta2=="0") ta2="6";
                else ta3="6";
            }
            if(cnt!=3){
                Toast.makeText(getApplicationContext(),"3개를 선택해주세요",Toast.LENGTH_LONG);
            }
            else{//아이디 세팅
                UpData task = new UpData();
                Intent intent = getIntent();
                final String idFromIntent = intent.getExtras().getString("userId");
                task.execute(idFromIntent/*IDTxt.getText().toString()*/,ta1,ta2,ta3);
            }
        }
        if(v.equals(BtnCancel)){
            //이전 화면으로 이동/
            finish();
        }
        /*if(v.equals(LogBtn)){
            //db에서 가져온 정보 화면에 세팅
            GetData task = new GetData();
            task.execute(IDTxt.getText().toString());
        }*/
    }
    private class UpData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = ProgressDialog.show(HashActivity.this, "please wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            progressDialog.dismiss();
            //resultTxt.setText(result);
            Log.d(TAG, "response - "+result);
            /*if(result ==null){
                resultTxt.setText(errorString);
            }
            else {
                mJsonString =result;
                resultTxt.setText(result);
            }*/

        }

        @Override
        protected String doInBackground(String... params) {
            String searchID = params[0];
            String taste1 = params[1];
            String taste2 = params[2];
            String taste3 = params[3];
            Log.d("id ",searchID);

            String serverURL = "http://13.59.52.83/UpTaste.php";
            String postParameters = "id="+searchID+"&taste1="+taste1+"&taste2="+taste2+"&taste3="+taste3;

            Log.d("post ", postParameters);

            try{
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG,"response code - "+responseStatusCode);



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

                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e){
                Log.d(TAG, "search data : error", e);
                errorString = e.toString();
                return null;
            }
        }
    }

    private class GetData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = ProgressDialog.show(HashActivity.this, "please wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            progressDialog.dismiss();
            //resultTxt.setText(result);
            Log.d(TAG, "response - "+result);
            if(result ==null){
                //resultTxt.setText(errorString);
            }
            else {
                mJsonString =result;
                showResult();
            }

        }

        @Override
        protected String doInBackground(String... params) {
            String searchID = params[0];

            String serverURL = "http://13.59.52.83/loadTaste.php";
            String postParameters = "id="+searchID;
            try{
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG,"response code - "+responseStatusCode);



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

                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e){
                Log.d(TAG, "search data : error", e);
                errorString = e.toString();
                return null;
            }
        }
    }

    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(Tag_JSON);

            JSONObject item = jsonArray.getJSONObject(0);

            String taste1 ;
            String taste2 ;
            String taste3 ;

            if(item.getString(Taste1)==null) taste1="0";
            else taste1 = item.getString(Taste1);
            if(item.getString(Taste2)==null) taste2="0";
            else taste2 = item.getString(Taste2);
            if(item.getString(Taste3)==null) taste3="0";
            else taste3 = item.getString(Taste3);


            //resultTxt.setText(taste1+" "+taste2+" "+taste3);
            int t1=Integer.parseInt(taste1), t2=Integer.parseInt(taste2), t3=Integer.parseInt(taste3);

            TasteSweet.setChecked(false); TasteSpicy.setChecked(false); TasteClean.setChecked(false);
            TasteNutty.setChecked(false); TasteSalty.setChecked(false); TasteFresh.setChecked(false);

            if(t1==1||t2==1||t3==1){TasteSweet.setChecked(true);}
            if(t1==2||t2==2||t3==2){TasteSpicy.setChecked(true);}
            if(t1==3||t2==3||t3==3){TasteClean.setChecked(true);}
            if(t1==4||t2==4||t3==4){TasteNutty.setChecked(true);}
            if(t1==5||t2==5||t3==5){TasteSalty.setChecked(true);}
            if(t1==6||t2==6||t3==6){TasteFresh.setChecked(true);}

        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }

}
