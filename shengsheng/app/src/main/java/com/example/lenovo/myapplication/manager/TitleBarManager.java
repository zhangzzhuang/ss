package com.example.lenovo.myapplication.manager;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.myapplication.R;


/**
 * @User Jian.Wang
 * @Date 2018/8/20
 * @Time 下午8:50
 * @Version 1.0
 * @Description TitleBar管理类
 */
public class TitleBarManager implements View.OnClickListener {
    private Context mContext;
    private View mTitleBar;
    private TitleBarType mTitleBarType;
    private LinearLayout btnBack;
    private TextView tvTitleName;
    OnTitleBarBackClickListener mOnTitleBarBackClickListener;
    OnTitleBarShareClickListener mOnTitleBarShareClickListener;

    public TitleBarManager(Context context) {
        mContext = context;
    }

    public void setTitleBar(View titleBar) {
        mTitleBar = titleBar;
    }

    /**
     * 设置标题栏返回键点击事件
     *
     * @param onTitleBarBackClickListener
     */
    public void setOnTitleBarBackClickListener(OnTitleBarBackClickListener onTitleBarBackClickListener) {
        mOnTitleBarBackClickListener = onTitleBarBackClickListener;
    }

    /**
     * 设置标题栏结束按钮点击事件
     *
     * @param onTitleBarShareClickListener
     */
    public void setOnTitleBarShareClickListener(OnTitleBarShareClickListener onTitleBarShareClickListener) {
        mOnTitleBarShareClickListener = onTitleBarShareClickListener;
    }

    /**
     * 设置title类型
     *
     * @param titleType
     */
    public void setTitleType(TitleBarType titleType) {
        updateTitleView(titleType);
    }

    /**
     * 设置title文字
     *
     * @param titleId
     */
    public void setTitleId(@StringRes int titleId) {
        if (tvTitleName == null) {
            tvTitleName = (TextView) mTitleBar.findViewById(R.id.tv_title);
        }
        if (tvTitleName != null) {
            tvTitleName.setText(titleId);
        }
    }

    /**
     * 设置title文字
     *
     * @param titleName
     */
    public void setTitleName(String titleName) {
        if (tvTitleName == null) {
            tvTitleName = (TextView) mTitleBar.findViewById(R.id.tv_title);
        }
        if (tvTitleName != null) {
            tvTitleName.setText(titleName);
        }
    }

    /**
     * 设置标题文字不显示
     */
    public void setTitleGone(){
        if (tvTitleName == null){
            tvTitleName = (TextView) mTitleBar.findViewById(R.id.tv_title);
        }
        if (tvTitleName != null){
            tvTitleName.setVisibility(View.GONE);
        }
    }


    /**
     * 更新titleView
     */
    public void updateTitleView(TitleBarType titleType) {
        mTitleBarType = titleType;
        if (tvTitleName == null) {
            tvTitleName = (TextView) mTitleBar.findViewById(R.id.tv_title);
        }
        if (btnBack == null) {
            btnBack =  mTitleBar.findViewById(R.id.btn_back);
        }
        switch (titleType) {
            case TITLE_TYPE_TEXT:
                btnBack.setVisibility(View.GONE);
                break;
            case TITLE_TYPE_BACK:
                btnBack.setVisibility(View.VISIBLE);
                btnBack.setOnClickListener(this);
                break;
            default:
                break;
        }
    }

    /**
     * 设置标题栏是否可点击
     *
     * @param clickable
     */
    public void setTitleBarClickable(boolean clickable) {
        mTitleBar.setClickable(clickable);
        if (clickable) {
            mTitleBar.setOnClickListener(this);
        }
    }

    /**
     * 设置标题栏背景色
     *
     * @param colorRes
     */
    public void setTitleBarColor(@ColorRes int colorRes) {
        mTitleBar.setBackgroundResource(colorRes);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                if (mOnTitleBarBackClickListener != null) {
                    mOnTitleBarBackClickListener.onBackClick();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 返回键点击回调
     */
    public interface OnTitleBarBackClickListener {
        void onBackClick();
    }

    /**
     * TitleBar点击回调
     */
    public interface OnTitleBarClickListener {
        void onTitleBarClick();
    }

    /**
     * 结束按钮点击回调
     */
    public interface OnTitleBarShareClickListener {
        void onShareCLick();
    }
}
