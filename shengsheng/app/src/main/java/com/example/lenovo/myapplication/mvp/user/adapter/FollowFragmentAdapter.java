package com.example.lenovo.myapplication.mvp.user.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.lenovo.myapplication.mvp.main.model.Goods;
import com.example.lenovo.myapplication.mvp.main.view.activity.Data;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.mvp.user.activity.GoodsdetialActivity;


/**
 * @User basi
 * @Date 2019/4/13
 * @Time 10:52 PM
 * @Description ${DESCRIPTION}
 */
public class FollowFragmentAdapter extends BaseQuickAdapter<Goods,BaseViewHolder> {


    private Context mContext;
    public FollowFragmentAdapter(Context context){
        super(R.layout.frag_recycerview_item);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final Goods item) {
        ImageView imageView = helper.getView(R.id.image_item);
        Glide.with(mContext)
                .load(Data.urlImage + item.getImage())
                .into(imageView);
        helper.setText(R.id.name_item,item.getName());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsdetialActivity.readGo(mContext,item);
            }
        });
    }

}
