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

public class ListViewAdapter2 extends BaseAdapter implements AdapterView.OnClickListener {
    private Context context;
    private ArrayList<HashMap<String, Object>> data;
    Bean2 bean = null;
    public ListViewAdapter2(Context context, ArrayList<HashMap<String,Object>> data){
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
            bean=new Bean2();
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            holder=layoutInflater.inflate(R.layout.changeinfo_item,null);
            bean.itemName=(TextView)holder.findViewById(R.id.itemName);
            bean.enter=(ImageView)holder.findViewById(R.id.enter);
            bean.value=(TextView)holder.findViewById(R.id.change_value);
            holder.setTag(bean);
        }
        else{
            bean=(Bean2)holder.getTag();
        }
        bean.itemName.setText((String)data.get(position).get("itemName"));
        bean.value.setText((String)data.get(position).get("value"));
        return holder;
    }
    @Override
    public void onClick(View v){
    }

}
