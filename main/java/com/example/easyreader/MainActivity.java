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
                intent = new Intent(MainActivity.this, JiaXiaoTong.class);
=======
                intent = new Intent(MainActivity.this, action.class);
>>>>>>> 9510421b0485816795b7bce1e8bbebf02d13bd42
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
    }
}