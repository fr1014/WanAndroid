package com.fr.mvvm.base;

/**
 * 创建时间：2019/10/8
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public interface IModel {

    /**
     * ViewModel销毁时清楚Model,与ViewModel共消亡。Model层同样不能持有长生命周期对象
     */
    void onCleared();
}
