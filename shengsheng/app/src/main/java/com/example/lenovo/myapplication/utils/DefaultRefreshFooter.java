package com.example.lenovo.myapplication.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.widget.GlideApp;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

import static com.scwang.smartrefresh.layout.util.DensityUtil.dp2px;

/**
 * @User basi
 * @Date 2018/11/21
 * @Time 下午9:10
 * @Description ${DESCRIPTION}
 */
public class DefaultRefreshFooter extends RelativeLayout implements RefreshFooter {

    private Context mContext;
    private TextView mTextView;
    private ImageView mImageView;
    private boolean mNoMoredata;


    public DefaultRefreshFooter(Context context) {
        super(context);
        initView(context);
    }

    public DefaultRefreshFooter(Context context, AttributeSet attrs) {
        super(context,attrs);
        initView(context);
    }

    public DefaultRefreshFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView(context);
    }

    private void initView(Context context) {
        RelativeLayout footerLoading = (RelativeLayout) View.inflate(getContext(), R.layout.footer_loading,null);
        addView(footerLoading);
        mTextView = footerLoading.findViewById(R.id.footer_text);
        mImageView = footerLoading.findViewById(R.id.footer_img);
        LayoutParams params = (LayoutParams) footerLoading.getLayoutParams();
        params.width = LayoutParams.MATCH_PARENT;
        params.height = dp2px(50);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        GlideApp.with(context).load(R.drawable.refresh_loading).into(mImageView);

    }


    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        if (mNoMoredata!=noMoreData){
            mNoMoredata = noMoreData;
            if (noMoreData){
                mTextView.setVisibility(VISIBLE);
                mTextView.setText("没有更多加载信息");
                mImageView.setVisibility(GONE);
            }else {
                mTextView.setVisibility(GONE);
                mImageView.setVisibility(VISIBLE);
            }
        }
        return true;
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int extendHeight) {

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
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int extendHeight) {

    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
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
