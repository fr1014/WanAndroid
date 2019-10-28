package com.fr.mvvm.http.base;

/**
 * 创建时间：2019/10/6
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public interface BaseResponse<T> {

    int getCode();

    void setCode(int code);

    T getData();

    void setData(T data);

    String getMsg();

    void setMsg(String msg);
}
