package com.example.easyreader;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

public class SignIn extends AppCompatActivity {
    private MapView mapView;
    private AMap aMap;
    private Button locate, signin;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

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
                init();
                vib.vibrate(75);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignIn.this,"签到成功!",Toast.LENGTH_SHORT).show();
                vib.vibrate(75);
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
}