package com.example.easyreader;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button requestbutton = null;
    URL url;
    private HttpURLConnection urlConnection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestbutton = (Button) findViewById(R.id.requestbutton);
        requestbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            url = new URL("http://123.56.151.219/tree/tree");
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