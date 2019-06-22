package com.example.lenovo.myapplication.event;

/**
 * @User Jian.Wang
 * @Date 2018/8/19
 * @Time 下午11:11
 * @Version 1.0
 * @Description 时间模型
 */
public class RxEvent<T> {
    private int code;
    private T data;

    public RxEvent() {
    }

    public RxEvent(int code) {
        this.code = code;
    }

    public RxEvent(int code, T data) {
        this.code = code;
        this.data = data;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
