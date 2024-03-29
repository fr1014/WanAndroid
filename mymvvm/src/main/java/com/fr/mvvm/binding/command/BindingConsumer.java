package com.fr.mvvm.binding.command;

/**
 * 一个参数的行为
 *
 * 创建时间：2019/10/20
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public interface BindingConsumer<T> {

    void call(T t);
}
