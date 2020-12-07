package com.example.easyreader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button testBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testBtn = findViewById(R.id.testBtn);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                ///intent = new Intent(MainActivity.this, learning.class);
<<<<<<< HEAD
                intent = new Intent(MainActivity.this, Login.class);
=======
                intent = new Intent(MainActivity.this, learning.class);
>>>>>>> 8330e6669c84fad7f0f381b42fe13745ebd37586
                //intent = new Intent(MainActivity.this, action.class);
                //intent = new Intent(MainActivity.this, action.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
    }
}