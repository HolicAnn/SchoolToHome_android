package com.example.easyreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class history extends AppCompatActivity {

    private TextView tv1,tv2,tv3,tv4,tv5,tv6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        setStatusBarFullTransparent();
        tv1=findViewById(R.id.textView33);
        tv2=findViewById(R.id.textView34);
        tv3=findViewById(R.id.textView35);

        tv4=findViewById(R.id.textView38);
        tv5=findViewById(R.id.textView39);
        tv6=findViewById(R.id.textView40);

        Context ctx = history.this;
        SharedPreferences share =ctx.getSharedPreferences("myshare", Context.MODE_APPEND);
        String daka1=share.getString("signin_record1","");
        String daka2=share.getString("signin_record2","");
        String daka3=share.getString("signin_record3","");
        tv1.setText(daka1);
        tv2.setText(daka2);
        tv3.setText(daka3);

        String jk1=share.getString("health_record1","");
        String jk2=share.getString("health_record2","");
        String jk3=share.getString("health_record3","");
        tv4.setText(jk1);
        tv5.setText(jk2);
        tv6.setText(jk3);
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