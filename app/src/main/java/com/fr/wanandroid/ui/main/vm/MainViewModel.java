package com.fr.wanandroid.ui.main.vm;

import android.app.Application;

import androidx.annotation.NonNull;

import com.fr.mvvm.base.BaseViewModel;
import com.fr.wanandroid.model.ModelRepository;

/**
 * 创建时间：2019/10/11
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class MainViewModel extends BaseViewModel<ModelRepository> {

    public MainViewModel(@NonNull Application application, ModelRepository model) {
        super(application, model);
    }

}
