package com.fr.mvvm.http.utils;

import android.os.Looper;

import java.io.Closeable;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 工具类
 *
 * 创建时间：2019/10/5
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class Utils {

    //检查传入的对象是否为空，并返回该对象
    public static <T> T checkNotNull(T t,String message){
        if (t ==null){
            throw new NullPointerException(message);
        }
        return t;
    }

    //检查线程是否为主线程
    public static boolean checkMain(){
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    public static RequestBody createFile(String jsonString){
        checkNotNull(jsonString,"json not null");
        return RequestBody.create(MediaType.parse("application/json;charset=utf-8"),jsonString);
    }

    public static void close(Closeable close){
        if (close!=null){
            try {
                closeThrowException(close);
            } catch (IOException e) {

            }
        }
    }

    private static void closeThrowException(Closeable close) throws IOException{
        if (close!=null){
            close.close();
        }
    }
}
