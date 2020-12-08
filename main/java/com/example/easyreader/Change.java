package com.example.easyreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Change extends AppCompatActivity {
    private Button register_button, getverifycode_button;
    private EditText register_nickname, register_password, register_phonenumber, register_verifycode;
    private TextView status;
    private String uurl;
    private URL url;
    private HttpURLConnection urlConnection = null;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            //status.setText(Html.fromHtml(msg.obj.toString()));
            Toast.makeText(Change.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            System.out.println(msg.obj.toString());
            if(msg.obj.toString().equals("重置密码成功")){
                Intent intent = null;
                intent = new Intent(Change.this, Login.class);
                intent.putExtra("phoneNumber", register_phonenumber.getText().toString());
                intent.putExtra("passWord", register_password.getText().toString());
                startActivity(intent);
                Change.this.finish();
            }
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        setStatusBarFullTransparent();

        register_button = findViewById(R.id.ch_Button);
        getverifycode_button = findViewById(R.id.getVerifyCode2);
        register_password = findViewById(R.id.ch_password);
        register_phonenumber = findViewById(R.id.ch_phone);
        register_verifycode=findViewById(R.id.change_verifyCode);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final String nickname=register_nickname.getText().toString();
                final String password=register_password.getText().toString();
                final String verifycode=register_verifycode.getText().toString();
                final String phoneNumber=register_phonenumber.getText().toString();
                uurl = getString(R.string.Server_IP_Port);
                uurl+="/user/user/resetpwd?phone=";
                //uurl = new String("http://192.168.2.130:3000/user/user/register?phone=");
                uurl+=phoneNumber;
                uurl+="&newpwd=";
                uurl+=password;
                uurl+="&codes=";
                uurl+=verifycode;


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Message msg = new Message();
                            url = new URL(uurl);
                            urlConnection = (HttpURLConnection) url.openConnection();
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
                            JsonUtils_login jsonUtils_login=new JsonUtils_login();
                            Login_state login_state=jsonUtils_login.parseLoginStateFromJson(result);
                            msg.obj=login_state.getMsg();
                            mHandler.sendMessage(msg);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        getverifycode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phoneNumber=register_phonenumber.getText().toString();
                String uuurl = getString(R.string.Server_IP_Port);
                uurl = uuurl+"/user/user/sendSMS?phone=";
                uurl+=phoneNumber;
                uurl+="&TemplateCode=2";

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Message msg = new Message();
                            url = new URL(uurl);
                            urlConnection = (HttpURLConnection) url.openConnection();
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
                            JsonUtils_login jsonUtils_login=new JsonUtils_login();
                            Login_state login_state=jsonUtils_login.parseLoginStateFromJson(result);
                            msg.obj=login_state.getMsg();
                            mHandler.sendMessage(msg);
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