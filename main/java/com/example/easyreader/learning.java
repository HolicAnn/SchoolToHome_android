package com.example.easyreader;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class learning extends AppCompatActivity {

    private String[] title = {"心有所信，方能行远", "使社会始终充满生机活力", "聚天下英才而用之", "一国两制”是完全行得通、办得到、得人心的"};

    private String[] name = {"\uD83C\uDDE8\uD83C\uDDF3青年大学习特辑", "\uD83C\uDDE8\uD83C\uDDF3青年大学习", "\uD83C\uDDE8\uD83C\uDDF3青年大学习", "\uD83C\uDDE8\uD83C\uDDF3青年大学习"};

    private String[] desc = {"2020-11-19 18:21", "2020-11-19 18:21", "2020-11-19 18:21", "2020-11-19 18:21"};

    private int[] imageids = {R.mipmap.haha1, R.mipmap.haha1, R.mipmap.haha1, R.mipmap.haha1};

    private ListView lt1;

    public ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();//

    private Context context;//

    private Mybaseadapter list_item;//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                new int[]{R.id.title, R.id.name, R.id.head, R.id.desc});

        lt1 = (ListView) findViewById(R.id.lt1);
        lt1.setAdapter(simplead);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*
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
                new int[]{R.id.title, R.id.name, R.id.head, R.id.desc});

        lt1 = (ListView) findViewById(R.id.lt1);
        lt1.setAdapter(simplead);
     */

    private void init() {
        list.clear();
        lt1 = (ListView) findViewById(R.id.lt1);
        list_item = new Mybaseadapter();
        lt1.setAdapter((ListAdapter) list_item);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    //服务端访问地址
                    Request request = new Request
                            .Builder()
                            .url("http://120.36.153.174:9080/CargolistApi/select").build();
                    Response response = client.newCall(request).execute();
                    //得到服务器返回的数据后，调用parseJSONWithJSONObject进行解析
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData) {
        if (jsonData != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonData);

                //获取数据中的code值，如果是0则正确
                String resultCode = jsonObject.getString("code");
                if (resultCode.equals("0")) {
                    //获取到json数据中里的data内容
                    JSONArray resultJsonArray = jsonObject.getJSONArray("data");
                    Log.d("MainActivity", "data+++" + resultJsonArray);
                    for (int i = 0; i < resultJsonArray.length(); i++) {
                        //循环遍历，获取json数据中data数组里的内容
                        JSONObject Object = resultJsonArray.getJSONObject(i);
                        Map<String, Object> map = new HashMap<String, Object>();
                        try {
                            String cargono = Object.getString("cargono");
                            String variety = Object.getString("variety");
                            String markno = Object.getString("markno");
                            String spec = Object.getString("spec");
                            String kgs = Object.getString("kgs");
                            String net = Object.getString("net");

                            map.put("cargono", cargono);
                            map.put("variety", variety);
                            map.put("markno", markno);
                            map.put("spec", spec);
                            map.put("kgs", kgs);
                            map.put("net", net);

                            //保存到ArrayList集合中
                            list.add(map);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    handler.sendEmptyMessageDelayed(1, 100);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    list_item.notifyDataSetChanged();
                    break;
            }
        }
    };


    //listview适配器
    public class Mybaseadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = new ViewHolder();

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.view_main, null);
                viewHolder.Cargono = (TextView) convertView.findViewById(R.id.tvCargono);
                viewHolder.Variety = (TextView) convertView.findViewById(R.id.tvVariety);
                viewHolder.Markno = (TextView) convertView.findViewById(R.id.tvMarkno);
                viewHolder.Spec = (TextView) convertView.findViewById(R.id.tvSpec);
                viewHolder.Kgs = (TextView) convertView.findViewById(R.id.tvKgs);
                viewHolder.Net = (TextView) convertView.findViewById(R.id.tvNet);

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.Cargono.setText("钢卷号：" + list.get(position).get("cargono").toString());
            viewHolder.Variety.setText("品种：" + list.get(position).get("variety").toString());
            viewHolder.Markno.setText("牌号：" + list.get(position).get("markno").toString());
            viewHolder.Spec.setText("规格型号：" + list.get(position).get("spec").toString());
            viewHolder.Kgs.setText("毛重：" + list.get(position).get("kgs").toString());
            viewHolder.Net.setText("净重：" + list.get(position).get("net").toString());

            return convertView;
        }
    }

    final static class ViewHolder {
        TextView Cargono;
        TextView Variety;
        TextView Markno;
        TextView Spec;
        TextView Kgs;
        TextView Net;
    }

    @Override
    public void onClick(View view) {

    }
}

