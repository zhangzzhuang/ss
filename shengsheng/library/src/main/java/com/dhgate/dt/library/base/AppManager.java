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

import java.util.Stack;

/**
 * @User Jian.Wang
 * @Date 2018/8/19
 * @Time 下午11:11
 * @Version 1.0
 * @Description Activity管理类
 */
public class AppManager {

    private static final String TAG = AppManager.class.getSimpleName();

    private static AppManager instance = null;
    private static Stack<Activity> mActivities = new Stack<>();

    private AppManager() {

    }

    public static AppManager getInstance() {
        if (null == instance) {
            synchronized (AppManager.class) {
                if (null == instance) {
                    instance = new AppManager();
                }
            }
        }
        return instance;
    }

    public int size() {
        return mActivities.size();
    }

    public Stack<Activity> getAllActivity (){
        return mActivities;
    }

    /**
     * 获得当前的activity(即最上层)
     *
     * @return
     */
    public Activity currentActivity() {
        Activity activity = null;
        if (!mActivities.empty()) {
            activity = mActivities.lastElement();
        }
        return activity;
    }

    /**
     * 添加
     *
     * @param activity
     */
    public synchronized void addActivity(Activity activity) {
        mActivities.add(activity);
    }

    /**
     * 移除
     *
     * @param activity
     */
    public synchronized void removeActivity(Activity activity) {
        if (activity != null && mActivities.contains(activity)) {
            mActivities.remove(activity);
        }
    }

    /**
     * 包含
     *
     * @param cls
     * @return
     */
    public synchronized boolean contains(Class<? extends Activity> cls) {
        for (int i = 0; i < mActivities.size(); i++) {
            Activity activity = mActivities.get(i);
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 清空栈
     */
    public synchronized void clear() {
        while (!mActivities.empty()) {
            Activity activity = currentActivity();
            removeActivity(activity);
            activity.finish();
        }
    }

    /**
     * 结束除cls之外的所有activity
     */
    public synchronized void clearWithOut(Class<? extends Activity> cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            removeActivity(activity);
            activity.finish();
        }
    }

    /**
     * 退出App
     */
    public void exitApp() {
        if (mActivities != null) {
            synchronized (mActivities) {
                clear();
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}
