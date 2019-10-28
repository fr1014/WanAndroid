package com.fr.wanandroid.main.ui.main.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.fr.mvvm.base.BaseModel;
import com.fr.mvvm.base.BaseViewModel;
import com.fr.wanandroid.main.entity.ArticleBean;
import com.fr.wanandroid.main.entity.ArticleListBean;

/**
 * 创建时间：2019/10/22
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class DetailViewModel extends BaseViewModel {

    public ObservableField<ArticleBean> entity = new ObservableField<>();

    public DetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void setEntity(ArticleBean entity){
        this.entity.set(entity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        entity = null;
    }
}
