package com.example.easyreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class health_report extends AppCompatActivity {
    private EditText et1,et2,et3,et4,et5;
    private Button btn;
    Calendar cal;
    String year;
    String month;
    String day;
    String hour;
    String minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_report);
        setStatusBarFullTransparent();
        et1=findViewById(R.id.h1);
        et2=findViewById(R.id.h);
        et3=findViewById(R.id.h2);
        et4=findViewById(R.id.h3);
        et5=findViewById(R.id.h4);
        btn=findViewById(R.id.btnsubmit);


        cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = String.valueOf(cal.get(Calendar.YEAR));
                month = String.valueOf(cal.get(Calendar.MONTH)+1);
                day = String.valueOf(cal.get(Calendar.DATE));
                if (cal.get(Calendar.AM_PM) == 0)
                    hour = String.valueOf(cal.get(Calendar.HOUR));
                else
                    hour = String.valueOf(cal.get(Calendar.HOUR)+12);
                minute = String.valueOf(cal.get(Calendar.MINUTE));
                String str=year+"-"+month+"-"+day+" "+hour+":"+minute+" "+et5.getText().toString()+"\n"+"体温 "+et1.getText().toString()+"\n"+et2.getText().toString()+"感冒症状\n"+et3.getText().toString()+"经过风险地区\n"+et4.getText().toString()+"接触过感染者";
                Context ctx = health_report.this;
                SharedPreferences share = ctx.getSharedPreferences("myshare", Context.MODE_APPEND);

                String s1,s2,s3;
                s1=share.getString("health_record1","");
                s2=share.getString("health_record2","");
                s3=share.getString("health_record3","");

                SharedPreferences.Editor editor = share.edit();
                editor.putString("health_record1", str);
                editor.putString("health_record2", s1);
                editor.putString("health_record3", s2);
                editor.commit();
                Toast.makeText(health_report.this, "提交成功", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(health_report.this,JiaXiaoTong.class);
                startActivity(intent);
                health_report.this.finish();
            }
        });
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