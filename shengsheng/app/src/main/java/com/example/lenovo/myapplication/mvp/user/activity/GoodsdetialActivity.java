package com.example.lenovo.myapplication.mvp.user.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lenovo.myapplication.manager.UserManage;
import com.example.lenovo.myapplication.mvp.main.view.activity.Data;
import com.example.lenovo.myapplication.mvp.main.model.Goods;
import com.example.lenovo.myapplication.mvp.user.adapter.GoodsDetialpinglunAdapter;
import com.example.lenovo.myapplication.mvp.user.model.PingLun;
import com.example.lenovo.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lenovo on 2018/5/28.
 */

public class GoodsdetialActivity extends Activity {

    private RecyclerView lv_goodsreview;
    private Goods goods = new Goods();
    public  ImageView goodsDetial_back;
    private ImageView imageView1;
    private ImageView imageViewuser;
    private TextView starttime;
    private TextView goods_price_detail;
    private TextView goods_type_detail;
    private TextView goods_name_detail;
    private TextView goods_info;
    private TextView endtime;
    private EditText edit_bg;
    private ImageView shoucangshangpin;
    private LinearLayout no_data_view;
    private Boolean flag = false;
    private  List<PingLun> pingLun = new ArrayList<>();
    private GoodsDetialpinglunAdapter goodsDetialpinglunAdapter;
    private SmartRefreshLayout refreshLayout;
    private String retStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsdetial);
        ButterKnife.bind(this);
        initView();

        Intent intent = getIntent();
        goods = (Goods) intent.getSerializableExtra("goods");


        if (focuOrNotGood()){
            shoucangshangpin.setImageDrawable(getResources().getDrawable(R.drawable.collected_good));
        }else {
            shoucangshangpin.setImageDrawable(getResources().getDrawable(R.drawable.not_collected_good));
        }

        goods_name_detail.setText(goods.getName());
        goods_type_detail.setText(goods.getType());
        goods_info.setText(goods.getInfo());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        starttime.setText(df.format(goods.getStarttime()));
        endtime.setText(df.format(goods.getEndtime()));

        Glide.with(GoodsdetialActivity.this).load(
                Data.urlImageBusiness+goods.getImage()
        ).into(imageView1);

        Glide.with(GoodsdetialActivity.this).load(
                Data.urlImage+Data.user.getImage()
        ).into(imageViewuser);

        if (TextUtils.isEmpty(goods.getPrice().toString())){
            goods_price_detail.setVisibility(View.GONE);
        }else {
            goods_price_detail.setText(goods.getPrice().toString());
        }

        setGoodPingLunItem(goods);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                upPullRefresh(refreshLayout);
                refreshLayout.finishLoadMore(2000);
            }
        });

    }


    public static void readGo(Context context,Goods goods){
        Intent intent  = new Intent(context,GoodsdetialActivity.class);
        intent.putExtra("goods",goods);
        context.startActivity(intent);

    }

    private void initView(){
        starttime = findViewById(R.id.starttime);
        imageView1=findViewById(R.id.Business_good_img);
        imageViewuser=findViewById(R.id.pinlun_head_touxing);
        goodsDetial_back = findViewById(R.id.goods_detial_back);
        goods_price_detail = findViewById(R.id.goods_price_detail);
        goods_type_detail = findViewById(R.id.goods_type_detail);
        goods_name_detail = findViewById(R.id.goods_name_detail);
        goods_info = findViewById(R.id.goods_info);
        endtime = findViewById(R.id.endtime);
        edit_bg = findViewById(R.id.pinglun_content);
        shoucangshangpin = findViewById(R.id.shoucangshangpin);
        lv_goodsreview = findViewById(R.id.lv_goodsreview);
        no_data_view = findViewById(R.id.no_data_view);
        refreshLayout = findViewById(R.id.refrfeshGoodDetialLayout);
        edit_bg = findViewById(R.id.pinglun_content);
    }

    @OnClick({R.id.goods_detial_back})
    public void backToFlooow(){
        this.finish();
    }

    @OnClick(R.id.review_submit)
    public void submitPingLun(){
                String pinglun_content = edit_bg.getText().toString();
                PingLun pingLun1 = new PingLun();
                pingLun1.setBusiness_id(goods.getBusiness_id());
                pingLun1.setContent(pinglun_content);
                pingLun1.setGoods_id(goods.getId());
                pingLun1.setUser_id(Data.user.getId());
                pingLun1.setImage(Data.user.getImage());
                pingLun1.setUsername(Data.user.getName());
                pingLun.add(pingLun1);
                Gson gson = new Gson();
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = RequestBody.create(
                        MediaType.parse("text/x-markdown; charset=utf-8"),gson.toJson(pingLun1));
                Request request = new Request.Builder()
                        .url(Data.url+"TianJiaPingLun")
                        .post(requestBody)
                        .build();
                Call call = okHttpClient.newCall(request);
                try {
                    call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                edit_bg.setText("");

                goodsDetialpinglunAdapter.setNewData(pingLun);
                Toast.makeText(GoodsdetialActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
    }

    private void upPullRefresh(RefreshLayout refreshLayout){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Data.url + "GetGoodsPinglun?goods_id="+goods.getId())
                .build();
        Call call = okHttpClient.newCall(request);
        String pinglunStr="";
        try {
            Response response = call.execute();
            pinglunStr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Type type=new TypeToken<List<PingLun>>(){}.getType();
        pingLun =gson.fromJson(pinglunStr,type);
        goodsDetialpinglunAdapter.setNewData(pingLun);
    }

    private void setGoodPingLunItem(Goods goods){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Data.url + "GetGoodsPinglun?goods_id="+goods.getId())
                .build();
        Call call = okHttpClient.newCall(request);
        String pinglunStr="";
        try {
            Response response = call.execute();
            pinglunStr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Type type=new TypeToken<List<PingLun>>(){}.getType();
        pingLun =gson.fromJson(pinglunStr,type);
        if (pingLun.size()==0){
            lv_goodsreview.setVisibility(View.GONE);
            no_data_view.setVisibility(View.VISIBLE);
        }else {
            lv_goodsreview.setVisibility(View.VISIBLE);
            no_data_view.setVisibility(View.GONE);
        }
        goodsDetialpinglunAdapter = new GoodsDetialpinglunAdapter(GoodsdetialActivity.this);
        goodsDetialpinglunAdapter.setNewData(pingLun);
        lv_goodsreview.setAdapter(goodsDetialpinglunAdapter);
    }

    private Boolean focuOrNotGood(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Data.url + "FocuOrNotGood?user_id=" + UserManage.getInstance().getUserInfo(GoodsdetialActivity.this).getId() + "&goods_id=" + goods.getId())
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                retStr = response.body().string();
                flag = "true".equals(retStr)?true:false;
                Log.e("ASSSSA", String.valueOf(flag));
            }
        });
        return flag;
    }

    private void toCollectGood(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Data.url + "ShouCangGoods?user_id="+UserManage.getInstance().getUserInfo(GoodsdetialActivity.this).getId()+"&goods_id="+goods.getId())
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        flag = focuOrNotGood();
        shoucangshangpin.setImageDrawable(getResources().getDrawable(R.drawable.collected_good));
        Toast.makeText(GoodsdetialActivity.this,"收藏成功！",Toast.LENGTH_SHORT).show();
    }

    private void cancalCollectGood(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Data.url + "CancelCollectionGood?user_id="+UserManage.getInstance().getUserInfo(GoodsdetialActivity.this).getId()+"&goods_id="+goods.getId())
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        flag = focuOrNotGood();
        shoucangshangpin.setImageDrawable(getResources().getDrawable(R.drawable.not_collected_good));
        Toast.makeText(GoodsdetialActivity.this,"取消收藏！",Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.shoucangshangpin)
    public void shouCanShangPinBtn(){
        flag = focuOrNotGood();
        if (flag){
            cancalCollectGood();
        }else {
            toCollectGood();
        }
    }

}
