package com.example.lenovo.myapplication.mvp.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


import com.example.lenovo.myapplication.mvp.main.model.Goods;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.mvp.user.adapter.SearchShopdetialAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SearchShopdetialActivity extends Activity{
    private List<Goods> goodsList;
    private String goodsListStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sousuoshopdetialactivity);


        Intent getIntent = getIntent();
        goodsListStr = getIntent.getStringExtra("goodsListStr");
        Gson gson = new Gson();
        Type type = new TypeToken<List<Goods>>(){}.getType();

        goodsList = gson.fromJson(goodsListStr,type);


        ListView lv = findViewById(R.id.lvShop);
        SearchShopdetialAdapter shopdetialAdapter = new SearchShopdetialAdapter(SearchShopdetialActivity.this,
              R.layout.sousuoshopdetialactivity_list  );
        shopdetialAdapter.setDataSource(goodsList);
        lv.setAdapter(shopdetialAdapter);


        //返回按钮
        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
