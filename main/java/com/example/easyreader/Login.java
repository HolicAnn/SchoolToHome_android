package com.example.easyreader;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.TestLooperManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;


public class Login extends AppCompatActivity {

    private Button btnLogin;
    private EditText login_account, login_password;
    private TextView status,register;
    private String uurl;
    private URL url;
    private HttpURLConnection urlConnection = null;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            //status.setText(Html.fromHtml(msg.obj.toString()));
            Toast.makeText(Login.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            if (msg.obj.toString().equals("登录成功")) {
                Intent intent = null;
                intent = new Intent(Login.this, JiaXiaoTong.class);
                startActivity(intent);
                Login.this.finish();
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setStatusBarFullTransparent();
        register=findViewById(R.id.textView48);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Login.this,Register.class);
                startActivity(intent);
                Login.this.finish();
            }
        });


        Intent intent = getIntent();
        String msg1 = intent.getStringExtra("phoneNumber");
        String msg2 = intent.getStringExtra("passWord");
        //textView.setText(msg);
        btnLogin = findViewById(R.id.button_login);
        login_account = findViewById(R.id.login_account);
        login_password = findViewById(R.id.login_password);
        if (msg1 != null && msg2 != null) {
            System.out.println("Get from register activity");
            login_account.setText(msg1);
            login_password.setText(msg2);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    btnLogin.performClick();
                }
            }).start();
        }
        Context ctx = Login.this;
        SharedPreferences share =ctx.getSharedPreferences("myshare", Context.MODE_APPEND);
        msg1=share.getString("username","");
        msg2=share.getString("password","");
        if (msg1 != null && msg2 != null) {
            System.out.println("Get from last login");
            login_account.setText(msg1);
            login_password.setText(msg2);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    btnLogin.performClick();
                }
            }).start();
        }
        //status = findViewById(R.id.textView5);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String account = login_account.getText().toString();
                final String password = login_password.getText().toString();
                //System.out.println(account+password);
                uurl = getString(R.string.Server_IP_Port);
                uurl += "/user/user/login?username=";
                uurl += account;
                uurl += "&password=";
                uurl += password;
                //uurl ="http://123.56.151.219:8888/user/user/login?username="+account+"&password="+password;
                //status.setText(uurl);

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
                            JsonUtils_login jsonUtils_login = new JsonUtils_login();
                            msg.obj = jsonUtils_login.parseLoginStateFromJson(result).getMsg();
                            mHandler.sendMessage(msg);

                            Context ctx = Login.this;
                            SharedPreferences share = ctx.getSharedPreferences("myshare", Context.MODE_APPEND);
                            SharedPreferences.Editor editor = share.edit();
                            String str = "data_id=" + jsonUtils_login.parseLoginStateFromJson(result).getData();
                            editor.putString("data_id", str);
                            editor.putString("username", account);
                            editor.putString("password", password);
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