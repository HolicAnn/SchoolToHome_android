package com.example.easyreader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telecom.TelecomManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link h_HomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class h_HomePageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private WebView webView_h1;
    private ImageButton ib1,ib2,ib3,ib4,ib5,ib6,ib7,ib8,ib9,ib10;
    private TextView schoolname,schoolmemo;
//    private Handler mHandler1 = new Handler() {
//        public void handleMessage(Message msg) {
//            String str=msg.obj.toString();
//        };
//    };

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
            System.out.println("----------调用onResume----------");
        } else {
            //相当于Fragment的onPause
            System.out.println("----------调用onPause----------");
        }
    }

    public h_HomePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment h_HomePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static h_HomePageFragment newInstance(String param1, String param2) {
        h_HomePageFragment fragment = new h_HomePageFragment();
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
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }

    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null) {
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initHomePage();

        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(getActivity(), h_CheckTable.class);
                startActivity(intent);
                //Toast.makeText(getActivity(), "这是功能1", Toast.LENGTH_SHORT).show();
            }
        });
        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "这是功能2", Toast.LENGTH_SHORT).show();
            }
        });
        ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "这是功能3", Toast.LENGTH_SHORT).show();

            }
        });
        ib4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), learning.class);
                startActivity(intent);
            }
        });
        ib5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(getActivity(), health_report.class);
                startActivity(intent);
                //Toast.makeText(getActivity(), "这是功能5", Toast.LENGTH_SHORT).show();
            }
        });
        ib6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(getActivity(), SignIn.class);
                startActivity(intent);
                //Toast.makeText(getActivity(), "这是功能6", Toast.LENGTH_SHORT).show();
            }
        });
        ib7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "这是功能7", Toast.LENGTH_SHORT).show();
            }
        });
        ib8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone();
                Toast.makeText(getActivity(), "正在拨打电话...", Toast.LENGTH_SHORT).show();
            }
        });
//        ib9.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getActivity(), learning.class);
//                startActivity(intent);
//            }
//        });
//        ib10.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getActivity(), action.class);
//                startActivity(intent);
//            }
//        });

    }

    public void initHomePage() {

        schoolname=getView().findViewById(R.id.schoolname);
        schoolmemo=getView().findViewById(R.id.schoolmemo);
        Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.likai);
        schoolname.setTypeface(typeface);
        schoolmemo.setTypeface(typeface);

        ib1=getView().findViewById(R.id.imageButton);
        ib2=getView().findViewById(R.id.imageButton2);
        ib3=getView().findViewById(R.id.imageButton3);
        ib4=getView().findViewById(R.id.imageButton4);
        ib5=getView().findViewById(R.id.imageButton5);
        ib6=getView().findViewById(R.id.imageButton6);
        ib7=getView().findViewById(R.id.imageButton7);
        ib8=getView().findViewById(R.id.imageButton8);

        ViewPager viewPager1 = getView().findViewById(R.id.viewpager_home);
        List<View> views = new ArrayList<View>();
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        View view1 = inflater.inflate(R.layout.h1, null);
        View view2 = inflater.inflate(R.layout.h2, null);
        View view3 = inflater.inflate(R.layout.h3, null);

        views.add(view1);
        views.add(view2);
        views.add(view3);

        viewPager1.setAdapter(new HomePagerAdapter(views));
        //ib9=getView().findViewById(R.id.imageButton9);
        //ib10=getView().findViewById(R.id.imageButton10);
        View view_h1 = View.inflate(getActivity(), R.layout.h1, null);

        webView_h1 = view_h1.findViewById(R.id.webView_h1);
        webView_h1.loadUrl("http://www.bing.com");
        webView_h1.getSettings().setJavaScriptEnabled(true);
        webView_h1.setWebViewClient(new WebViewClient());
        webView_h1.getSettings().setLoadWithOverviewMode(true);
        webView_h1.getSettings().setUseWideViewPort(true);
        //webView_h1.getSettings().setBuiltInZoomControls(true);


    }


    public void callPhone() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    //Message msg=new Message();
                    Context ctx = getActivity();
                    SharedPreferences share =ctx.getSharedPreferences("myshare", Context.MODE_APPEND);

                    String accStr=share.getString("data_id","");
                    //System.out.println(accStr);

                    String uurl=getString(R.string.Server_IP_Port)+"/user/student/get_teacher_phone?"+accStr;
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
                    JsonUtils_login jsonUtils_login = new JsonUtils_login();
                    Login_state login_state = jsonUtils_login.parseLoginStateFromJson(result);
                    String phoneNum=login_state.getData();
                    System.out.println("phoneNum:"+phoneNum);
                    //mHandler1.sendMessage(msg);
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + phoneNum);
                    intent.setData(data);
                    startActivity(intent);
                }
                catch (MalformedURLException e){
                    e.printStackTrace();
                }
                catch (IOException ee){
                    ee.printStackTrace();
                }
            }
        }).start();
    }

    public class HomePagerAdapter extends PagerAdapter {
        public List<View> views;

        public HomePagerAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public int getCount() { return views.size(); }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int pos, Object obj) {
            ((ViewPager) container).removeView(views.get(pos));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(views.get(position));
            return views.get(position);
        }
    }
}