package com.example.easyreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ChangeInfo extends AppCompatActivity {
    Intent intent = new Intent();
    ArrayList<HashMap<String, Object>> arrayList;
    int pos = -1;
    ListViewAdapter2 listViewAdapter;

    private Handler mHandler1 = new Handler() {
        public void handleMessage(Message msg) {

        };
    };

    @Override
    protected void onResume() {
        super.onResume();
        ListView listView1=findViewById(R.id.change1);
        ListView listView2=findViewById(R.id.change2);
        listViewAdapter = new ListViewAdapter2(ChangeInfo.this, getData1());
        listView1.setAdapter(listViewAdapter);

        listViewAdapter = new ListViewAdapter2(ChangeInfo.this, getData2());
        listView2.setAdapter(listViewAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);

        setStatusBarFullTransparent();

        ListView listView1=findViewById(R.id.change1);
        ListView listView2=findViewById(R.id.change2);

        listViewAdapter = new ListViewAdapter2(ChangeInfo.this, getData1());
        listView1.setAdapter(listViewAdapter);

        listViewAdapter = new ListViewAdapter2(ChangeInfo.this, getData2());
        listView2.setAdapter(listViewAdapter);

        AdapterView.OnItemClickListener listViewListener1
                = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arg2 == 0) {
                    intent=new Intent(ChangeInfo.this,ChangeProfile.class);
                    startActivity(intent);
                }
                if (arg2 == 1) {
                    Toast.makeText(ChangeInfo.this, "学校名称只能由学校管理员修改！", Toast.LENGTH_SHORT).show();
                }
                if (arg2 == 2) {
                    Toast.makeText(ChangeInfo.this, "This is Item 2", Toast.LENGTH_SHORT).show();
                }
                if (arg2 == 3) {

                }
                if (arg2 == 4) {
                    intent = new Intent(ChangeInfo.this, ChangeInfo_sign.class);
                    startActivity(intent);
                }
            }
        };
        listView1.setOnItemClickListener(listViewListener1);
        AdapterView.OnItemClickListener listViewListener2
                = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arg2 == 0) {
                    intent = new Intent(ChangeInfo.this, ChangeInfo_name.class);
                    startActivity(intent);
                }
                if (arg2 == 1) {
                    Toast.makeText(ChangeInfo.this, "学号只能由学校管理员修改！", Toast.LENGTH_SHORT).show();
                }
            }
        };
        listView2.setOnItemClickListener(listViewListener2);

    }

    private ArrayList<HashMap<String, Object>> getData1() {
        arrayList = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> hashMap1 = new HashMap<String, Object>();
        HashMap<String, Object> hashMap2 = new HashMap<String, Object>();
        HashMap<String, Object> hashMap3 = new HashMap<String, Object>();
        HashMap<String, Object> hashMap4 = new HashMap<String, Object>();
        HashMap<String, Object> hashMap5 = new HashMap<String, Object>();
        HashMap<String, Object> hashMap6 = new HashMap<String, Object>();

        Context ctx = ChangeInfo.this;
        SharedPreferences share =ctx.getSharedPreferences("myshare", Context.MODE_APPEND);
        String qianming=share.getString("qianming","");
        String phone=share.getString("username","");


        hashMap1.put("itemName", "头像");
        hashMap1.put("value","点击修改头像");
        arrayList.add(hashMap1);
        hashMap2.put("itemName", "学校");
        hashMap2.put("value","南京大学金陵学院");
        arrayList.add(hashMap2);
        hashMap3.put("itemName", "性别");
        hashMap3.put("value","请选择");
        arrayList.add(hashMap3);
        hashMap5.put("itemName", "手机号");
        hashMap5.put("value",phone);
        arrayList.add(hashMap5);

        hashMap4.put("itemName", "个性签名");
        hashMap4.put("value",qianming);
        arrayList.add(hashMap4);

        return arrayList;
    }

    private ArrayList<HashMap<String, Object>> getData2() {
        Context ctx = ChangeInfo.this;
        SharedPreferences share =ctx.getSharedPreferences("myshare", Context.MODE_APPEND);
        String xuehao=share.getString("xuehao","");
        String nickname=share.getString("nickname","");

        arrayList = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> hashMap1 = new HashMap<String, Object>();
        HashMap<String, Object> hashMap2 = new HashMap<String, Object>();
        HashMap<String, Object> hashMap3 = new HashMap<String, Object>();
        HashMap<String, Object> hashMap4 = new HashMap<String, Object>();
        HashMap<String, Object> hashMap5 = new HashMap<String, Object>();
        HashMap<String, Object> hashMap6 = new HashMap<String, Object>();

        hashMap1.put("itemName", "姓名");
        hashMap1.put("value", nickname);
        arrayList.add(hashMap1);
        hashMap2.put("itemName", "学号");
        hashMap2.put("value", xuehao);
        arrayList.add(hashMap2);

        return arrayList;
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