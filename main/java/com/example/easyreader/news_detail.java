package com.example.easyreader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//import butterknife.BindView;

public class news_detail extends AppCompatActivity {

    private String id = "";
    private String _Title = "";
    private String _Msg = "";
    private String _Created_time = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        getJson();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TextView text_title = (TextView) findViewById(R.id.new_detail_title);
        text_title.setText(_Title);

        TextView text_createtime = (TextView) findViewById(R.id.new_detail_time);
        text_createtime.setText(_Created_time);

        TextView text_msg = (TextView) findViewById(R.id.new_detail_msg);
        text_msg.setText(_Msg);
    }

    private void getJson() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //String _url = "http://172.20.10.2:3000/user/student/learning_detail" + "?id=" + id;
                    String _url = getString(R.string.Server_IP_Port) + "/user/student/news_detail" + "?id=" + id;
                    URL url = new URL(_url);
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
                    br.close();
                    //System.out.println("--------------------------------------------------------------------------");
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String data = jsonObject.optString("data", null);
                        //System.out.println(data);
                        try {
                            JSONObject jsondata = new JSONObject(data);
                            String _id = jsondata.optString("_id", null);
                            id = _id;
                            String msg = jsondata.optString("msg", null);
                            _Msg = msg;
                            System.out.println("--------------");
                            System.out.println(_Msg);
                            String title = jsondata.optString("title", null);
                            _Title = title;
                            String created_time = jsondata.optString("created_time", null);
                            _Created_time = created_time;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
