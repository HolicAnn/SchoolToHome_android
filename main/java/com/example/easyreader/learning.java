package com.example.easyreader;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
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

public class learning extends AppCompatActivity {

    private String[] Title = new String[11];// = {"心有所信，方能行远", "使社会始终充满生机活力", "聚天下英才而用之", "一国两制”是完全行得通、办得到、得人心的"};

    private String[] Name = new String[11];// = {"\uD83C\uDDE8\uD83C\uDDF3青年大学习特辑", "\uD83C\uDDE8\uD83C\uDDF3青年大学习", "\uD83C\uDDE8\uD83C\uDDF3青年大学习", "\uD83C\uDDE8\uD83C\uDDF3青年大学习"};

    private String[] Time = new String[11];// = {"2020-11-19 18:21", "2020-11-19 18:21", "2020-11-19 18:21", "2020-11-19 18:21"};

    private String[] Imageids = new String[11];// = {R.mipmap.haha1, R.mipmap.haha1, R.mipmap.haha1, R.mipmap.haha1};

    private ListView lt1;

    public ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getJson();
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h_learning);
        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < Name.length; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("title", Title[i]);
            item.put("images", Imageids[i]);
            item.put("name", Name[i]);
            item.put("create_time", Time[i]);
            System.out.println(Time[i]);
            listitem.add(item);
        }
        SimpleAdapter simplead = new SimpleAdapter(this, listitem,
                R.layout.h_learning_item, new String[]{"title", "name", "images", "create_time"},
                new int[]{R.id.title, R.id.name, R.id.images, R.id.create_time});
        lt1 = (ListView) findViewById(R.id.lt1);
        lt1.setAdapter(simplead);
    }

    private void getJson() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.2.130:3000/user/student/learning_list");
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
                    System.out.println("--------------------------------------------------------------------------");
                    try {
                        JSONObject jsonObjectALL = new JSONObject(result);
                        JSONArray jsonArray = jsonObjectALL.getJSONArray("list");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String _id = jsonObject.optString("_id", null);
                            //System.out.println(_id);
                            String name = jsonObject.optString("name", null);
                            //System.out.println(name);
                            Name[i] = name;
                            String title = jsonObject.optString("title", null);
                            System.out.println(title);
                            Title[i] = title;
                            String cover = jsonObject.optString("cover", null);
                            //System.out.println(cover);
                            Imageids[i] = cover;
                            String video = jsonObject.optString("video", null);
                            //System.out.println(video);
                            String QR = jsonObject.optString("QR", null);
                            //System.out.println(QR);
                            String created_time = jsonObject.optString("created_time", null);
                            //System.out.println(created_time);
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

    public void test_OnCreate(Bundle savedInstanceState) {
        String[] title = {"心有所信，方能行远", "使社会始终充满生机活力", "聚天下英才而用之", "一国两制”是完全行得通、办得到、得人心的"};
        String[] name = {"\uD83C\uDDE8\uD83C\uDDF3青年大学习特辑", "\uD83C\uDDE8\uD83C\uDDF3青年大学习", "\uD83C\uDDE8\uD83C\uDDF3青年大学习", "\uD83C\uDDE8\uD83C\uDDF3青年大学习"};
        String[] desc = {"2020-11-19 18:21", "2020-11-19 18:21", "2020-11-19 18:21", "2020-11-19 18:21"};
        int[] imageids = {R.mipmap.haha1, R.mipmap.haha1, R.mipmap.haha1, R.mipmap.haha1};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h_learning);
        List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < name.length; i++) {
            Map<String, Object> listem = new HashMap<String, Object>();
            listem.put("title", title[i]);
            listem.put("head", imageids[i]);
            listem.put("name", name[i]);
            listem.put("desc", desc[i]);
            listems.add(listem);
        }
        SimpleAdapter simplead = new SimpleAdapter(this, listems,
                R.layout.h_learning_item, new String[]{"title", "name", "head", "desc"},
                new int[]{R.id.title, R.id.name, R.id.images, R.id.create_time});

        lt1 = (ListView) findViewById(R.id.lt1);
        lt1.setAdapter(simplead);
    }

    public void getJson_1(String result) throws JSONException {
        try {
            JSONObject jsonObject = new JSONObject(result);
            String msg = jsonObject.optString("msg", null);
            System.out.println(msg);
            int state = jsonObject.optInt("state", 0);
            System.out.println(state);
            String list = jsonObject.optString("list", null);
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        {
        "state": 200,
        "msg": "获取列表成功",
        "list":"  "
        }
        */
    }

    public void getJson_2(String result) throws JSONException {
        try {
            JSONObject jsonObjectALL = new JSONObject(result);
            JSONArray jsonArray = jsonObjectALL.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String _id = jsonObject.optString("_id", null);
                System.out.println(_id);
                String name = jsonObject.optString("name", null);
                System.out.println(_id);
                String title = jsonObject.optString("title", null);
                System.out.println(_id);
                String cover = jsonObject.optString("cover", null);
                System.out.println(_id);
                String video = jsonObject.optString("video", null);
                System.out.println(_id);
                String QR = jsonObject.optString("QR", null);
                System.out.println(_id);
                String created_time = jsonObject.optString("created_time", null);
                System.out.println(_id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        {
        "state": 200,
        "msg": "获取列表成功",
        "list": [
            {
             "_id": "5fb6471e26813c2dc8473dd6",
            "name": "🇨🇳“青年大学习”特辑",
            "title": "心有所信，方能行远",
            "cover": "http://img.cyol.com/img/news/attachement/jpg/site2/20200713/IMG484d7ea271a15446058243.jpg",
            "video": "http://dxxsv.cyol.com/9dxx11.mp4",
            "QR": "http://h5.cyol.com/special/daxuexi/9fudanhui11/images/erweima.png",
            "created_time": "2020-11-19 18:21"
            },
            {
            "_id": "5fb6473d26813c2dc8473dd7",
            "name": "🇨🇳青年大学习",
            "title": "使社会始终充满生机活力",
            "cover": "http://img.cyol.com/img/news/attachement/jpg/site2/20200706/IMG484d7ea271a15440039182.jpg",
            "video": "http://dxxsv.cyol.com/9dxx10.mp4",
            "QR": "http://h5.cyol.com/special/daxuexi/9aabgtcgs10/images/erweima.png",
            "created_time": "2020-11-19 18:21"
            }
          ]
        }
        */
    }
}