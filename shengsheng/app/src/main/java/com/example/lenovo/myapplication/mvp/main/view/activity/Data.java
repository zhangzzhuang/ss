package com.example.lenovo.myapplication.mvp.main.view.activity;

import android.app.Application;

import com.example.lenovo.myapplication.mvp.main.model.Goods;
import com.example.lenovo.myapplication.mvp.business.model.Business;
import com.example.lenovo.myapplication.mvp.user.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dingin on 2018/5/25.
 */

public class Data extends Application {

    public static String PublicUrl="http://192.168.43.221:8080";
//    public static String PublicUrl="http://192.168.43.240:8080";
    public static String url=PublicUrl+"/sx/";
    public static String urlImage=PublicUrl+"/sx/user/";
    public static String urlLunBoTu = url+"/lunbotu/";
    public static User user =new User();
    public static Business business = new Business();
    public static String BusinessUrlImage=PublicUrl+"/sx/business/";
    public static String urlImageBusiness=PublicUrl+"/sx/business/";
    public static Goods goods = new Goods();
    public static String sousuoGoods = PublicUrl+"/sx/SouSuoGoods?name=";

    public static List<String> typeList=new ArrayList<>();
    public static String businessLocation = new String();
    public static String userLocation = new String();

    public static boolean isFirstLocation = true;
    }




