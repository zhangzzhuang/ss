package com.example.lenovo.myapplication.mvp.user.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018/5/24.
 */

public class User {
	int id;
	String name;
	String password;
	String phone;
	String collection_id;
	String business_id;
	int qiandao;
	Date qiandao_date;
	String image;
	String chakan_goods;
	String sex;
	String qianming;

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

	public String getCollection_id() {
		return collection_id;
	}

	public void setCollection_id(String collection_id) {
		this.collection_id = collection_id;
	}

	public String getBusiness_id() {
		return business_id;
	}

	public void setBusiness_id(String business_id) {
		this.business_id = business_id;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getChakan_goods() {
		return chakan_goods;
	}

	public void setChakan_goods(String chakan_goods) {
		this.chakan_goods = chakan_goods;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getQianming() {
		return qianming;
	}

	public void setQianming(String qianming) {
		this.qianming = qianming;
	}
}
