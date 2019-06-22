package com.example.lenovo.myapplication.mvp.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.myapplication.mvp.main.view.activity.Data;
import com.example.lenovo.myapplication.mvp.main.model.Goods;
import com.example.lenovo.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends Activity {
    private List<Goods> goodsList;
    private String goodsListStr;
    private EditText etSousuo;
    private String name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sousuoye);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ImageView search_back = findViewById(R.id.search_back);
        search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.this.finish();
            }
        });

        ImageView imageView = findViewById(R.id.ivsousuoye);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    etSousuo = findViewById(R.id.etSousuoye);
                    name = etSousuo.getText().toString();
                    if (name.equals("")){
                        Toast.makeText(SearchActivity.this,"你还没有输入要搜索的东西！！",Toast.LENGTH_SHORT).show();
                    }else {
                        OkHttpClient okHttpClient = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url(Data.sousuoGoods+name)
                                .build();
                        Call call = okHttpClient.newCall(request);
                        try {
                            Response response = call.execute();
                            goodsListStr = response.body().string();
                            Log.e("goodsListStr",goodsListStr);

                            Gson gson = new Gson();
                            Type type = new TypeToken<List<Goods>>(){}.getType();
                            goodsList = gson.fromJson(goodsListStr,type);
                            if (goodsList.size()< 0){
                                viewTextImg(v,etSousuo);
                                etSousuo.setText("");
                            }else{
                                Intent intent = new Intent();
                                intent.putExtra("goodsListStr",goodsListStr);
                                intent.setClass(SearchActivity.this,SearchShopdetialActivity.class);
                                startActivity(intent);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
        }


    public void viewTextImg(View view, TextView tv){
        Toast toast = new Toast(this);
        TextView textView= new TextView(this);
        ImageView imageView = new ImageView(this);
        imageView.setMaxWidth(500);
        imageView.setMaxHeight(900);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        textView.setTextSize(25);
        textView.setText("你搜索的'"+tv.getText().toString()+"'没有，去换个东西试试");
        imageView.setImageResource(R.drawable.bg);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.addView(imageView);
        linearLayout.addView(textView);
        toast.setView(linearLayout);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

}
