package com.fr.mvvm.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 创建时间：2019/10/14
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class ToastUtils {

    public static void showShort(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
