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
import org.json.JSONException;
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

public class news extends AppCompatActivity {

    private String[] Id = new String[4];
    private String[] Msg = new String[4];
    private String[] Title = new String[4];
    private String[] Time = new String[4];
    private ListView new_list;
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
        setContentView(R.layout.news);
        setStatusBarFullTransparent();
        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < Msg.length; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("new_title", Title[i]);
            item.put("new_msg", Msg[i]);
            item.put("news_time", Time[i]);
            listitem.add(item);
        }
        SimpleAdapter simplead = new SimpleAdapter(this, listitem,
                R.layout.news_item, new String[]{"new_title", "new_msg", "news_time"},
                new int[]{R.id.new_title, R.id.new_msg, R.id.news_time});
        new_list = (ListView) findViewById(R.id.new_list);
        new_list.setAdapter(simplead);
        final Intent intent = new Intent(news.this, news_detail.class);

        new_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        intent.putExtra("id", Id[position]);
                        startActivity(intent);
                        break;
                    case 1:
                        intent.putExtra("id", Id[position]);
                        startActivity(intent);
                        break;
                    case 2:
                        intent.putExtra("id", Id[position]);
                        startActivity(intent);
                        break;
                    case 3:
                        intent.putExtra("id", Id[position]);
                        startActivity(intent);
                        break;
                    case 4:
                        intent.putExtra("id", Id[position]);
                        startActivity(intent);
                        break;
                }
            }
        });

    }

    private void getJson() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //URL url = new URL("http://172.20.10.2:3000/user/student/learning_list");
                    URL url = new URL(getString(R.string.Server_IP_Port) + "/user/student/news_list");
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
                            String _id = jsonObject.optString("_id", null);
                            Id[i] = _id;
                            String msg = jsonObject.optString("msg", null);
                            Msg[i] = msg.substring(0,22);
                            String title = jsonObject.optString("title", null);
                            Title[i] = title;
                            String created_time = jsonObject.optString("created_time", null);
                            Time[i] = created_time;
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