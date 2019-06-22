package com.example.lenovo.myapplication.mvp.base.presenter;

import com.example.lenovo.myapplication.mvp.base.inf.BaseView;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @User basi
 * @Date 2019/1/3
 * @Time 下午2:53
 * @Description ${DESCRIPTION}
 * 使用弱引用，对处理内存泄露起到很好的作用
 */
public abstract class BasePresenter<T>{

    /**
     * Veiw接口类型的弱引用
     */
    private Reference<T> mViewRef;

    /**
     * 绑定视图
     * @param mView 视图
     */
    public void attachView(T mView){
        mViewRef = new WeakReference<>(mView);
    }

    protected T getView(){
        if (isViewAttached()){
            return mViewRef.get();
        }else {
           return null;
        }
    }

    private boolean isViewAttached(){
        return mViewRef != null && mViewRef.get() != null;
    }

    /**
     * 解绑视图
     */
    public void detachView(){
        if (mViewRef != null){
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
