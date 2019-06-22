package com.example.lenovo.myapplication.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.widget.GlideApp;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

import static com.scwang.smartrefresh.layout.util.DensityUtil.dp2px;

/**
 * @User basi
 * @Date 2018/11/21
 * @Time 下午6:17
 * @Description ${DESCRIPTION}
 */
public class DefaultRefreshHeader extends RelativeLayout implements RefreshHeader {


    public DefaultRefreshHeader(Context context) {
        super(context);
        initView(context);
    }

    public DefaultRefreshHeader(Context context, AttributeSet attrs) {
        super(context,attrs);
        initView(context);
    }

    public DefaultRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        ImageView ivLoding = (ImageView)View.inflate(getContext(), R.layout.img_view_loading,null);
        addView(ivLoding);
        LayoutParams params = (LayoutParams) ivLoding.getLayoutParams();
        params.width = dp2px(50);
        params.height = dp2px(50);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        GlideApp.with(context).load(R.drawable.refresh_loading).into(ivLoding);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {

    }

    @Override
    public void onPulling(float percent, int offset, int height, int extendHeight) {

    }

    @Override
    public void onReleasing(float percent, int offset, int height, int extendHeight) {

    }

    @Override
    public void onReleased(RefreshLayout refreshLayout, int height, int extendHeight) {

    }

    @Override
    public void onStartAnimator(RefreshLayout refreshLayout, int height, int extendHeight) {

    }

    @Override
    public int onFinish(RefreshLayout refreshLayout, boolean success) {
        return 0;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {

    }
}
