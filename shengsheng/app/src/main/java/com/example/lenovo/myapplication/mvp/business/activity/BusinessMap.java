package com.example.lenovo.myapplication.mvp.business.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.mvp.main.view.activity.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by me on 2018/6/13.
 */

public class BusinessMap extends Activity {
    private MapView mapView;
    private BaiduMap baiduMap;
    private LocationClient locationClient;
    private LocationClientOption locationClientOption;
    private Display display;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.business_map);
        display= getWindowManager().getDefaultDisplay();
        showInfo("请长按标记");

        mapView = findViewById(R.id.business_map);
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
        baiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                showInfo("返回保存位置");
                String businessLocation = marker.getPosition().latitude+";"+marker.getPosition().longitude;
                Data.businessLocation=businessLocation;
            }

            @Override
            public void onMarkerDragStart(Marker marker) {
                Data.isFirstLocation = false;
                showInfo("请选择您店铺的位置");

            }
        });
    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (Data.isFirstLocation){
                LatLng point = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.marker);
                OverlayOptions markeroptions = new MarkerOptions().position(point).icon(bitmap).draggable(true);
                Marker marker = (Marker) baiduMap.addOverlay(markeroptions);
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(point);
                baiduMap.animateMapStatus(msu);
                MyLocationData locationData = new MyLocationData.Builder()
                        .latitude(bdLocation.getLatitude())
                        .longitude(bdLocation.getLongitude())
                        .build();
                baiduMap.setMyLocationData(locationData);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        mapView.onDestroy();
    }

    private void showInfo(String info){
        Toast toast = Toast.makeText(getApplicationContext(),info,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,0,display.getHeight()/6);
        toast.show();
        }

}
