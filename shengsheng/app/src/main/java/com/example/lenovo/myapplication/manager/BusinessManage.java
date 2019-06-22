package com.example.lenovo.myapplication.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.lenovo.myapplication.utils.StreamTools;
import com.example.lenovo.myapplication.mvp.main.model.BussinessInfo;

import java.util.Date;

/**
 * @User basi
 * @Date 2019/4/11
 * @Time 10:44 AM
 * @Description ${DESCRIPTION}
 */
public class BusinessManage {

    private static BusinessManage instance;

    private BusinessManage(){

    }

    public static BusinessManage getInstance(){
        if (instance == null){
            instance = new BusinessManage();
        }
        return instance;
    }

    public void saveBussinessInfo(Context context,String id,String name,String password,String phone,String baidu_zuobiao,String image,String followers_num
    ,String qiandao,Date qiandao_date ,String shop_name ){
        SharedPreferences sp = context.getSharedPreferences("bussinessInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("ID",id);
        editor.putString("NAME",name);
        editor.putString("PASSWORD",password);
        editor.putString("PHONE",phone);
        editor.putString("ZUOBIAO",baidu_zuobiao);
        editor.putString("IMAGE",image);
        editor.putString("FOLLOWERS",followers_num);
        editor.putString("QIANDAO",qiandao);
        editor.putString("DATE", StreamTools.DateToStr(qiandao_date));
        editor.putString("SHOPNAME", shop_name);
        editor.commit();

    }

    public BussinessInfo getBusinessInfo(Context context){
        SharedPreferences sp = context.getSharedPreferences("bussinessInfo",Context.MODE_PRIVATE);
        BussinessInfo bussinessInfo = new BussinessInfo();
        bussinessInfo.setId(sp.getString("ID",""));
        bussinessInfo.setName(sp.getString("NAME",""));
        bussinessInfo.setPassword(sp.getString("PASSWORD",""));
        bussinessInfo.setBaidu_zuobiao(sp.getString("ZUOBIAO",""));
        bussinessInfo.setImage(sp.getString("IMAGE",""));
        bussinessInfo.setFollowers_num(sp.getString("FOLLOWERS",""));
        bussinessInfo.setQiandao(sp.getString("QIANDAO",""));
        bussinessInfo.setQiandao_date(sp.getString("DATE",""));
        bussinessInfo.setShop_name(sp.getString("SHOPNAME",""));
        return bussinessInfo;
    }

    public void delBussinessInfo(Context context){
        SharedPreferences sp = context.getSharedPreferences("bussinessInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    public boolean hasBussinessInfo(Context context){
        BussinessInfo bussinessInfo = getBusinessInfo(context);
        if (bussinessInfo != null){
            if ((!TextUtils.isEmpty(bussinessInfo.getId()))&&(!TextUtils.isEmpty(bussinessInfo.getName())) && (!TextUtils.isEmpty(bussinessInfo.getPassword()))){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }
}
