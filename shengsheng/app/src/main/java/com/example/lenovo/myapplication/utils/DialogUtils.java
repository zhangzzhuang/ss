package com.example.lenovo.myapplication.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.lenovo.myapplication.R;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @User basi
 * @Date 2018/11/9
 * @Time 下午5:01
 * @Description ${DESCRIPTION}
 */
public class DialogUtils extends Dialog {

    private Context context;
    private String body;
    private String head;
    private boolean or;

    @BindView(R.id.ivGif)
    ImageView imageViewGif;

    @BindView(R.id.tvHead)
    TextView textViewHead;

    @BindView(R.id.tvBody)
    TextView textViewBody;


    /**
     * 消息体
     * @param context
     * @param bodyId
     * @param or
     */
    public DialogUtils(Context context, int bodyId , boolean or) {
        super(context,R.style.AlertDialog);
        this.context = context;
        this.body = context.getString(bodyId);
        this.or = or;

    }

    /**
     * 消息体和内容
     * @param context
     * @param bodyId
     * @param headId
     * @param or
     */
    public DialogUtils(Context context, int bodyId , int headId, boolean or) {
        super(context,R.style.AlertDialog);
        this.context = context;
        this.body = context.getString(bodyId);
        this.head = context.getString(headId);
        this.or = or;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        setCanceledOnTouchOutside(true);//点击阴影处退出dialog
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        if (!or){
            textViewHead.setTextColor(context.getResources().getColor(R.color.color_cd2021));
            textViewBody.setTextColor(context.getResources().getColor(R.color.color_cd2021));
        }

        if (TextUtils.isEmpty(head)){
            textViewHead.setVisibility(View.GONE);
        }else {
            textViewHead.setText(head);
        }
        if (TextUtils.isEmpty(body)){
            return;
        }else {
            textViewBody.setText(body);
        }
    }

    public void show(){
        super.show();
        if (or){
            Glide.with(context)
                    .load(R.drawable.img_success)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            if (resource instanceof GifDrawable){
                                GifDrawable gifDrawable = (GifDrawable) resource;
                                gifDrawable.setLoopCount(3);
                                imageViewGif.setImageDrawable(resource);
                                gifDrawable.start();
                            }
                        }
                    });
        }else {
            Glide.with(context)
                    .load(R.drawable.img_falied)
                    .into(imageViewGif);
        }
    }
}
