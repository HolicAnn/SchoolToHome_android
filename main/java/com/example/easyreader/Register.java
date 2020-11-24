package com.example.easyreader;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Register extends AppCompatActivity {

    private Button register_button, getverifycode_button;
    private EditText register_nickname, register_password, register_phonenumber, register_verifycode;
    private TextView status;
    private String uurl;
    private URL url;
    private HttpURLConnection urlConnection = null;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            //status.setText(Html.fromHtml(msg.obj.toString()));
            Toast.makeText(Register.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            if(msg.obj.toString().equals("注册成功")){
                Intent intent = null;
                intent = new Intent(Register.this, Login.class);
                intent.putExtra("phoneNumber", register_phonenumber.getText().toString());
                intent.putExtra("passWord", register_password.getText().toString());
                startActivity(intent);
                Register.this.finish();
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_button = findViewById(R.id.register_Button);
        getverifycode_button = findViewById(R.id.getVerifyCode);
        register_nickname = findViewById(R.id.nickName);
        register_password = findViewById(R.id.register_password);
        register_phonenumber = findViewById(R.id.register_phoneNumber);
        register_verifycode = findViewById(R.id.register_verifyCode);
        status = findViewById(R.id.register_status);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nickname=register_nickname.getText().toString();
                final String password=register_password.getText().toString();
                final String verifycode=register_verifycode.getText().toString();
                final String phoneNumber=register_phonenumber.getText().toString();
                uurl = getString(R.string.Server_IP_Port);
                uurl+="/user/user/register?phone=";
                //uurl = new String("http://192.168.2.130:3000/user/user/register?phone=");
                uurl+=phoneNumber;
                uurl+="&password=";
                uurl+=password;
                uurl+="&codes=";
                uurl+=verifycode;
                uurl+="&nickname=";
                uurl+=nickname;


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
                            msg.obj=jsonUtils_login.parseLoginStateFromJson(result);
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
                uurl = new String("http://192.168.2.130:3000/user/user/sendSMS?phone=");
                uurl+=phoneNumber;
                uurl+="&TemplateCode=1";

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
                            msg.obj=jsonUtils_login.parseLoginStateFromJson(result);
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
}