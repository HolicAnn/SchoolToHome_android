package com.example.easyreader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link m_MyInfoAndSettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class m_MyInfoAndSettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView tx1,tx2,tx3,tx4,tx5;
    private TextClock mTextClock;

    public m_MyInfoAndSettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment m_MyInfoAndSettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static m_MyInfoAndSettingFragment newInstance(String param1, String param2) {
        m_MyInfoAndSettingFragment fragment = new m_MyInfoAndSettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_info_and_setting, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initSettingItems();
        //uurl ="http://123.56.151.219:8888/user/user/login?username="+account+"&password="+password;
        //status.setText(uurl);
        //System.out.println("--------------------------------------------------------------------------");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message msg = new Message();
                    Context ctx = getActivity();
                    SharedPreferences share =ctx.getSharedPreferences("myshare", Context.MODE_APPEND);
                    SharedPreferences.Editor editor = share.edit();
                    editor.putString("data_id", "?data_id=5fbce52d29c82964fbb33089");
                    editor.commit();

                    String accStr=share.getString("data_id","");
                    //System.out.println(accStr);

                    String uurl=getString(R.string.Server_IP_Port)+"/user/user/detail"+accStr;
                    URL url = new URL(uurl);
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
                    System.out.println("--------------------------------------------------------------------------");
                    System.out.println(result);
                    br.close();
                    //JsonUtils_login jsonUtils_login=new JsonUtils_login();
                    //msg.obj=jsonUtils_login.parseLoginStateFromJson(result);
                    //mHandler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        tx1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tx2.setOnClickListener(new View.OnClickListener() {//背景音乐
            @Override
            public void onClick(View view) {

            }
        });
        tx3.setOnClickListener(new View.OnClickListener() {//主题
            @Override
            public void onClick(View view) {


            }
        });
        tx4.setOnClickListener(new View.OnClickListener() {//关于
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),About.class);
                //intent.setClass();
                startActivity(intent);
            }
        });
        tx5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void initSettingItems(){
        tx1=getView().findViewById(R.id.SettingItem1);
        tx2=getView().findViewById(R.id.SettingItem2);
        tx3=getView().findViewById(R.id.SettingItem3);
        tx4=getView().findViewById(R.id.SettingItem4);
        tx5=getView().findViewById(R.id.SettingItem5);
        mTextClock = (TextClock)getView().findViewById(R.id.textclock);
        // 设置12时制显示格式
        mTextClock.setFormat12Hour("MMMM dd,h:mmaa");

        // 设置24时制显示格式
        //mTextClock.setFormat24Hour("yyyy-MM-dd hh:mm, EEEE");
    }
    public void setMenuVisibility(boolean menuVisible){
        super.setMenuVisibility(menuVisible);
        if(this.getView()!=null){
            this.getView().setVisibility(menuVisible?View.VISIBLE:View.GONE);
        }
    }
}