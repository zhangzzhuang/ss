package com.example.lenovo.myapplication.mvp.base.inf;

/**
 * @User basi
 * @Date 2019/1/3
 * @Time 下午2:38
 * @Description ${View基类}
 */
public interface BaseView {

    /**
     * 显示加载弹框
     */
   void showLoading();

    /**
     * 是否正在显示加载框
     */
   void isShowLoading();

    /**
     * 隐藏加载弹框
     */
   void hideLoading();

    /**
     * 显示错误信息
     */
   void showError();

    /**
     * 显示空页面
     */
   void showEmpty();

    /**
     * 显示消息
     * @param msg
     */
   void showMsg(String msg);

    /**
     * 显示消息
     * @param msgResId
     */
    void showMsg(int msgResId);

    /**
     * 关闭页面
     */
    void closePage();
}
