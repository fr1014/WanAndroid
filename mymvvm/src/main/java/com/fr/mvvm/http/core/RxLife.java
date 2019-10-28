package com.fr.mvvm.http.core;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 创建时间：2019/10/6
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class RxLife {

    private CompositeDisposable mCompositeDisposable = null;

    private RxLife(){
        mCompositeDisposable = new CompositeDisposable();
    }

    public static RxLife create(){
        return new RxLife();
    }

    public void destory(){
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()){
            return;
        }
        mCompositeDisposable.dispose();
    }

    public void add(Disposable d){
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()){
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(d);
    }
}
