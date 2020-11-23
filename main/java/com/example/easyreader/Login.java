package com.example.easyreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.TestLooperManager;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    private TextView status;
    private String uurl;
    private URL url;
    private HttpURLConnection urlConnection = null;
    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg){
            status.setText(Html.fromHtml(msg.obj.toString()));
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.button_login);
        login_account = findViewById(R.id.login_account);
        login_password = findViewById(R.id.login_password);
        status = findViewById(R.id.textView5);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String account = login_account.getText().toString();
                final String password = login_password.getText().toString();
                //System.out.println(account+password);
                uurl = new String("http://192.168.2.130:3000/user/user/login?username=");
                uurl+=account;
                uurl+="&password=";
                uurl+=password;
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