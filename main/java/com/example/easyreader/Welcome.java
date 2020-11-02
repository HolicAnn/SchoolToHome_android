package com.example.easyreader;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);

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
                Intent intent=new Intent(Welcome.this,EasyReader.class);
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
}