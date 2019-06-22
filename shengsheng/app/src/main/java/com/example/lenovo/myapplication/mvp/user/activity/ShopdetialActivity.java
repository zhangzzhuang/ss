package com.example.lenovo.myapplication.mvp.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lenovo.myapplication.manager.UserManage;
import com.example.lenovo.myapplication.mvp.main.view.activity.Data;
import com.example.lenovo.myapplication.mvp.main.model.Goods;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.mvp.user.adapter.ShopdetialAdapter;
import com.example.lenovo.myapplication.map.UserLocation;
import com.example.lenovo.myapplication.mvp.business.model.Business;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lenovo on 2018/5/25.
 */

public class ShopdetialActivity extends Activity {

    private ImageView guanzhubusiness;
    private TextView business_address;
    private ImageView shopDetial_back;
    private Business business = new Business();
    private ImageView business_address_map;
    private View no_data_view;
    private List<Goods> goodsList = new ArrayList<>();
    private ListView lv_shopdetial_list;
    private ImageView imageView;
    private TextView phone_business;
    private String retStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopdetail);
        initView();

        shopDetial_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        business = (Business) intent.getSerializableExtra("business");
        Glide.with(ShopdetialActivity.this)
                .load(Data.urlImageBusiness + business.getImage())
                .into(imageView);

        business_address_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ShopdetialActivity.this,UserLocation.class);
                intent1.putExtra("address",business.getBaidu_zuobiao());
                Data.isFirstLocation=true;
                startActivityForResult(intent1,5);
            }
        });

        if (!TextUtils.isEmpty(business.getShop_add_name())){
            business_address.setText(business.getShop_add_name());

        }
        guanzhubusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (focuOrNotBusiness()){
                    cancalFollowBusiness();
                }else {
                    toFollowBusiness();
                }
            }
        });
        phone_business.setText(business.getPhone());
        if (focuOrNotBusiness()){
            guanzhubusiness.setImageResource(R.drawable.followed);
        }else {
            guanzhubusiness.setImageResource(R.drawable.notfollow);
        }

        setBusinessGoodItem(business);

    }

    private void cancalFollowBusiness(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Data.url + "CancalFollowBusiness?user_id=" + UserManage.getInstance().getUserInfo(ShopdetialActivity.this).getId() + "&business_id=" + business.getId())
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        guanzhubusiness.setImageResource(R.drawable.notfollow);
        Toast.makeText(ShopdetialActivity.this,"取消关注！",Toast.LENGTH_SHORT).show();
    }

    private boolean focuOrNotBusiness(){

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Data.url + "FocuOrNotBusinesss?user_id=" + UserManage.getInstance().getUserInfo(ShopdetialActivity.this).getId() + "&business_id=" + business.getId())
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                retStr = response.body().string();
            }
        });
        return Boolean.parseBoolean(retStr);
    }

    private void initView(){
        imageView=findViewById(R.id.business_detail_img);
        shopDetial_back = findViewById(R.id.shop_detial_back);
        business_address_map = findViewById(R.id.business_address_map);
        guanzhubusiness = findViewById(R.id.guanzhubusiness);
        business_address=findViewById(R.id.business_address_name);
        no_data_view = findViewById(R.id.no_data_view);
        lv_shopdetial_list = findViewById(R.id.lv_shopdetial_list);
        phone_business = findViewById(R.id.phone_business);
    }

    private void toFollowBusiness(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Data.url + "GuanzhuButton?user_id=" + UserManage.getInstance().getUserInfo(ShopdetialActivity.this).getId() + "&business_id=" + business.getId())
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        guanzhubusiness.setImageResource(R.drawable.followed);

        Toast.makeText(ShopdetialActivity.this,"关注成功！",Toast.LENGTH_SHORT).show();
    }


    private void setBusinessGoodItem(Business business){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Data.url+"GetBusinessGoods?business_id="+business.getId())
                .build();

        final Call call = okHttpClient.newCall(request);
        try {
            Response response=call.execute();
            String goodsListStr = response.body().string();
            Gson gson = new Gson();
            Type type = new TypeToken<List<Goods>>() {}.getType();
            goodsList = gson.fromJson(goodsListStr,type);

            if (goodsList.size() == 0){
                lv_shopdetial_list.setVisibility(View.GONE);
                no_data_view.setVisibility(View.VISIBLE);
            }else {
                lv_shopdetial_list.setVisibility(View.VISIBLE);
                no_data_view.setVisibility(View.GONE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ShopdetialAdapter customAdapter = new ShopdetialAdapter(this, goodsList, R.layout.shopdetial_list);
        lv_shopdetial_list.setAdapter(customAdapter);
    }
}
