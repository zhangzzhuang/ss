package com.example.lenovo.myapplication.mvp.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.myapplication.mvp.main.view.activity.Data;
import com.example.lenovo.myapplication.mvp.main.model.Goods;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.mvp.user.adapter.ShopGoodsTypeAdapter;
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

public class ShopGoodsTypeActivity extends Activity {
    private String goodstype_listStr;
    private List<Goods> goodstypeList;
    List typeList = new ArrayList();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopgoodstypelayout);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //返回按钮
        Button backtofaxian = findViewById(R.id.backtofaxian);
        backtofaxian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //获取类别

        Intent getIntent = getIntent();
        String typename = getIntent.getStringExtra("name");
        TextView goodstype_type = findViewById(R.id.goodstype_type);
        goodstype_type.setText(typename);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Data.PublicUrl+"/sx/SouSuoGoods")
                .build();

        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            goodstype_listStr = response.body().string();

            Gson gson = new Gson();
            Type type = new TypeToken<List<Goods>>(){}.getType();
            goodstypeList = gson.fromJson(goodstype_listStr,type);
            if (goodstypeList.size() == 0){
                Toast.makeText(this,"done",Toast.LENGTH_SHORT).show();
                finish();
            }
            else {
                Log.e("goodstypeList",goodstype_listStr);
                for (int i=0;i<goodstypeList.size();i++){
                    if (goodstypeList.get(i).getType().equals(typename)){
                         typeList.add(goodstypeList.get(i));
                    }
                }
                Log.e("typeList",typeList.toString());

                ListView listView = findViewById(R.id.goodtype_lv);
                ShopGoodsTypeAdapter adapter = new ShopGoodsTypeAdapter(ShopGoodsTypeActivity.this,typeList,
                        R.layout.shopgoodstypelayout_list);
                listView.setAdapter(adapter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
