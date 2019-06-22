package com.example.lenovo.myapplication.mvp.user.adapter;

import android.app.Activity;
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

import java.util.List;

import static com.example.lenovo.myapplication.mvp.main.view.activity.Data.BusinessUrlImage;

public class SearchShopdetialAdapter extends BaseAdapter {
    private Activity context;
    private List<Goods> data;
    private int item_layout_id;

    public SearchShopdetialAdapter(Activity context, int item_layout_id){
        this.context = context;
        this.item_layout_id = item_layout_id;
    }

    public void setDataSource( List<Goods> data) {
        this.data = data;
        this.notifyDataSetChanged();
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
        if (convertView==null){
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(R.layout.sousuoshopdetialactivity_list,null);
        }
        TextView txt_shopName = convertView.findViewById(R.id.shopName);
        ImageView imageView = convertView.findViewById(R.id.shopImg);
        View bottom_line = convertView.findViewById(R.id.bottom_line);
        if (position < data.size()-1){
            bottom_line.setVisibility(View.VISIBLE);
        }
        final Goods goods=data.get(position);

        txt_shopName.setText(goods.getName());
        Glide.with(context)
                .load(BusinessUrlImage+goods.getImage())
                .into(imageView);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsdetialActivity.readGo(context,goods);
                context.finish();
            }
        });
        return convertView;

    }
}
