package com.example.lenovo.myapplication.mvp.base.view.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.dhgate.dt.library.base.BaseCompatActivity;
import com.dhgate.dt.library.utils.StringUtils;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.manager.TitleBarManager;
import com.example.lenovo.myapplication.manager.TitleBarType;
import com.example.lenovo.myapplication.utils.LoadingDialog;


import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * @User basi
 * @Date 2019/1/3
 * @Time 下午6:45
 * @Description ${DESCRIPTION}
 */
public class SimpleActivity extends BaseCompatActivity implements TitleBarManager.OnTitleBarBackClickListener{

    protected View titleBar;
    protected TitleBarManager mTitleBarManager;

    Unbinder mUnbinder;

    DisposableSubscriber mStateDisposable;
    Disposable mScreenShotDisposable;
    protected boolean destory = false;
    private Dialog loadingDialog;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        titleBar = ButterKnife.findById(this, R.id.title_bar);
        if (titleBar != null) {
            mTitleBarManager = new TitleBarManager(this);
            mTitleBarManager.setTitleBar(titleBar);
        }
        mUnbinder = ButterKnife.bind(this);
    }

    protected void setTitleType(TitleBarType titleType) {
        if (mTitleBarManager != null) {
            mTitleBarManager.setTitleType(titleType);
            switch (titleType) {
                case TITLE_TYPE_BACK:
                    mTitleBarManager.setOnTitleBarBackClickListener(this);
                    break;
                default:
                    break;
            }
        }
    }

    protected void setTitleId(@StringRes int titleId) {
        if (mTitleBarManager != null) {
            mTitleBarManager.setTitleId(titleId);
        }
    }

    protected void setTitleName(String titleName) {
        if (mTitleBarManager != null) {
            mTitleBarManager.setTitleName(titleName);
        }
    }


    protected void setTitleBarColor(@ColorRes int colorRes) {
        if (mTitleBarManager != null) {
            mTitleBarManager.setTitleBarColor(colorRes);
        }
    }

    protected void setTitleGone() {
        if (mTitleBarManager != null) {
            mTitleBarManager.setTitleGone();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onDestroy() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        destory = true;
        hideProgressLoading();
        if (mStateDisposable != null && !mStateDisposable.isDisposed()) {
            mStateDisposable.dispose();
        }
        if (mScreenShotDisposable != null && !mScreenShotDisposable.isDisposed()) {
            mScreenShotDisposable.dispose();
        }
        super.onDestroy();
    }



    /**
     * 接收页面传值
     * @param extras
     */
    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewRes() {
        return 0;
    }

    @Override
    protected void initViews() {

    }

    /**
     * 设置状态栏
     * @return
     */
    @Override
    protected int setStatusBarColorRes() {
        return R.color.white;
    }

    /**
     * 网络连接
     */
    @Override
    protected void onNetWorkConnect() {

    }

    /**
     * 网络断开
     */
    @Override
    protected void onNetWorkDisConnect() {

    }

    @Override
    public void onBackClick() {
        finish();
    }

    /**
     * 显示Loading
     */
    protected void showProgressLoading() {
        if(loadingDialog==null){
            loadingDialog = LoadingDialog.loadingDialog(this,"");
        }
        if(!loadingDialog.isShowing()){
            loadingDialog.show();
        }

    }

    /**
     * 是否正在显示加载框
     * @return
     */
    protected boolean isProgressLoading(){
        return loadingDialog != null && loadingDialog.isShowing();
    }

    /**
     * 隐藏Loading
     */
    private void hideProgressLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }


    public void showMsg(String msg) {
        if (!StringUtils.isEmpty(msg)) {
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
        }
    }

    public void showMsg(int msg) {
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
