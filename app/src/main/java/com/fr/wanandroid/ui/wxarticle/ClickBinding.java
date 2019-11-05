package com.fr.wanandroid.ui.wxarticle;

import android.os.Bundle;
import android.view.View;

import com.fr.mvvm.binding.command.BindingCommand;
import com.fr.mvvm.binding.command.BindingConsumer;

/**
 * 创建时间：2019/11/5
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class ClickBinding {

    public BindingCommand<View> onClickCommand = new BindingCommand<View>(new BindingConsumer<View>() {
        @Override
        public void call(View view) {

        }
    });
}
