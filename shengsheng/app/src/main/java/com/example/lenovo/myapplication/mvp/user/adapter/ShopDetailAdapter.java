package com.example.lenovo.myapplication.mvp.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.myapplication.R;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2018/5/24.
 */

public class ShopDetailAdapter extends BaseAdapter {

    private Context context;        //上下文环境
    private List<Map<String,Object>> dataSource; //数据源
    private int discovery_list;   //声明列表项的布局

    //声明列表项里的控件
    public ShopDetailAdapter(Context context, List<Map<String, Object>> dataSource, int discovery_list) {
        this.context=context;
        this.dataSource=dataSource;
        this.discovery_list = discovery_list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView){
            //加载布局文件
            LayoutInflater mInflater = LayoutInflater.from(context);
            convertView = mInflater.inflate(discovery_list,null);
        }

        ImageView img_locationShop = convertView.findViewById(R.id.img_locationShop);
        TextView txt_shopName = convertView.findViewById(R.id.txt_shopName);
        // 给数据项填充数据
        final Map<String, Object> mItemData = dataSource.get(position);
        img_locationShop.setImageResource((int)mItemData.get("header"));
        txt_shopName.setText(mItemData.get("name").toString());
        // 给列表项中的控件注册事件监听器
        img_locationShop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
        return convertView; // 返回列表项
    }
}
