package com.example.lenovo.myapplication.mvp.business.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.myapplication.manager.BusinessManage;
import com.example.lenovo.myapplication.mvp.main.view.activity.Data;
import com.example.lenovo.myapplication.mvp.main.model.Goods;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.utils.DialogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lenovo on 2018/5/26.
 */

public class GoodsContentActivity extends Activity {
    private TextView Goods_name;
    private ImageView Goods_image;
    private TextView Goods_info;
    private TextView Collet_num;
    private TextView Goods_type;
    private Button Delete_Goods;
    private Button update_goods;
    private ImageView return_business;
    private static final int DELSUC = 0;

    private String goodsString; //gson字符串
    private String position;
    private Goods goods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_layout);

        initView();

        Intent intent=getIntent();
        goods = new Goods();
        goods= (Goods) intent.getSerializableExtra("data");
        position = intent.getStringExtra("position");
        setView(goods);

        update_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoodsContentActivity.this,GoodsUpdateActivity.class);
                intent.putExtra("goods",goods);
                startActivity(intent);
            }
        });
        Glide.with(GoodsContentActivity.this)
                .load(Data.urlImageBusiness+goods.getImage())
                .into(Goods_image);
        Log.e("GoodsContentActivity",Data.urlImageBusiness+goods.getImage());
        return_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsContentActivity.this.finish();
            }
        });
        Delete_Goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delGood(String.valueOf(goods.getId()),String.valueOf(goods.getBusiness_id()));
            }
        });
    }

    private Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case DELSUC:
                    final DialogUtils dialogUtils = new DialogUtils(GoodsContentActivity.this,R.string.success_del,true);
                    dialogUtils.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialogUtils.dismiss();
                            GoodsContentActivity.this.startActivity(new Intent(GoodsContentActivity.this,BuinessActivity.class));
                            GoodsContentActivity.this.finish();
                        }
                    },3000);
                    break;
                default:
                    break;
            }
        }
    };

    private void delGood(String good_id,String business_id){

        final OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(Data.url+"DelGood?goods_id="+good_id +"&business_id="+business_id)
                .build();
        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Response response = null;
                try {
                    //回调
                    response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Message message = new Message();
                        message.what = DELSUC;
                        mhandler.sendMessage(message);
                    } else {
                        throw new IOException("Unexpected code:" + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

        private void initView(){
            return_business=findViewById(R.id.return_business_index);
            Goods_image=findViewById(R.id.Goods_content_image);
            Goods_name=findViewById(R.id.Goods_content_name);
            Goods_type=findViewById(R.id.Goods_content_type);
            Goods_info=findViewById(R.id.Goods_content_info);
            Collet_num=findViewById(R.id.Goods_content_collect_num);
            Delete_Goods=findViewById(R.id.delete_goods);
            update_goods = findViewById(R.id.update_goods);
        }

        private void setView(Goods goods){
                Goods_name.setText(goods.getName());
                Goods_info.setText(goods.getInfo());
                Goods_type.setText(goods.getType());
                Collet_num.setText(String.valueOf(goods.getCollect_num()));
        }

        private void goodContentRefresh(){
            List<Goods> dataList = new ArrayList<>();

            String id= BusinessManage.getInstance().getBusinessInfo(GoodsContentActivity.this).getId();
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
            setView(dataList.get(Integer.parseInt(position)));

        }

    @Override
    protected void onResume() {
        super.onResume();
        goodContentRefresh();
    }
}
