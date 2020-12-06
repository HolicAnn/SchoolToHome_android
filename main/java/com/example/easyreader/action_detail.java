package com.example.easyreader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

public class action_detail extends AppCompatActivity {

    private String id = "";
    private String _Name = "";
    private String _Title = "";
    private String _Cover = "";
    private String _Video = "";
    private String _QR = "";
    private String _Created_time = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h_learning_detail);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        getJson();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TextView learning_title = (TextView) findViewById(R.id.learning_title);
        learning_title.setText(_Title);

        TextView text_createtime = (TextView) findViewById(R.id.text_createtime);
        text_createtime.setText("发布时间：" + _Created_time);
    }

    private void getJson() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String _url = "http://192.168.2.130:3000/user/student/learning_detail" + "?id=" + id;
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
                            //System.out.println(name);
                            _Name = name;
                            String title = jsondata.optString("title", null);
                            System.out.println(title);
                            _Title = title;
                            String cover = jsondata.optString("cover", null);
                            //System.out.println(cover);
                            _Cover = cover;
                            String video = jsondata.optString("video", null);
                            //System.out.println(video);
                            _Video = video;
                            String QR = jsondata.optString("QR", null);
                            //System.out.println(QR);
                            _QR = QR;
                            String created_time = jsondata.optString("created_time", null);
                            //System.out.println(created_time);
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

    public static Bitmap getHttpBitmap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setConnectTimeout(0);
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
