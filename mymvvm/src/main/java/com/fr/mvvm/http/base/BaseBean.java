package com.fr.mvvm.http.base;

import com.fr.mvvm.http.utils.JsonFormatUtils;
import com.google.gson.Gson;

import java.io.Serializable;

/**
 * 网络请求的实体类基类
 *
 * 创建时间：2019/10/6
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class BaseBean implements Serializable {

    public String toJson(){
        return new Gson().toJson(this);
    }

    public String toFormatJson(){
        return JsonFormatUtils.format(toJson());
    }
}
