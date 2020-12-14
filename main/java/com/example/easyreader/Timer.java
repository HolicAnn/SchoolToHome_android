package com.example.easyreader;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

public class Timer extends Service {
    MediaPlayer mp = null;
    String t1,t2,t3,n1,n2,n3,flag;

    Calendar cal;
    String year;
    String month;
    String day;
    String hour;
    String minute;

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        try {
            t1="0:26";
            AssetFileDescriptor afd = getAssets().openFd("I.mp3");
            mp = new MediaPlayer();
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mp.prepare();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {

                        cal = Calendar.getInstance();
                        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

                        year = String.valueOf(cal.get(Calendar.YEAR));
                        month = String.valueOf(cal.get(Calendar.MONTH)+1);
                        day = String.valueOf(cal.get(Calendar.DATE));
                        if (cal.get(Calendar.AM_PM) == 0)
                            hour = String.valueOf(cal.get(Calendar.HOUR));
                        else
                            hour = String.valueOf(cal.get(Calendar.HOUR)+12);
                        minute = String.valueOf(cal.get(Calendar.MINUTE));
                        Context ctx = Timer.this;
                        SharedPreferences share = ctx.getSharedPreferences("myshare", Context.MODE_APPEND);
                        t1=share.getString("t1","").toString();
                        t2=share.getString("t2","").toString();
                        t3=share.getString("t3","").toString();
                        n1=share.getString("n1","").toString();
                        n2=share.getString("n2","").toString();
                        n3=share.getString("n3","").toString();
                        flag=share.getString("flag","").toString();
                        String now=hour+":"+minute;
                        System.out.println(t1+"   "+now);
                        if (now.equals(t1)&&(!flag.equals(1))) {
                            //mp.start();
                            Intent intent1=new Intent(Timer.this,n.class);
                            intent1.putExtra("n",n1);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                            startActivity(intent1);
                        }
                        else if(now.equals(t2)&&(!flag.equals(1))) {
                            //mp.start();
                            Intent intent1=new Intent(Timer.this,n.class);
                            intent1.putExtra("n",n2);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                            startActivity(intent1);
                        }
                        else if(now.equals(t3)&&(!flag.equals(1))) {
                            //mp.start();
                            Intent intent1=new Intent(Timer.this,n.class);
                            intent1.putExtra("n",n3);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                            startActivity(intent1);
                        }
                        else {
                            mp.stop();
                        }

                        try {
                            Thread.sleep(1000*60);
                        }catch (InterruptedException e4) {
                            e4.printStackTrace();
                        }

                    }
                }
            }).start();

        } catch (
                IllegalArgumentException e) {
            e.printStackTrace();
        } catch (
                IllegalStateException ee) {
            ee.printStackTrace();
        } catch (
                IOException eee) {
            eee.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);

    }

    public void onDestroy() {
        super.onDestroy();
        mp.stop();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
