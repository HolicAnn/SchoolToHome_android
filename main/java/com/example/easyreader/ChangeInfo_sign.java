package com.example.easyreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ChangeInfo_sign extends AppCompatActivity {

    private Button btn;
    private EditText editText;
    private Intent intent;

    private Handler mHandler1 = new Handler() {
        public void handleMessage(Message msg) {
            Toast.makeText(ChangeInfo_sign.this, "修改成功!", Toast.LENGTH_SHORT).show();
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info_detail);
        setStatusBarFullTransparent();
        btn=findViewById(R.id.button_change_sign);
        editText=findViewById(R.id.editsign);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            String str=editText.getText().toString();
                            Message msg = new Message();

                            //写入共享变量
                            Context ctx = ChangeInfo_sign.this;
                            SharedPreferences share = ctx.getSharedPreferences("myshare", Context.MODE_APPEND);

                            String accStr = share.getString("data_id", "");

                            String uurl = getString(R.string.Server_IP_Port) + "/user/user/edit_memo?memo="+str +"&"+ accStr;
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
                            mHandler1.sendMessage(msg);
                            //Context ctx = ChangeInfo_sign.this;
                            //SharedPreferences share = ctx.getSharedPreferences("myshare", Context.MODE_APPEND);
                            SharedPreferences.Editor editor = share.edit();
                            editor.putString("qianming", str);
                            editor.commit();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
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