package com.example.lenovo.myapplication.mvp.base.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.SupportActivity;

import com.example.lenovo.myapplication.mvp.base.inf.BaseView;
import com.example.lenovo.myapplication.mvp.base.presenter.BasePresenter;

/**
 * @User basi
 * @Date 2019/1/3
 * @Time 下午3:02
 * @Description ${含有数据请求的activity基类}
 */
public abstract class BaseActivity<V,T extends BasePresenter<V>> extends SimpleActivity implements BaseView {

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());

        mPresenter = createPresenter();

        if (mPresenter != null){
            mPresenter.attachView((V)this);
        }


        initView();

        initData();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mPresenter != null){
            mPresenter.detachView();
        }
    }


    /**
     * 设置资源id
     * @return layoutId
     */
    protected abstract int setLayoutId();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 创建Presenter
     * @return Presenter
     */
    protected abstract T createPresenter();


}

