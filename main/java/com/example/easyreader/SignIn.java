package com.example.easyreader;

import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class SignIn extends AppCompatActivity {
    private MapView mapView;
    private AMap aMap;
    private Button locate, signin;
    private LocationManager locationManager;
    Calendar cal;
    String year;
    String month;
    String day;
    String hour;
    String minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        setStatusBarFullTransparent();
        final Vibrator vib = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        locate = findViewById(R.id.locate);
        signin = findViewById(R.id.signin);

        aMap=mapView.getMap();
        CameraUpdate cu= CameraUpdateFactory.zoomTo(15);
        aMap.moveCamera(cu);

        init();

        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignIn.this,"正在定位，请稍候...",Toast.LENGTH_SHORT).show();
                init();
                LatLng pos=new LatLng(32.17580772,118.71004343);
                moveCamera(pos);
                vib.vibrate(75);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignIn.this,"签到成功!",Toast.LENGTH_SHORT).show();
                vib.vibrate(75);
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
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                DecimalFormat df=new DecimalFormat("0.###");
                String str1=df.format(location.getLatitude());
                String str2=df.format(location.getLongitude());
                String str=year+"-"+month+"-"+day+" "+hour+":"+minute+" 位置:"+str2+" "+str1;
                Context ctx = SignIn.this;
                SharedPreferences share = ctx.getSharedPreferences("myshare", Context.MODE_APPEND);

                String s1,s2,s3;
                s1=share.getString("signin_record1","");
                s2=share.getString("signin_record2","");
                s3=share.getString("signin_record3","");

                SharedPreferences.Editor editor = share.edit();
                editor.putString("signin_record1", str);
                editor.putString("signin_record2", s1);
                editor.putString("signin_record3", s2);
                editor.commit();
            }
        });

    }

    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    protected void onSavedInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    private void init() {

        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 300, 8, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            updatePosition(location);
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {
                        }

                        @Override
                        public void onProviderEnabled(String provider) {
                            updatePosition(locationManager.getLastKnownLocation(provider));
                        }

                        @Override
                        public void onProviderDisabled(String provider) {
                        }
                    }
            );

        } catch (SecurityException e) {
            e.printStackTrace();
        }

        CameraUpdate cu= CameraUpdateFactory.zoomTo(18);
        aMap.moveCamera(cu);
    }

    private void updatePosition(Location loc){
        LatLng pos=new LatLng(loc.getLatitude(),loc.getLongitude());
        CameraUpdate cu =CameraUpdateFactory.changeLatLng(pos);
        moveCamera(pos);
        MarkerOptions mo=new MarkerOptions();
        mo.position(pos);
        //mo.icon(BitmapDescriptorFactory.fromResource())
        mo.draggable(true);
        Marker marker=aMap.addMarker(mo);
    }

    private void moveCamera(LatLng pos){
        aMap.clear();
        CameraUpdate cu=CameraUpdateFactory.changeLatLng(pos);
        aMap.moveCamera(cu);
    }

    protected void setStatusBarFullTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}