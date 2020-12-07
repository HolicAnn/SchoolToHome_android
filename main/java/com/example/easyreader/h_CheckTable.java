package com.example.easyreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class h_CheckTable extends AppCompatActivity {
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String url=msg.obj.toString();

            webView = findViewById(R.id.table);
            webView.loadUrl(url);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            //webView.getSettings().setBuiltInZoomControls(true);
        };
    };
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h__check_table);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message msg = new Message();

                    //写入共享变量
                    Context ctx = h_CheckTable.this;
                    SharedPreferences share =ctx.getSharedPreferences("myshare", Context.MODE_APPEND);

                    String accStr=share.getString("data_id","");
                    //System.out.println(accStr);

                    String uurl=getString(R.string.Server_IP_Port)+"/user/student/get_timetable?"+accStr;
                    URL url = new URL(uurl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader isw = new InputStreamReader(in);
                    BufferedReader br = new BufferedReader(isw);
                    StringBuilder sb = new StringBuilder();
                    String result = "";
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        result = result + line;
                    }
                    System.out.println("--------------------------------------------------------------------------");
                    System.out.println(result);
                    br.close();
                    JsonUtils_login jsonUtils_login = new JsonUtils_login();
                    Login_state login_state = jsonUtils_login.parseLoginStateFromJson(result);
                    msg.obj = login_state.getData();
                    mHandler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}