/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dhgate.dt.library.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.blankj.utilcode.util.NetworkUtils;
import com.dhgate.dt.library.network.NetChangeObserver;
import com.dhgate.dt.library.network.NetStateReceiver;
import com.dhgate.dt.library.utils.StatusBarUtil;

/**
 * @User Jian.Wang
 * @Date 2018/8/19
 * @Time 下午11:11
 * @Version 1.0
 * @Description BaseCompatActivity
 */
public abstract class BaseCompatActivity extends AppCompatActivity {
    /**
     * Log tag
     */
    protected static String TAG = null;

    /**
     * context
     */
    protected Activity mContext = null;

    /**
     * 网络状态
     */
    protected NetChangeObserver mNetChangeObserver = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        TAG = this.getClass().getSimpleName();
        if (getContentViewRes() != 0) {
            setContentView(getContentViewRes());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }

        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }

        if (setStatusBarColorRes() != 0) {
            StatusBarUtil.setColor(this, getResources().getColor(setStatusBarColorRes()), 0);
        }
        AppManager.getInstance().addActivity(this);

        mNetChangeObserver = new NetChangeObserver() {
            @Override
            public void onNetConnected(NetworkUtils.NetworkType type) {
                super.onNetConnected(type);
                onNetWorkConnect();
            }

            @Override
            public void onNetDisConnect() {
                super.onNetDisConnect();
                onNetWorkDisConnect();
            }
        };
        NetStateReceiver.registerObserver(mNetChangeObserver);
        initViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        AppManager.getInstance().removeActivity(this);
        NetStateReceiver.removeRegisterObserver(mNetChangeObserver);
        super.onDestroy();
    }

    /**
     * 获取传入的Bundle对象
     *
     * @param extras
     */
    protected abstract void getBundleExtras(Bundle extras);

    /**
     * 绑定布局资源文件
     *
     * @return id of layout resource
     */
    protected abstract int getContentViewRes();

    /**
     * 视图初始化
     */
    protected abstract void initViews();


    /**
     * 设置沉浸式状态栏颜色
     */
    protected abstract int setStatusBarColorRes();


    /**
     * 网络连接
     */
    protected abstract void onNetWorkConnect();

    /**
     * 网络断开
     */
    protected abstract void onNetWorkDisConnect();

    /**
     * 打开Activity并且携带Bundle
     *
     * @param clazz
     * @param bundle
     */
    public static void readyGo(Activity context, Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * 打开Activity后销毁当前Activity
     *
     * @param clazz
     */
    public static void readyGoThenKill(Activity context, Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
        context.finish();
    }

    /**
     * 打开新的Activity并且携带Bundle后销毁当前Activity
     *
     * @param clazz
     * @param bundle
     */
    public static void readyGoThenKill(Activity context, Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
        context.finish();
    }

    /**
     * 打开带返回值的Activity
     *
     * @param clazz
     * @param requestCode
     */
    public static void readyGoForResult(Activity context, Class<?> clazz, int requestCode) {
        Intent intent = new Intent(context, clazz);
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 打开带返回值的Activity并且携带Bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    public static void readyGoForResult(Activity context, Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 打开Activity，只可在lanuch里面调用
     *
     * @param clazz
     */
    public static void readyGo(Activity context, Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }



}
