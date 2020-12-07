package com.example.easyreader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.Integer.parseInt;

public class action_detail extends AppCompatActivity {

    private String id = "";
    private String _Title = "";//标题
    private int seq;
    private String _Professional = "";//院系
    private String _Tribe = "";//部落
    private String _Memo = "";//活动简介

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h_action_detail);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        seq = parseInt(intent.getStringExtra("seq"));
        getJson();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TextView title = (TextView) findViewById(R.id.action_detail_title);
        title.setText(_Title);

        TextView memo = (TextView) findViewById(R.id.action_detail_memo);
        memo.setText(_Memo);

        TextView learning_title = (TextView) findViewById(R.id.learning_title);
        learning_title.setText(_Title);

        TextView professional = (TextView) findViewById(R.id.action_yuanxi);
        professional.setText(_Professional);

        TextView tribe = (TextView) findViewById(R.id.action_tribe);
        tribe.setText(_Tribe);

        ImageView imageView=(ImageView)findViewById(R.id.action_detail_image);
        if(seq==0){
            imageView.setImageResource(R.mipmap.cover_0);
        }else if(seq==1){
            imageView.setImageResource(R.mipmap.cover1);
        }else if(seq==2){
            imageView.setImageResource(R.mipmap.cover2);
        }else if(seq==3){
            imageView.setImageResource(R.mipmap.cover3);
        }else if(seq==4){
            imageView.setImageResource(R.mipmap.cover4);
        }
    }

    private void getJson() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String _url = "http://172.20.10.2:3000/user/student/action_detail" + "?id=" + id;
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
                            String name = jsondata.optString("name", null);
                            _Title = name;
                            System.out.println(name);
                            String professional = jsondata.optString("professional", null);
                            _Professional = professional;
                            String memo = jsondata.optString("memo", null);
                            _Memo = memo;
                            String tribe = jsondata.optString("tribe", null);
                            _Tribe = tribe;
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
