package com.fr.wanandroid.main.ui.wxarticle.vm;

import android.app.Application;

import androidx.annotation.NonNull;

import com.fr.mvvm.base.BaseViewModel;
import com.fr.wanandroid.main.model.ModelRepository;

/**
 * 创建时间：2019/10/9
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class ArticleViewModel extends BaseViewModel<ModelRepository> {

    public ArticleViewModel(@NonNull Application application, ModelRepository model) {
        super(application, model);
    }

}
