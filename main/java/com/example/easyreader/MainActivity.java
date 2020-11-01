package com.example.easyreader;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private Button requestbutton = null;
    private HttpResponse httpResponse = null;
    private HttpEntity httpEntity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestbutton = (Button) findViewById(R.id.requestbutton);
        requestbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpGet httpget = new HttpGet("http://www.baidu.com");//("http://123.56.151.219/tree/tree");
                HttpClient httpclient = new DefaultHttpClient();
                InputStream inputStream = null;
                try {
                    httpResponse = httpclient.execute(httpget);
                    httpEntity = httpResponse.getEntity();
                    inputStream = httpEntity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String result = "";
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        result = result + line;
                    }
                    System.out.println("--------------------------------------------------------------------------");
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    try{
                        inputStream.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}