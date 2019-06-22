package com.example.lenovo.myapplication.mvp.business.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.myapplication.mvp.business.adapter.BusinessGoodsAdapter;
import com.example.lenovo.myapplication.mvp.business.model.Business;
import com.example.lenovo.myapplication.mvp.main.view.activity.Data;
import com.example.lenovo.myapplication.mvp.main.model.Goods;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.utils.RoundImageView;
import com.example.lenovo.myapplication.manager.BusinessManage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lenovo on 2018/5/23.
 */

public class BuinessActivity extends Activity {
    private TextView name;//侧边栏商品名
    private RoundImageView img;//侧边栏头像
    private String goodsString; //gson字符串
    private GridView gridView;  //商品列表
    private ImageView imageView; //商铺头像
    private TextView textView;//商铺名
    private ImageButton Add_goods;
    private Button tuichudenglu;//退出登录按钮
    private List<Goods> dataList = new ArrayList<>();
    private LinearLayout no_data_view;
    private BusinessGoodsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buiness_layout);
        gridView=findViewById(R.id.Business_Goods_Gv);
        imageView=findViewById(R.id.Business_Image_top);
        Add_goods=findViewById(R.id.add_goods_button);
        no_data_view = findViewById(R.id.no_data_view);
         Glide.with(BuinessActivity.this)
                .load(Data.urlImageBusiness + BusinessManage.getInstance().getBusinessInfo(this).getImage())
                .into(imageView);

        textView=findViewById(R.id.Business_top_name);
        textView.setText(BusinessManage.getInstance().getBusinessInfo(this).getShop_name());
        name = findViewById(R.id.name_business);
        name.setText(BusinessManage.getInstance().getBusinessInfo(this).getShop_name());
        img = findViewById(R.id.image_business);
        Glide.with(BuinessActivity.this).load(Data.urlImageBusiness + BusinessManage.getInstance().getBusinessInfo(this).getImage())
                .into(img);
        Log.e("img",Data.urlImageBusiness + BusinessManage.getInstance().getBusinessInfo(this).getImage());
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        getBusinessList();
        Add_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BuinessActivity.this,AddGoodsActivity.class);
                startActivity(intent);
            }
        });

        tuichudenglu = findViewById(R.id.tuichu_business);
        tuichudenglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder AdBuilder =
                        new AlertDialog.Builder(BuinessActivity.this);
                Log.e("okk","okk");
                AdBuilder.setTitle("是否确认要退出当前账户");
                AdBuilder.setNegativeButton("取消",null);
                AdBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        BusinessManage.getInstance().delBussinessInfo(BuinessActivity.this);
                        Intent intent = new Intent(BuinessActivity.this,BusinessLoginActivity.class);
                        BuinessActivity.this.startActivity(intent);
                        BuinessActivity.this.finish();
                    }
                });
                AdBuilder.show();
            }

        });
    }

    public static void readGo(Context context){
        Intent intent = new Intent(context,BuinessActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBusinessList();
    }

    private void getBusinessList(){
        int id= Integer.parseInt(BusinessManage.getInstance().getBusinessInfo(this).getId());
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request=new Request.Builder()
                .url(Data.url+"GetBusinessGoods?business_id="+id)
                .build();
        Call call=okHttpClient.newCall(request);
        try {
            Response response=call.execute();
            goodsString=response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Goods>>(){}.getType();
        dataList=gson.fromJson(goodsString,type);
        if (dataList.size()>0){
            gridView.setVisibility(View.VISIBLE);
            no_data_view.setVisibility(View.GONE);
            adapter = new BusinessGoodsAdapter(BuinessActivity.this,dataList);
            gridView.setAdapter(adapter);
        }else {
            gridView.setVisibility(View.GONE);
            no_data_view.setVisibility(View.VISIBLE);
        }
    }
}
