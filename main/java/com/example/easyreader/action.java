package com.example.easyreader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class action extends AppCompatActivity {

    private String[] Author = new String[5];
    private String[] Name = new String[5];
    private String[] Time = new String[5];
    private String[] Imageids = new String[5];
    private String[] Professional = new String[5];
    private String[] Hot = new String[5];
    private String[] Memo = new String[5];
    private String[] Id = new String[5];
    private int[] Images = {R.mipmap.learning0, R.mipmap.learning1, R.mipmap.learning2, R.mipmap.learning3, R.mipmap.learning4, R.mipmap.learning5, R.mipmap.learning6, R.mipmap.learning7, R.mipmap.learning8, R.mipmap.learning9, R.mipmap.learning10};
    private ListView action_list;
    public ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getJson();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h_action);
        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < Name.length; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("images", Images[i]);
            item.put("name", Name[i]);
            item.put("create_time", Time[i]);
            //System.out.println(Time[i]);
            listitem.add(item);
        }
        SimpleAdapter simplead = new SimpleAdapter(this, listitem,
                R.layout.h_learning_item, new String[]{"title", "name", "images", "create_time"},
                new int[]{R.id.title, R.id.name, R.id.images, R.id.create_time});
        action_list = (ListView) findViewById(R.id.action_list);
        action_list.setAdapter(simplead);
        final Intent intent = new Intent(action.this, learning_detail.class);

        action_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        intent.putExtra("id", Id[position]);
                        intent.putExtra("seq", position);
                        startActivity(intent);
                        break;
                    case 1:
                        intent.putExtra("id", Id[position]);
                        intent.putExtra("seq", position);
                        startActivity(intent);
                        break;
                    case 2:
                        intent.putExtra("id", Id[position]);
                        intent.putExtra("seq", position);
                        startActivity(intent);
                        break;
                    case 3:
                        intent.putExtra("id", Id[position]);
                        intent.putExtra("seq", position);
                        startActivity(intent);
                        break;
                    case 4:
                        intent.putExtra("id", Id[position]);
                        intent.putExtra("seq", position);
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
                    URL url = new URL("http://192.168.2.130:3000/user/student/action_list");
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
                            String name = jsonObject.optString("name", null);
                            Name[i] = name;
                            String author = jsonObject.optString("author", null);
                            Author[i] = author;
                            String professional = jsonObject.optString("professional", null);
                            Professional[i] = professional;
                            String hot = jsonObject.optString("hot", null);
                            Hot[i] = hot;
                            String memo = jsonObject.optString("memo", null);
                            Memo[i] = memo;
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