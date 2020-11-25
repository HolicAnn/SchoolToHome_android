package com.example.easyreader;

import android.os.Bundle;
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
                switch (checkedId){
                    case R.id.rbbookShelf:
                        index=0;
                        break;
                    case R.id.rbonLineStore:
                        index=1;
                        break;
                    case R.id.rbmyInfoAndSetting:
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
}