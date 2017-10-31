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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
 * Created by inthe on 2017-10-27.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.idInput) EditText id;
    @BindView(R.id.pwd) EditText pwd;
    @BindView(R.id.loginBtn) Button loginBtn;
    @BindView(R.id.linkToRegister) TextView linkToReg;

    String idString, nickString, pwdString;
    int gender, age, taste1, taste2, taste3;

    String stringJSON = null;
    private static final String TAG_JSON = "codingstory";


    @Override
    public void onCreate(Bundle savedinstanceState)  {
        super.onCreate(savedinstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        // 로그인 버튼 클릭 시
        loginBtn.setOnClickListener(new View.OnClickListener()    {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Login");

                idString = id.getText().toString();
                pwdString = pwd.getText().toString();

                UserLogin task = new UserLogin();
                task.execute(idString, pwdString);
            }
        });

        // 회원가입하기 링크 눌렀을 시
        linkToReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 페이지로 이동
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    // 서버의 DB를 읽어 올 작업
    class UserLogin extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(LoginActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        /* 서버에서 응답 */
            Log.e("RECV DATA", result);
            progressDialog.dismiss();
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(LoginActivity.this);

            // 비밀번호가 틀린 경우
            if (result.equals("Empty")) {
                Log.e("RESULT", "존재하지 않는 아이디거나 비밀번호가 일치하지 않습니다.");
                alertBuilder
                        .setTitle("로그인 실패")
                        .setMessage("존재하지 않는 아이디거나 비밀번호가 일치하지 않습니다.")
                        .setCancelable(true)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //finish();
                            }
                        });
                AlertDialog dialog = alertBuilder.create();
                dialog.show();

            } else if (result.length() > 8) {
                stringJSON = result;
                showResult();

                Log.e("RESULT", "성공적으로 로그인 되었습니다!");
                Log.e("JSON 결과", "닉넴:" + nickString + " / 성:" + gender + " / 나이:" + age + " / 태그:" + taste1 + taste2 + taste3);
                alertBuilder
                        .setTitle("알림")
                        .setMessage("성공적으로 로그인 되었습니다!")
                        .setCancelable(true)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // TODO: 인텐트 전달
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("userId", idString);
                                intent.putExtra("userNick", nickString);
                                intent.putExtra("userGender", gender);
                                intent.putExtra("userAge", age);
                                intent.putExtra("userTaste1", taste1);
                                intent.putExtra("userTaste2", taste2);
                                intent.putExtra("userTaste3", taste3);

                                startActivity(intent);
                                finish();
                            }
                        });
                AlertDialog dialog = alertBuilder.create();
                dialog.show();
            } else {
                Log.e("RESULT", "에러 발생! ERRCODE = " + data);
                alertBuilder
                        .setTitle("알림")
                        .setMessage("로그인 중 에러가 발생했습니다! errcode : " + data)
                        .setCancelable(true)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //finish();
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

            String serverURL = "http://13.59.52.83/login.php";
            String postParameters = "id=" + dbId + "&pwd=" + dbPwd;

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                //httpURLConnection.setRequestProperty("content-type", "application/json");
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

                inputStream.close();
                bufferedReader.close();

                return sb.toString();

            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                return null;
            }

        }
    }

    public void showResult()    {
        // json data 읽어오기
        try {
            JSONObject jsonObject = new JSONObject(stringJSON);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);


            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                nickString = item.getString("nick");
                gender = Integer.parseInt(item.getString("gender"));
                age = Integer.parseInt(item.getString("age"));
                taste1 = Integer.parseInt(item.getString("taste1"));
                taste2 = Integer.parseInt(item.getString("taste2"));
                taste3 = Integer.parseInt(item.getString("taste3"));

            }

        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }

}
