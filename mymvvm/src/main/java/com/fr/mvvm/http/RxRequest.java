package com.fr.mvvm.http;

import com.fr.mvvm.http.base.BaseResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 网络请求
 * <p>
 * 创建时间：2019/10/6
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class RxRequest<T, R extends BaseResponse<T>> {
    private final Observable<R> mObservable;

    private RxRequest(Observable<R> observable){
        mObservable = observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static <T,R extends BaseResponse<T>> RxRequest<T,R> create(Observable<R> observable){
        return new RxRequest<>(observable);
    }

    /**
     * 添加生命周期的监听
     */
    public RxRequest<T,R> listener(){
        return this;
    }



//    public interface RequestListener{
//        void onStart();
//
//        void onError(ExceptionHandle handle);
//
//        void onFinish();
//    }
}
