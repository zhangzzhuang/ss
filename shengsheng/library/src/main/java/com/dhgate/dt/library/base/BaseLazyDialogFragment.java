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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;

import java.lang.reflect.Field;


/**
 * @User Jian.Wang
 * @Date 2018/8/19
 * @Time 下午11:11
 * @Version 1.0
 * @Description BaseLazyDialogFragment
 */
public abstract class BaseLazyDialogFragment extends DialogFragment {

    /**
     * Log tag
     */
    protected static String TAG = null;

    /**
     * context
     */
    protected Activity mContext = null;

    private boolean isFirstVisible = true;
    private boolean isViewCreated = false;
    private boolean isVisibleToUser = false;
    private boolean isFragmentVisible;

//    protected Unbinder mUnbinder;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getContentViewRes() != 0) {
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
            Window window = getDialog().getWindow();
            window.setGravity(getGravity());
            View view = inflater.inflate(getContentViewRes(), ((ViewGroup) window.findViewById(android.R.id.content)), false);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(ScreenUtils.getScreenWidth(), getHeight() == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : SizeUtils.dp2px(getHeight()));
            return view;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getContentViewRes();
        initViews();
        isViewCreated = true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisibleToUser && isFirstVisible) {
            onFirstUserVisible();
            isFirstVisible = false;
        } else if (!isFragmentVisible && !isFirstVisible && isVisibleToUser) {
            onUserVisible();
            isFragmentVisible = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isFragmentVisible) {
            onUserInvisible();
            isFragmentVisible = false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            if (isViewCreated) {
                if (isFirstVisible) {
                    onFirstUserVisible();
                    isFirstVisible = false;
                } else if (!isFragmentVisible) {
                    onUserVisible();
                }
            }
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            onUserInvisible();
            isFragmentVisible = false;
        }
    }

    /**
     * 绑定布局文件
     *
     * @return id of layout resource
     */
    protected abstract int getContentViewRes();

    /**
     * 初始化
     */
    protected abstract void initViews();

    /**
     * 初始化位置
     */
    protected abstract int getGravity();

    /**
     * 第一次打开时调用
     */
    protected abstract void onFirstUserVisible();

    /**
     * 可见时调用
     */
    protected abstract void onUserVisible();

    /**
     * 不可见时调用
     */
    protected abstract void onUserInvisible();

    /**
     * 获取 height 单位 dp
     *
     * @return
     */
    protected abstract int getHeight();

    /**
     * 获取兼容包下的FragmentManager
     *
     * @return
     */
    protected FragmentManager getSupportFragmentManager() {
        return getActivity().getSupportFragmentManager();
    }

    /**
     * 打开Activity
     *
     * @param clazz
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    /**
     * 携带Bundle打开Activity
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 打开带返回至的Activity
     *
     * @param clazz
     * @param requestCode
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 携带Bundle打开带返回值的Activity
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

}
