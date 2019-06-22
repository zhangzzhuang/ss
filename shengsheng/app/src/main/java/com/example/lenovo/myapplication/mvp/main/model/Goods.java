package com.example.lenovo.myapplication.mvp.main.model;

import com.example.lenovo.myapplication.mvp.user.model.PingLun;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dingin on 2018/5/27.
 */

public class Goods implements Serializable{
    private int id;
    private String type;
    private String name;
    private Double price;
    private String info;
    private int collect_num;
    private int business_id;
    private String image;
    private Date starttime;
    private Date endtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getCollect_num() {
        return collect_num;
    }

    public void setCollect_num(int collect_num) {
        this.collect_num = collect_num;
    }

    public int getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(int business_id) {
        this.business_id = business_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public List<PingLun> getPingLun() {
        return pingLun;
    }

    public void setPingLun(List<PingLun> pingLun) {
        this.pingLun = pingLun;
    }

    List<PingLun> pingLun = new ArrayList<PingLun>();
}
