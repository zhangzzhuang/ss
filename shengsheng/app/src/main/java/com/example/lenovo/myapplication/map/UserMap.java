package com.example.lenovo.myapplication.map;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.mvp.main.view.activity.Data;

/**
 * Created by me on 2018/6/13.
 */

public class UserMap extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.user_map);
        Intent intent = getIntent();
        String address = intent.getStringExtra("address");
        if (address!=null){
            String[] user_point = Data.userLocation.split(";");
            String[] business_poing = address.split(";");
            Log.e("导航起点:",""+Double.parseDouble(user_point[0])+";"+Double.parseDouble(user_point[1]));
            Log.e("导航终点:",""+Double.parseDouble(business_poing[0])+";"+Double.parseDouble(business_poing[1]));
            LatLng start_point = new LatLng(Double.parseDouble(user_point[0]),Double.parseDouble(user_point[1]));
            LatLng end_point = new LatLng(Double.parseDouble(business_poing[0]),Double.parseDouble(business_poing[1]));
            NaviParaOption para = new NaviParaOption();
            para.startPoint(start_point);
            para.startName("从这里开始");
            para.endPoint(end_point);
            para.endName("到这里结束");
            try {
                BaiduMapNavigation.openBaiduMapNavi(para, getApplicationContext());
            } catch (BaiduMapAppNotSupportNaviException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"您尚未安装百度地图或地图版本过低",Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }
}
