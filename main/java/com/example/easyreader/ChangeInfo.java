package com.example.easyreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class ChangeInfo extends AppCompatActivity {
    Intent intent = new Intent();
    ArrayList<HashMap<String, Object>> arrayList;
    int pos = -1;
    ListViewAdapter2 listViewAdapter;

    private Handler mHandler1 = new Handler() {
        public void handleMessage(Message msg) {

        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);

        ListView listView1=findViewById(R.id.change1);
        ListView listView2=findViewById(R.id.change2);

        listViewAdapter = new ListViewAdapter2(ChangeInfo.this, getData1());
        listView1.setAdapter(listViewAdapter);

        listViewAdapter = new ListViewAdapter2(ChangeInfo.this, getData2());
        listView2.setAdapter(listViewAdapter);


    }

    private ArrayList<HashMap<String, Object>> getData1() {
        arrayList = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> hashMap1 = new HashMap<String, Object>();
        HashMap<String, Object> hashMap2 = new HashMap<String, Object>();
        HashMap<String, Object> hashMap3 = new HashMap<String, Object>();
        HashMap<String, Object> hashMap4 = new HashMap<String, Object>();
        HashMap<String, Object> hashMap5 = new HashMap<String, Object>();
        HashMap<String, Object> hashMap6 = new HashMap<String, Object>();

        hashMap1.put("itemName", "头像");
        arrayList.add(hashMap1);
        hashMap2.put("itemName", "学校");
        arrayList.add(hashMap2);
        hashMap3.put("itemName", "性别");
        arrayList.add(hashMap3);
        hashMap5.put("itemName", "手机号");
        arrayList.add(hashMap5);
        hashMap4.put("itemName", "个性签名");
        hashMap4.put("value","123");
        arrayList.add(hashMap4);

        return arrayList;
    }

    private ArrayList<HashMap<String, Object>> getData2() {
        arrayList = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> hashMap1 = new HashMap<String, Object>();
        HashMap<String, Object> hashMap2 = new HashMap<String, Object>();
        HashMap<String, Object> hashMap3 = new HashMap<String, Object>();
        HashMap<String, Object> hashMap4 = new HashMap<String, Object>();
        HashMap<String, Object> hashMap5 = new HashMap<String, Object>();
        HashMap<String, Object> hashMap6 = new HashMap<String, Object>();

        hashMap1.put("itemName", "姓名");
        arrayList.add(hashMap1);
        hashMap2.put("itemName", "学号");
        arrayList.add(hashMap2);

        return arrayList;
    }
}