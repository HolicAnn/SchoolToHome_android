package com.example.easyreader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

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

    private ListView listview;

    private TextView tx1, tx2, tx3, tx4, tx5, nickname,qm;
    private TextClock mTextClock;

    private Button loginout;

    Intent intent = new Intent();
    ArrayList<HashMap<String, Object>> arrayList;
    int pos = -1;
    //boolean play_flag = false;
    ListViewAdapter listViewAdapter;
    ImageView imageView = null;

    private Handler mHandler1 = new Handler() {
        public void handleMessage(Message msg) {
            nickname = getView().findViewById(R.id.schoolname);
            //status.setText(Html.fromHtml(msg.obj.toString()));
            //Toast.makeText(Login.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            String str = msg.obj.toString();
            nickname.setText(str);

//            Context ctx = getActivity();
//            SharedPreferences share =ctx.getSharedPreferences("myshare", Context.MODE_APPEND);
//            String str=share.getString("qianming","");
//            qm = getView().findViewById(R.id.qianming);
//            nickname.setText(str);
        };
    };
    private Handler mHandler2 = new Handler() {
        public void handleMessage(Message msg) {
            qm = getView().findViewById(R.id.schoolmemo);
            //status.setText(Html.fromHtml(msg.obj.toString()));
            //Toast.makeText(Login.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            String str = msg.obj.toString();
            qm.setText(str);
        };
    };

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
                    Message msg1 = new Message();
                    //写入共享变量
                    Context ctx = getActivity();
                    SharedPreferences share = ctx.getSharedPreferences("myshare", Context.MODE_APPEND);

                    String accStr = share.getString("data_id", "");
                    //System.out.println(accStr);

                    String uurl = getString(R.string.Server_IP_Port) + "/user/user/detail?" + accStr;
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
                    JsonUtils_detail jsonUtils_detail = new JsonUtils_detail();
                    Detail detail = jsonUtils_detail.parseDetailFromJson(result);
                    //Data data=new Detail.data();
                    msg.obj = detail.data.nickname;
                    msg1.obj=detail.data.memo;
                    System.out.println("nickname:" + detail.data.nickname);
                    System.out.println("memo:" + detail.data.memo);
//                    Context ctx = getActivity();
//                    SharedPreferences share = ctx.getSharedPreferences("myshare", Context.MODE_APPEND);
                    SharedPreferences.Editor editor = share.edit();
                    editor.putString("qianming", detail.data.memo);
                    editor.putString("xuehao", detail.data.XH);
                    editor.putString("nickname", detail.data.nickname);
                    editor.commit();
                    mHandler1.sendMessage(msg);
                    mHandler2.sendMessage(msg1);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    public void initSettingItems() {

        ListView listView = getView().findViewById(R.id.settingList);
        listViewAdapter = new ListViewAdapter(getActivity(), getData());
        listView.setAdapter(listViewAdapter);
        AdapterView.OnItemClickListener listViewListener
                = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arg2 == 0) {
                    //Toast.makeText(getActivity(), "This is Item 0", Toast.LENGTH_SHORT).show();
                    intent = new Intent(getActivity(), ChangeInfo.class);
                    startActivity(intent);
                }
                if (arg2 == 1) {
                    //Toast.makeText(getActivity(), "This is Item 1", Toast.LENGTH_SHORT).show();
                    intent = new Intent(getActivity(), Change.class);
                    startActivity(intent);
                }
                if (arg2 == 2) {
                    //Toast.makeText(getActivity(), "This is Item 2", Toast.LENGTH_SHORT).show();
                    intent= new Intent(getActivity(), history.class);
                    startActivity(intent);
                }
                if (arg2 == 3) {
                    intent = new Intent(getActivity(), Faq.class);
                    startActivity(intent);
                }
                if (arg2 == 4) {
                    intent = new Intent(getActivity(), About.class);
                    startActivity(intent);
                }
            }
        };
        listView.setOnItemClickListener(listViewListener);

        loginout=getView().findViewById(R.id.LoginOut);
        loginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context ctx = getActivity();
                SharedPreferences share = ctx.getSharedPreferences("myshare", Context.MODE_APPEND);
                SharedPreferences.Editor editor = share.edit();
                editor.putString("data_id", "");
                editor.putString("username", "");
                editor.putString("password", "");
                editor.commit();
                intent=new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

    }

    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null) {
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
        }
    }

    private ArrayList<HashMap<String, Object>> getData() {
        arrayList = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> hashMap1 = new HashMap<String, Object>();
        HashMap<String, Object> hashMap2 = new HashMap<String, Object>();
        HashMap<String, Object> hashMap3 = new HashMap<String, Object>();
        HashMap<String, Object> hashMap4 = new HashMap<String, Object>();
        HashMap<String, Object> hashMap5 = new HashMap<String, Object>();
        HashMap<String, Object> hashMap6 = new HashMap<String, Object>();

        hashMap1.put("image", R.mipmap.profile);
        hashMap1.put("itemName", "修改个人资料");
        arrayList.add(hashMap1);
        hashMap2.put("image", R.mipmap.account);
        hashMap2.put("itemName", "账号与安全");
        arrayList.add(hashMap2);
        hashMap3.put("image", R.mipmap.history);
        hashMap3.put("itemName", "历史记录");
        arrayList.add(hashMap3);
        hashMap5.put("image", R.mipmap.faq);
        hashMap5.put("itemName", "技术文档");
        arrayList.add(hashMap5);
        hashMap4.put("image", R.mipmap.about);
        hashMap4.put("itemName", "关于");
        arrayList.add(hashMap4);

        return arrayList;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
            System.out.println("----------调用onResume----------");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Message msg = new Message();
                        Message msg1 = new Message();
                        //写入共享变量
                        Context ctx = getActivity();
                        SharedPreferences share = ctx.getSharedPreferences("myshare", Context.MODE_APPEND);

                        String accStr = share.getString("data_id", "");
                        //System.out.println(accStr);

                        String uurl = getString(R.string.Server_IP_Port) + "/user/user/detail?" + accStr;
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
                        JsonUtils_detail jsonUtils_detail = new JsonUtils_detail();
                        Detail detail = jsonUtils_detail.parseDetailFromJson(result);
                        //Data data=new Detail.data();
                        msg.obj = detail.data.nickname;
                        msg1.obj=detail.data.memo;
                        System.out.println("nickname:" + detail.data.nickname);
                        System.out.println("memo:" + detail.data.memo);
//                    Context ctx = getActivity();
//                    SharedPreferences share = ctx.getSharedPreferences("myshare", Context.MODE_APPEND);
                        SharedPreferences.Editor editor = share.edit();
                        editor.putString("qianming", detail.data.memo);
                        editor.commit();
                        mHandler1.sendMessage(msg);
                        mHandler2.sendMessage(msg1);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            //相当于Fragment的onPause
            System.out.println("----------调用onPause----------");
        }
    }
}