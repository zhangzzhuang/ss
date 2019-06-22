package com.example.lenovo.myapplication.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.mvp.main.view.activity.Data;

public class UserLocation extends Activity {
    private MapView mapView;
    private BaiduMap baiduMap;
    private LocationClient locationClient;
    private LocationClientOption locationClientOption;
    private boolean isFirstLocation = true;
    private boolean flag = false;
    String address;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.user_location);

        Intent intent = getIntent();
        address = intent.getStringExtra("address");

        mapView = findViewById(R.id.user_location_map);
        baiduMap = mapView.getMap();
        locationClient = new LocationClient(getApplicationContext());
        locationClientOption = new LocationClientOption();
        locationClientOption.setCoorType("bd0911");
        locationClientOption.setOpenGps(true);
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        locationClientOption.setScanSpan(3000);
        locationClientOption.setIsNeedAddress(true);
        locationClientOption.setIsNeedLocationPoiList(true);
        locationClient.setLocOption(locationClientOption);
        MyLocationListener myLocationListener = new MyLocationListener();
        locationClient.registerLocationListener(myLocationListener);
        locationClient.start();
        baiduMap.setMyLocationEnabled(true);
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        baiduMap.setMapStatus(msu);
        if (flag){
            Intent intent1 = new Intent(getApplicationContext(),UserMap.class);
            intent1.putExtra("address",address);
            startActivity(intent1);
        }
    }
    private class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (isFirstLocation){
                String userLocation = bdLocation.getLatitude()+";"+bdLocation.getLongitude();
                Log.e("123",userLocation);
                Data.userLocation = userLocation;
                LatLng point = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.marker);
                OverlayOptions markeroptions = new MarkerOptions().position(point).icon(bitmap).draggable(true);
                Marker marker = (Marker) baiduMap.addOverlay(markeroptions);
                marker.setTitle("您的位置");
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(point);
                baiduMap.animateMapStatus(msu);
                isFirstLocation = false;
                MyLocationData locationData = new MyLocationData.Builder()
                        .latitude(bdLocation.getLatitude())
                        .longitude(bdLocation.getLongitude())
                        .build();
                baiduMap.setMyLocationData(locationData);
                flag=true;
                Intent intent = new Intent(getApplicationContext(),UserMap.class);
                intent.putExtra("address",address);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }
}
