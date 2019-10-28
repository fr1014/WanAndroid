package com.fr.mvvm.base;

import org.jetbrains.annotations.NotNull;

/**
 * 创建时间：2019/10/14
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class ItemViewModel<VM extends BaseViewModel> {
    protected VM viewModel;

    public ItemViewModel(@NotNull VM viewModel) {
        this.viewModel = viewModel;
    }
}
