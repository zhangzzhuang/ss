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
import com.example.lenovo.myapplication.mvp.main.model.Goods;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.mvp.user.activity.GoodsdetialActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.example.lenovo.myapplication.mvp.main.view.activity.Data.BusinessUrlImage;

public class ShopGoodsTypeAdapter extends BaseAdapter {

    private Context context;        //上下文环境
    private List<Goods> data; //数据源
    private int goods_list;

    public ShopGoodsTypeAdapter(Context context, List<Goods> data, int goods_list) {
        this.context = context;
        this.data = data;
        this.goods_list = goods_list;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.shopgoodstypelayout_list,null);
        }
        TextView goodstype_name = convertView.findViewById(R.id.goodstype_name);
        ImageView goodstype_img = convertView.findViewById(R.id.goodstype_img);
        TextView goodstype_jieshao = convertView.findViewById(R.id.goodstype_jieshao);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TextView goodstype_time = convertView.findViewById(R.id.goodstype_time);


        final Goods goods=data.get(position);
        goodstype_name.setText(goods.getName());
        Glide.with(context)
                .load(BusinessUrlImage+goods.getImage())
                .into(goodstype_img);

        goodstype_time.setText(df.format(goods.getStarttime()));
        goodstype_jieshao.setText(goods.getInfo());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,GoodsdetialActivity.class);
                intent.putExtra("goods",goods);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
