package com.example.easyreader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class Welcome extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context ctx = Welcome.this;
        SharedPreferences share = ctx.getSharedPreferences("myshare", Context.MODE_APPEND);
        SharedPreferences.Editor editor = share.edit();
        String str=share.getString("FirstUse","");
        if(str.equals("1")){
            Intent intent=new Intent(Welcome.this, Login.class);
            startActivity(intent);
            Welcome.this.finish();
        }
        else{
            editor.putString("FirstUse", "1");
            editor.commit();
        }

        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        setStatusBarFullTransparent();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_welcome);
        Button btn2homepage=findViewById(R.id.btntoeasyreader);
        List<View> views = new ArrayList<View>();
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());

        View view1 = inflater.inflate(R.layout.tab1, null);
        View view2 = inflater.inflate(R.layout.tab2, null);
        View view3 = inflater.inflate(R.layout.tab3, null);

        views.add(view1);
        views.add(view2);
        views.add(view3);

        viewPager.setAdapter(new HomePagerAdapter(views));
        btn2homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Welcome.this, Login.class);
                startActivity(intent);
                Welcome.this.finish();
            }
        });
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