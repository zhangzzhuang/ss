package com.example.lenovo.myapplication.mvp.base.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lenovo.myapplication.mvp.base.presenter.BasePresenter;

import org.greenrobot.eventbus.EventBus;

/**
 * @User basi
 * @Date 2019/1/3
 * @Time 下午3:14
 * @Description ${DESCRIPTION}
 */
public abstract class BaseFragment<V ,T extends BasePresenter<V>> extends Fragment {

    protected T mPresenter;

    protected View mRootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = createPresenter();

        if (mPresenter !=null){
            mPresenter.attachView((V)this);
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(setLayoutId(),container,false);

        initView();

        initData();

        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


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

    /**
     * 注册EventBus
     */
    protected void register(){
        EventBus.getDefault().register(this);
    }

    /**
     * 注销EventBus
     */
    protected void unRegister(){
        EventBus.getDefault().unregister(this);
    }
}
