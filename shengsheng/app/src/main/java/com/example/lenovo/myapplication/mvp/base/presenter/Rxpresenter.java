package com.example.lenovo.myapplication.mvp.base.presenter;

import com.example.lenovo.myapplication.event.RxBus;
import com.example.lenovo.myapplication.mvp.base.inf.BaseView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @User basi
 * @Date 2019/1/3
 * @Time 下午5:34
 * @Description ${DESCRIPTION}
 */
public class Rxpresenter<T extends BaseView> extends BasePresenter<T>{

    protected T mView;

    protected CompositeDisposable mCompositeDisposable;

    protected void unSubscribe(){
        if (mCompositeDisposable != null){
            mCompositeDisposable.dispose();
        }
    }

    protected void addSubscribe(Disposable disposable){
        if (mCompositeDisposable == null){
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    protected <U> void addRxBusSubscribe(Class<U> eventType , Consumer<U> act){
        if (mCompositeDisposable == null){
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(RxBus.getDefault().toDefaultFlowable(eventType,act));
    }

    @Override
    public void attachView(T mView) {
        this.mView = mView;
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }
}
