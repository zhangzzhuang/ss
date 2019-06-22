package com.example.lenovo.myapplication.mvp.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.mvp.user.adapter.SAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dingin on 2018/5/24.
 */

public class SettingActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting);
        List<Map<String,Object>> list=new ArrayList<>();



        Map<String,Object> map2=new HashMap<>();
        map2.put("src",R.drawable.give_me_favor);
        map2.put("name","鼓励一下");

        Map<String,Object> map3=new HashMap<>();
        map3.put("src",R.drawable.privacy_agreement);
        map3.put("name","隐私和协定");

        Map<String,Object> map4=new HashMap<>();
        map4.put("src",R.drawable.signout);
        map4.put("name","退出登录");


        list.add(map2);
        list.add(map3);
        list.add(map4);
        SAdapter sadapter=new SAdapter(
                this,
                R.layout.list_setting_item,list
        );
        ListView listView=findViewById(R.id.setting_lv);
        listView.setAdapter(sadapter);


        Intent intent = getIntent();
        final int position = intent.getIntExtra("position",-2);


        ImageView set_back = findViewById(R.id.setting_back);
        set_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SettingActivity.this.finish();

            }
        });
    }
}
