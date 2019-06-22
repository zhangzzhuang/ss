package com.example.lenovo.myapplication.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.widget.GlideApp;


/**
 * @User basi
 * @Date 2018/11/27
 * @Time 下午4:02
 * @Description ${成功提示dialog}
 */
public class LoadingDialog {

    public static Dialog loadingDialog(Context context ,String msg){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_loading_dialog,null);
        ImageView ivLoading = view.findViewById(R.id.iv_loading);
        GlideApp.with(context).load(R.mipmap.loading).into(ivLoading);
        Dialog dialog = new Dialog(context,R.style.AlertDialog);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public static void showImageWithDialogEvent(Activity activity, int res, @StringRes int msg, DialogInterface.OnDismissListener listener){
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.dialog_layout,null);
        ((ImageView)view.findViewById(R.id.ivGif)).setImageResource(res);
        ((TextView)view.findViewById(R.id.tvBody)).setText(msg);
        Dialog dialog = new Dialog(activity,R.style.showImageDialogTransparent);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view,new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        dialog.setOnDismissListener(listener);
        dialog.show();
    }


    /**
     * 设置透明度
     * @param activity
     * @param a
     */
    public static void setAlpha(Activity activity,float a){
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = a;
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);
    }
}
