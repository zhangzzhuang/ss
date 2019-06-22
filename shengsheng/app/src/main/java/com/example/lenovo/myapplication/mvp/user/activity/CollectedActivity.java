package com.example.lenovo.myapplication.mvp.user.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.lenovo.myapplication.manager.UserManage;
import com.example.lenovo.myapplication.mvp.user.adapter.CollectedAdapter;
import com.example.lenovo.myapplication.mvp.main.view.activity.Data;
import com.example.lenovo.myapplication.mvp.main.model.Goods;
import com.example.lenovo.myapplication.R;
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
 * Created by dingin on 2018/5/27.
 */

public class CollectedActivity extends Activity{
    private String goodsListStr;
    private List<Goods> goodsList = new ArrayList<>();
    private LinearLayout no_data_view;
    private ListView listView;
    private ImageView col_back;
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collected);
        initView();
        col_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        OkHttpClient okHttpClient = new OkHttpClient();
         Request request = new Request.Builder()
                .url(Data.url + "ShouCangYeMian?user_id="+UserManage.getInstance().getUserInfo(CollectedActivity.this).getId())
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                goodsListStr = response.body().string();
                Gson gson = new Gson();
                Type type = new TypeToken<List<Goods>>(){}.getType();
                goodsList = gson.fromJson(goodsListStr,type);

                if (goodsList.size() == 0){
                    listView.setVisibility(View.GONE);
                    no_data_view.setVisibility(View.VISIBLE);
                }else {
                    listView.setVisibility(View.VISIBLE);
                    no_data_view.setVisibility(View.GONE);
                }

                CollectedActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CollectedAdapter goodsListAdapter = new CollectedAdapter(CollectedActivity.this, goodsList);
                        ListView listView = findViewById(R.id.lv_collected_list);
                        listView.setAdapter(goodsListAdapter);
                    }
                });
            }
        });



    }


    private void initView(){
        listView = findViewById(R.id.lv_collected_list);
        col_back =findViewById(R.id.col_back);
        no_data_view = findViewById(R.id.no_data_view);
    }
}
