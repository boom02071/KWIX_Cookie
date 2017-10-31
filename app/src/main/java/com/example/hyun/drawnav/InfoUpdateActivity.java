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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
 * Created by inthe on 2017-09-26.
 */


public class InfoUpdateActivity extends AppCompatActivity {
    private static final String TAG = "InfoUpdateActivity";

    @BindView(R.id.profileImage) ImageView profileImage;
    //@BindView(R.id.nicknameText) TextView nicknameText;
    @BindView(R.id.nicknameView) TextView nicknameView;
    @BindView(R.id.idView) TextView idView;
    @BindView(R.id.pwdToChange) EditText pwdToChange;
    @BindView(R.id.pwdToSure) EditText pwdToSure;       //비밀번호 아래부터는 추후 추가할것
    @BindView(R.id.radioGroup) RadioGroup radioGroup;
    @BindView(R.id.genderMale) RadioButton genMale;
    @BindView(R.id.genderFemale) RadioButton genFemale;
    @BindView(R.id.ageSpinner) Spinner ageSpinner;
    @BindView(R.id.listOfSpinner) TextView listOfSpinner;
    @BindView(R.id.saveButton) Button saveBtn;
    @BindView(R.id.cancelButton) Button cancelBtn;

    String idString, nickString, pwdString, pwdSureString;
    int gender, age, taste1, taste2, taste3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);       //액티비티 생성 시 저장된 데이터를 Bundle 클래스인 savedInstanceState에 담아옴
        setContentView(R.layout.activity_infoupdate);
        ButterKnife.bind(this);
        saveBtn.setEnabled(true);               //저장버튼 활성화

        // Intent에서 아이디, 닉네임 불러오기
        Intent intent = getIntent();
        String nickIntent = intent.getExtras().getString("userNick");
        String idIntent = intent.getExtras().getString("userId");
        //Log.d("", "nickIntent = " + nickIntent);
        nicknameView.setText(nickIntent);
        idView.setText(idIntent);

        Log.d("***", "--- idIntent : " + idIntent);

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


        // 변경 저장버튼 클릭 시
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBtn.setEnabled(false);

                //입력된 정보 받아오기
                idString = idView.getText().toString();
                pwdString = pwdToChange.getText().toString();
                pwdSureString = pwdToSure.getText().toString();
                if (genMale.isChecked() == true) gender = 0;         //남자면 0
                else if (genFemale.isChecked() == true) gender = 1;  //여자면 1
                else gender = -1;
                String ageStr = (String) ageSpinner.getAdapter().getItem(ageSpinner.getSelectedItemPosition());
                //age = Integer.parseInt(ageStr.replaceAll("[0-9]", "")); // '?0대'에서 숫자만 추출
                age = Integer.parseInt(ageStr);

                //pwdToChange와 pwdToSure이 틀리면 오류, 맞으면 저장
                if (!validate(pwdString, pwdSureString, gender)) {
                    Toast.makeText(getBaseContext(), "로그인 실패", Toast.LENGTH_LONG);
                    saveBtn.setEnabled(true);
                } else if (!pwdToChange.getText().toString().equals(pwdToSure.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "비밀번호 틀려요", Toast.LENGTH_LONG).show();
                    saveBtn.setEnabled(true);
                } else {
                    saveBtn.setEnabled(false);   //저장버튼 비활성화

                    UserInfoUpdate task = new UserInfoUpdate();
                    task.execute(idString, pwdString, Integer.toString(gender), Integer.toString(age));

                    finish();
                }
            }
        });

    }


    // 입력된 내용 예외처리
    public boolean validate(String pwdString, String pwdSureString, int gender)   {
        boolean valid = true;

        if(pwdString.isEmpty() || pwdString.length() < 4 || pwdString.length() > 10)    {
            pwdToChange.setError("비밀번호는 4글자에서 10글자 사이");
            valid = false;
        }   else {
            pwdToChange.setError(null);
        }

        if(gender == -1)    {
            Toast.makeText(getBaseContext(), "성별을 입력하세요", Toast.LENGTH_LONG);
            valid = false;
        }

        return valid;
    }



    // 서버의 DB를 읽어 올 작업
    class UserInfoUpdate extends AsyncTask<String, Void, String>  {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(InfoUpdateActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();            //mTextViewResult.setText(result);
            Log.e("RECV DATA",result);
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(InfoUpdateActivity.this);

            if(result.equals("SQL문 처리 성공"))
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
            String dbGender = (String) params[2];
            String dbAge = (String) params[3];

            /* 인풋 파라메터값 생성 */
            String serverURL = "http://13.59.52.83/infoUpdate.php";
            String postParameters = "id=" + dbId + "&pwd=" + dbPwd
                                    + "&gender=" + dbGender + "&age=" + dbAge;
            Log.d("2", "doInBackground 들어옴" + postParameters);

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                Log.d("3", "서버랑 연결됨");


                /* 안드로이드 -> 서버 파라메터값 전달 */
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
                Log.d("4", "파라미터값 전달됨");

                /* 서버 -> 안드로이드 파라메터값 전달 */
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
                Log.d(TAG, "UpdateData: Error ", e);
                return new String("Error: " + e.getMessage());
            }
        }
    }
}
