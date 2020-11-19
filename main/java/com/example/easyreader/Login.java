package com.example.easyreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.TestLooperManager;
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
                uurl = new String("http://123.56.151.219:8888/user/user/login");
                //uurl ="http://123.56.151.219:8888/user/user/login?username="+account+"&password="+password;
                //status.setText(uurl);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            url = new URL(uurl);
                            HttpURLConnection conn = null;


                            //url = new URL("http://123.56.151.219:8888/user/user/login?   username=13212405683   &password=123123");
                            conn = (HttpURLConnection) url.openConnection();
                            conn.setDoOutput(true);
                            conn.setConnectTimeout(5000);
                            conn.setRequestMethod("POST");
                            String data=":username="+account+"&password="+password;
                            conn.setRequestProperty("Content-Type", "aapplication/json");
                            conn.setRequestProperty("Content-Length", data.length()+"");
                            OutputStream os=conn.getOutputStream();
                            os.write(data.getBytes());

                            int code=conn.getResponseCode();
                            if(code==200)
                            {

                                //请求成功
                                System.out.println("Connect Succeed!");
                                InputStream in = conn.getInputStream();

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


                            }
                            else
                            {
                                System.out.println("Connect Failed!");
                            }


                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }catch(IOException e1){
                            e1.printStackTrace();
                        }
                    }
                }).start();

            }
        });
    }
}