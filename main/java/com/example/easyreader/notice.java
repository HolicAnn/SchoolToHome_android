package com.example.easyreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.TimeZone;

public class notice extends AppCompatActivity {

    public ToggleButton tb1,tb2,tb3;
    public EditText n1,n2,n3,t1,t2,t3;
    Calendar cal;
    String year;
    String month;
    String day;
    String hour;
    String minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        setStatusBarFullTransparent();
        tb1=findViewById(R.id.toggleButton);
        tb2=findViewById(R.id.toggleButton2);
        tb3=findViewById(R.id.toggleButton3);

        n1=findViewById(R.id.n1);
        n2=findViewById(R.id.n2);
        n3=findViewById(R.id.n3);
        t1=findViewById(R.id.t1);
        t2=findViewById(R.id.t2);
        t3=findViewById(R.id.t3);

        Context ctx = notice.this;
        SharedPreferences share = ctx.getSharedPreferences("myshare", Context.MODE_APPEND);
        SharedPreferences.Editor editor = share.edit();

        String tt1,tt2,tt3,nn1,nn2,nn3;
        tt1=share.getString("t1","").toString();
        tt2=share.getString("t2","").toString();
        tt3=share.getString("t3","").toString();
        nn1=share.getString("n1","").toString();
        nn2=share.getString("n2","").toString();
        nn3=share.getString("n3","").toString();

        t1.setText(tt1);
        t2.setText(tt2);
        t3.setText(tt3);
        n1.setText(nn1);
        n2.setText(nn2);
        n3.setText(nn3);

        tb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Context ctx = notice.this;
                    SharedPreferences share = ctx.getSharedPreferences("myshare", Context.MODE_APPEND);
                    SharedPreferences.Editor editor = share.edit();
                    editor.putString("t1", t1.getText().toString());
                    editor.putString("n1", n1.getText().toString());
                    editor.commit();
                }
            }
        });
        tb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Context ctx = notice.this;
                    SharedPreferences share = ctx.getSharedPreferences("myshare", Context.MODE_APPEND);
                    SharedPreferences.Editor editor = share.edit();
                    editor.putString("t2", t1.getText().toString());
                    editor.putString("n2", n2.getText().toString());
                    editor.commit();
                }
            }
        });
        tb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Context ctx = notice.this;
                    SharedPreferences share = ctx.getSharedPreferences("myshare", Context.MODE_APPEND);
                    SharedPreferences.Editor editor = share.edit();
                    editor.putString("t3", t1.getText().toString());
                    editor.putString("n3", n3.getText().toString());
                    editor.commit();
                }
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