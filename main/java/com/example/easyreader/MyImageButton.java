package com.example.easyreader;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyImageButton extends LinearLayout
{
    private ImageView mImage;
    private TextView mText;

    public MyImageButton(Context context, AttributeSet attrs)
    {
        super(context,attrs);

        mImage = new ImageView(context,attrs);
        mImage.setPadding(0,0,0,0);
        mText = new TextView(context,attrs);
        mText.setGravity(Gravity.CENTER);
        mText.setPadding(0,0,0,0);

        //mImage.setClickable(false);
        //mText.setClickable(false);
        setClickable(true);
        setFocusable(true);
        setBackgroundResource(android.R.drawable.btn_default);
        setOrientation(LinearLayout.VERTICAL);
        addView(mImage);
        addView(mText);

    }

}
