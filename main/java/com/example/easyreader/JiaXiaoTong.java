package com.example.easyreader;

import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class JiaXiaoTong extends AppCompatActivity {

    private FrameLayout homeContent;
    private RadioGroup radioGroup;
    private RadioButton rbBookShelf,rbOnlineStore,rbMy;
    static final int NUM_ITEMS=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiaxiaotong);

        setStatusBarFullTransparent();

        initView();

    }
    protected void initView(){
        homeContent=(FrameLayout)findViewById(R.id.homeContent);
        radioGroup=(RadioGroup)findViewById(R.id.MyRadioGroup);
        rbBookShelf=(RadioButton)findViewById(R.id.rbbookShelf);
        rbOnlineStore=(RadioButton)findViewById(R.id.rbonLineStore);
        rbMy=(RadioButton)findViewById(R.id.rbmyInfoAndSetting);
        //rbProfile=(RadioButton)findViewById(R.id.rbProfile);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup Group, int checkedId) {
                int index=0;
                final Vibrator vib = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
                switch (checkedId){
                    case R.id.rbbookShelf:
                        vib.vibrate(75);
                        index=0;
                        break;
                    case R.id.rbonLineStore:
                        vib.vibrate(75);
                        index=1;
                        break;
                    case R.id.rbmyInfoAndSetting:
                        vib.vibrate(75);
                        index=2;
                        break;
                    /*case R.id.rbProfile:
                        index=3;
                        break;*/
                }
                updateHomeContent(index);
            }
        });
    }
    FragmentStatePagerAdapter adapter=new
            FragmentStatePagerAdapter(getSupportFragmentManager()) {
                @NonNull
                @Override
                public Fragment getItem(int i) {
                    Fragment fragment=null;
                    switch(i){
                        case 0:
                            fragment=new h_HomePageFragment();
                            break;
                        case 1:
                            fragment=new ActivityFragment();
                            break;
                        case 2:
                            fragment=new m_MyInfoAndSettingFragment();
                            break;
                        default:
                            fragment=new h_HomePageFragment();
                            break;

                    }
                    return fragment;
                }

                @Override
                public int getCount() {
                    return NUM_ITEMS;
                }
            };
    public void updateHomeContent(int i){
        Fragment fragment=(Fragment)adapter.instantiateItem(homeContent,i);
        adapter.setPrimaryItem(homeContent,0,fragment);
        adapter.finishUpdate(homeContent);
    }

    protected void onStart() {
        super.onStart();
        radioGroup.check(R.id.rbbookShelf);
        updateHomeContent(0);
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