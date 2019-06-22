package com.example.lenovo.myapplication.mvp.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.myapplication.mvp.main.view.activity.Data;
import com.example.lenovo.myapplication.mvp.main.model.Goods;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.mvp.user.activity.GoodsdetialActivity;

import java.util.List;

/**
 * Created by lenovo on 2018/5/23.
 */

public class ShopdetialAdapter extends BaseAdapter {

    private Context context;        //上下文环境
    private List<Goods> dataSource; //数据源
    private int shopdetial_list;//声明列表项的布局

    //声明列表项里的控件
    public ShopdetialAdapter(Context context, List<Goods> dataSource, int shopdetail_list) {
        this.context = context;
        this.dataSource = dataSource;
        this.shopdetial_list = shopdetail_list;
    }

    @Override
    public int getCount() {

        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (null == convertView){
            //加载布局文件
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(shopdetial_list,null);
        }

        ImageView imggoods = convertView.findViewById(R.id.imggoods);
        TextView goods_name = convertView.findViewById(R.id.goods_name);
        TextView goods_price = convertView.findViewById(R.id.goods_price);
        Glide.with(convertView.getContext())
                .load(Data.urlImageBusiness+dataSource.get(position).getImage())
                .into(imggoods);
        goods_name.setText(dataSource.get(position).getName());
        goods_price.setText(dataSource.get(position).getInfo());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,GoodsdetialActivity.class);
                intent.putExtra("goods",dataSource.get(position));
                context.startActivity(intent);
            }
        });
        return convertView; // 返回列表项
    }
}