package com.example.easyreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ListViewAdapter extends BaseAdapter implements AdapterView.OnClickListener {
    private Context context;
    private ArrayList<HashMap<String, Object>> data;
    Bean bean = null;
    public ListViewAdapter(Context context, ArrayList<HashMap<String,Object>> data){
        this.context=context;
        this.data=data;
    }
    public int getCount(){
        return data.size();
    }
    public Object getItem(int position){
        return data.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(int position, View holder, ViewGroup parent){
        if(holder==null){
            bean=new Bean();
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            holder=layoutInflater.inflate(R.layout.setting_item,null);
            bean.imageView=(ImageView)holder.findViewById(R.id.setting_image);
            bean.itemName=(TextView)holder.findViewById(R.id.itemName);
            bean.enter=(ImageView)holder.findViewById(R.id.enter);
            holder.setTag(bean);
        }
        else{
            bean=(Bean)holder.getTag();
        }
        bean.imageView.setBackgroundResource((int)data.get(position).get("image"));
        bean.itemName.setText((String)data.get(position).get("itemName"));
        //bean.singer.setText((String)data.get(position).get("singer"));
        bean.enter.setOnClickListener(this);
        bean.enter.setTag(position);
        return holder;
    }
    @Override
    public void onClick(View v){
        //((JiaXiaoTong)context).click(v);
    }

}
