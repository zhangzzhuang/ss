package com.example.lenovo.myapplication.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.lenovo.myapplication.utils.StreamTools;
import com.example.lenovo.myapplication.mvp.main.model.UserInfo;
import java.util.Date;

/**
 * @User basi
 * @Date 2019/4/9
 * @Time 2:57 PM
 * @Description ${保存用户信息的管理类}
 */
public class UserManage {

    private static UserManage instance;

    private UserManage(){

    }

    public static UserManage getInstance(){
         if (instance == null){
             instance = new UserManage();
         }
         return instance;
    }

    /**
     * 保存自动登录的用户信息
     * @param context
     * @param username
     * @param password
     */
    public void saveUserInfo(Context context,String username , String password,String id,String phone, String collection_id,
             String business_id, String qiandao, Date qiandao_date, String image, String chakan_goods, String sex, String qianming){

        SharedPreferences sp = context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("USER_NAME",username);
        editor.putString("PASSWORD",password);
        editor.putString("ID",id);
        editor.putString("PHONE",phone);
        editor.putString("COLLECTION_ID",collection_id);
        editor.putString("BUSINESS_ID",business_id);
        editor.putString("QIANDAO", qiandao);
        editor.putString("DATE", StreamTools.DateToStr(qiandao_date));
        editor.putString("IMAGE",image);
        editor.putString("CHAKAN_GOODS",chakan_goods);
        editor.putString("SEX",sex);
        editor.putString("QIANMING",qianming);
        editor.commit();
    }

    /**
     * 获取用户信息
     * @param context
     * @return
     */
    public UserInfo getUserInfo(Context context){
        SharedPreferences sp = context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(sp.getString("USER_NAME",""));
        userInfo.setPassword(sp.getString("PASSWORD",""));
        userInfo.setId(sp.getString("ID",""));
        userInfo.setPhone(sp.getString("PHONE",""));
        userInfo.setCollection_id(sp.getString("COLLECTION_ID",""));
        userInfo.setBusiness_id(sp.getString("BUSINESS_ID",""));
        userInfo.setQiandao(sp.getString("QIANDAO", ""));
        userInfo.setQiandao_date(sp.getString("DATE",""));
        userInfo.setImage(sp.getString("IMAGE",""));
        userInfo.setChakan_goods(sp.getString("CHAKAN_GOODS",""));
        userInfo.setSex(sp.getString("SEX",""));
        userInfo.setQianming(sp.getString("QIANMING",""));
        return userInfo;

    }

    /**
     * 清空userInfo信息
     * @param context
     */
    public void delUserInfo(Context context){
        SharedPreferences sp = context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 判断userInfo是否有数据
     * @param context
     * @return
     */
    public boolean hasUserInfo(Context context){
        UserInfo userInfo  = getUserInfo(context);
        if (userInfo != null){
            if ((!TextUtils.isEmpty(userInfo.getId()))&&(!TextUtils.isEmpty(userInfo.getUserName())) && (!TextUtils.isEmpty(userInfo.getPassword()))){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }
}
