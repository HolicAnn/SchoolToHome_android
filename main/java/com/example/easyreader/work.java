package com.example.easyreader;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class work extends AppCompatActivity {

    private String[] Classes = new String[4];
    private String[] Memo = new String[4];
    private String[] Title = new String[4];
    private String[] Time = new String[4];
    private ListView work_list;
    public ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getJson();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work);
        setStatusBarFullTransparent();
        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < Classes.length; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("work_classes", Classes[i]);
            item.put("work_memo", Memo[i]);
            item.put("work_title", Title[i]);
            item.put("work_commit_time", Time[i]);
            listitem.add(item);
        }
        SimpleAdapter simplead = new SimpleAdapter(this, listitem,
                R.layout.work_item, new String[]{"work_classes", "work_memo", "work_title","work_commit_time"},
                new int[]{R.id.work_classes, R.id.work_memo, R.id.work_title,R.id.work_commit_time});
        work_list = (ListView) findViewById(R.id.work_list);
        work_list.setAdapter(simplead);
    }

    private void getJson() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //URL url = new URL("http://172.20.10.2:3000/user/student/learning_list");
                    URL url = new URL(getString(R.string.Server_IP_Port) + "/user/student/work_list");
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
                        JSONObject jsonObjectALL = new JSONObject(result);
                        JSONArray jsonArray = jsonObjectALL.getJSONArray("list");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String title = jsonObject.optString("title", null);
                            Title[i] = title;
                            String memo = jsonObject.optString("memo", null);
                            Memo[i] = memo;
                            String classes = jsonObject.optString("classes", null);
                            Classes[i] = "所属班级:"+classes;
                            String commit_time = jsonObject.optString("commit_time", null);
                            Time[i] = "截止日期:"+commit_time;
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

    protected void setStatusBarFullTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}