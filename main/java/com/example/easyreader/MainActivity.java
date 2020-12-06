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
<<<<<<< HEAD
                intent = new Intent(MainActivity.this, learning.class);
                intent = new Intent(MainActivity.this, Login.class);
                intent = new Intent(MainActivity.this, action.class);
=======
                intent = new Intent(MainActivity.this, action.class);
>>>>>>> 5f967b57b08105c8bebd01715e17940323952508
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
    }
}