package com.fr.wanandroid.app;

import android.app.Application;
import android.content.Context;

/**
 * 创建时间：2019/10/6
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class MyApplication extends Application {
    private static Application app = null;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static Context getContext(){
        if (app == null)
            return null;
        return app.getApplicationContext();
    }

    public static Application getApplication(){
        if (app == null){
            return null;
        }
        return app;
    }
}
