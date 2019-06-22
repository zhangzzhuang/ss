package com.example.lenovo.myapplication.mvp.business.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dingin on 2018/5/27.
 */
public class Business implements Serializable {
    int id;
    String name;
    String password;
    String phone;
    String baidu_zuobiao;
    String image;
    int followers_num;
    int qiandao;
    Date qiandao_date;
    String shop_name;
    String shop_add_name;


    public String getShop_add_name() {
        return shop_add_name;
    }

    public void setShop_add_name(String shop_add_name) {
        this.shop_add_name = shop_add_name;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBaidu_zuobiao() {
        return baidu_zuobiao;
    }

    public void setBaidu_zuobiao(String baidu_zuobiao) {
        this.baidu_zuobiao = baidu_zuobiao;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getFollowers_num() {
        return followers_num;
    }

    public void setFollowers_num(int followers_num) {
        this.followers_num = followers_num;
    }

    public int getQiandao() {
        return qiandao;
    }

    public void setQiandao(int qiandao) {
        this.qiandao = qiandao;
    }

    public Date getQiandao_date() {
        return qiandao_date;
    }

    public void setQiandao_date(Date qiandao_date) {
        this.qiandao_date = qiandao_date;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }
}

