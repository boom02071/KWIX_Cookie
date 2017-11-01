package com.example.hyun.drawnav;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.data;


/**
 * Created by inthe on 2017-10-03.
 */

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    @BindView(R.id.idInput) EditText id;
    @BindView(R.id.nickname) EditText nickname;
    @BindView(R.id.pwd) EditText pwd;
    @BindView(R.id.pwdSure) EditText pwdSure;
    //@BindView(R.id.radioGroup) RadioGroup radioGroup;
    @BindView(R.id.genderMale) RadioButton genMale;
    @BindView(R.id.genderFemale) RadioButton genFemale;
    @BindView(R.id.ageSpinner) Spinner ageSpinner;
    @BindView(R.id.listOfSpinner) TextView listOfSpinner;
    @BindView(R.id.regBtn) Button regBtn;

    String idString, nickString, pwdString;
    int gender, age, taste1, taste2, taste3;


    @Override
    public void onCreate(Bundle savedinstanceState)  {
        super.onCreate(savedinstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        // <------ 스피너 ------>
        String[] str = getResources().getStringArray(R.array.listSpinner);      //str 배열에 listSpinner의 항목들 저장
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, str);
        //배열adapter 생성
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //선택 항목들이 나타날 때 어떤 레이아웃 사용할 지 지정
        ageSpinner.setAdapter(adapter);     //spinner와 adapter 매핑해주기

        ageSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    //OnItem~Listener 클래스 사용 시 onItemSelected와 onNothingSelected 필수적으로 정의해야 함
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String res = "";            //Spinner에 선택된 값이 저장될 string 변수
                        if(ageSpinner.getSelectedItemPosition()>0)  {
                            res = (String)ageSpinner.getAdapter().getItem(ageSpinner.getSelectedItemPosition());
                            // 선택된 spinner를 가져오기
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        //onNothingSelected는 특별한 명령이 없더라도 반드시 재정의 해줘야 함
                    }
                }
        );   // <------ 스피너 ------>


        // 계정생성하기 버튼 클릭 시
        regBtn.setOnClickListener(new View.OnClickListener()    {
            @Override
            public void onClick(View v) {
                regBtn.setEnabled(false);

                if(!validate()) {       //입력에 오류가 있다면(비어있거나 글자 수 안맞거나)
                    Toast.makeText(getBaseContext(), "회원가입 실패", Toast.LENGTH_LONG);
                    regBtn.setEnabled(true);
                }

                else {
                    regBtn.setEnabled(false);   //계정생성버튼 비활성화

                    //현재 화면에 입력된 정보 가져오기
                    idString = id.getText().toString();
                    nickString = nickname.getText().toString();
                    if (genMale.isChecked() == true) gender = 0;         //남자면 0
                    else if (genFemale.isChecked() == true) gender = 1;  //여자면 1
                    String ageStr = (String) ageSpinner.getAdapter().getItem(ageSpinner.getSelectedItemPosition());
                    //age = Integer.parseInt(ageStr.replaceAll("[0-9]", "")); // '?0대'에서 숫자만 추출
                    age = Integer.parseInt(ageStr);

                    UserSignup task = new UserSignup();
                    task.execute(idString, pwdString, nickString, Integer.toString(gender), Integer.toString(age));

                    //TODO: 여기 인텐트 값 바꿔주세영
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public boolean validate()   {       //입력 오류 판별
        boolean valid = true;           //오류가 생기면 valid 값을 false로 변경

        nickString = nickname.getText().toString();
        idString = id.getText().toString();
        pwdString = pwd.getText().toString();
        String pwdSureString = pwdSure.getText().toString();


        if(idString.isEmpty() || idString.length() < 5) {   //*여유 있으면 id 기준도 따로 있는지 찾아보기
            id.setError("최소 5글자 이상 입력하세요");
            valid = false;
        }   else {
            id.setError(null);
        }

        /* -----  닉네임 최소 3글자 설정 --> 필요 없을 것 같아 제외  -----
        if(nickString.isEmpty() || nickString.length() < 3) {
            nickname.setError("최소 3글자 이상 입력하세요");
            valid = false;
        }   else    {
            nickname.setError(null);
        }*/

        if(pwdString.isEmpty() || pwdString.length() < 4 || pwdString.length() > 10)    {
            pwd.setError("비밀번호는 4글자에서 10글자 사이");
            valid = false;
        }   else {
            pwd.setError(null);
        }

        if(!pwdString.equals(pwdSureString))    {
            pwdSure.setError("입력한 비밀번호가 다릅니다");
            valid = false;
        }   else    {
            pwdSure.setError(null);
        }
        return valid;
    }


    // 서버의 DB를 읽어 올 작업
    class UserSignup extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(RegisterActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        /* 서버에서 응답 */
            Log.e("RECV DATA",result);
            progressDialog.dismiss();
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(RegisterActivity.this);

            if(result.equals("0"))
            {
                Log.e("RESULT","성공적으로 처리되었습니다!");
                alertBuilder
                        .setTitle("알림")
                        .setMessage("성공적으로 등록되었습니다!")
                        .setCancelable(true)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                AlertDialog dialog = alertBuilder.create();
                dialog.show();
            }
            else
            {
                Log.e("RESULT","에러 발생! ERRCODE = " + data);
                alertBuilder
                        .setTitle("알림")
                        .setMessage("등록중 에러가 발생했습니다! errcode : "+ data)
                        .setCancelable(true)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                AlertDialog dialog = alertBuilder.create();
                dialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String dbId = (String) params[0];
            String dbPwd = (String) params[1];
            String dbNick = (String) params[2];
            String dbGender = (String) params[3];
            String dbAge = (String) params[4];

            String serverURL = "http://13.59.52.83/register.php";
            String postParameters = "id=" + dbId + "&pwd=" + dbPwd + "&nick=" + dbNick
                    + "&gender=" + dbGender + "&age=" + dbAge;

            try {
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
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString();


            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                return new String("Error: " + e.getMessage());
            }


        }
    }
}



