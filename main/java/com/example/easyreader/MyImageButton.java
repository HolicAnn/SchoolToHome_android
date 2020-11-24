package com.example.easyreader;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyImageButton extends LinearLayout{
    private  ImageView   imageViewbutton;
    private  TextView   textView;
    public void ImageButton_define(Context context,AttributeSet attrs) {
        super(context,attrs);
        imageViewbutton = new ImageView(context, attrs);

        imageViewbutton.setPadding(0, 0, 0, 0);

        textView =new TextView(context, attrs);
        //设置文字水平居中
        textView.setGravity(android.view.Gravity.CENTER_HORIZONTAL);

        textView.setPadding(0, 0, 0, 0);
        //进行点击监听
        setClickable(true);

        setFocusable(true);

        setBackgroundResource(android.R.drawable.btn_default);
        //垂直布局
        setOrientation(LinearLayout.VERTICAL);

        addView(imageViewbutton);

        addView(textView);

    }
}

