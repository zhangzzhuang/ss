package com.example.lenovo.myapplication.mvp.business.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.myapplication.mvp.business.activity.GoodsContentActivity;
import com.example.lenovo.myapplication.mvp.main.view.activity.Data;
import com.example.lenovo.myapplication.mvp.main.model.Goods;
import com.example.lenovo.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/5/25.
 */

public class BusinessGoodsAdapter extends BaseAdapter {
    private Activity content;
    private List<Goods> dataList = new ArrayList<>();
    public BusinessGoodsAdapter(Activity context, List<Goods> list){
        this.content=context;
        this.dataList=list;
    }
    @Override
    public int getCount() {
            return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView= content.getLayoutInflater().inflate(R.layout.business_item_layout,null);
        }
        ImageView imageView=convertView.findViewById(R.id.Business_topImage);
        TextView textView=convertView.findViewById(R.id.index_Goods_name);
        textView.setText(dataList.get(position).getName());
        Glide.with(content).load(
                Data.urlImageBusiness+dataList.get(position).getImage()
        ).into(imageView);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(content,GoodsContentActivity.class);
                intent.putExtra("data",dataList.get(position));
                intent.putExtra("position",String.valueOf(position));
                content.startActivity(intent);
            }
        });
        return convertView;
    }
}
