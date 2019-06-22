package com.example.lenovo.myapplication.mvp.user.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.mvp.main.view.activity.Data;
import com.example.lenovo.myapplication.mvp.user.model.PingLun;

import java.util.List;

/**
 * @User basi
 * @Date 2019/4/18
 * @Time 8:47 PM
 * @Description ${DESCRIPTION}
 */
public class GoodsDetialpinglunAdapter extends BaseQuickAdapter<PingLun,BaseViewHolder> {


    private Context mContext;
    public GoodsDetialpinglunAdapter(Context context){
        super(R.layout.frag_recycerview_item);
        this.mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, PingLun item) {
        ImageView imageView = helper.getView(R.id.image_pinglun);
        Glide.with(mContext)
                .load(Data.urlImage+item.getImage())
                .into(imageView);
        helper.setText(R.id.txt_userName,item.getUsername());
        helper.setText(R.id.txt_review,item.getContent());
    }

}
