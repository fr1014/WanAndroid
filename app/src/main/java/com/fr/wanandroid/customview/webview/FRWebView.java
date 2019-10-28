package com.fr.wanandroid.customview.webview;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Message;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.fr.mvvm.http.utils.NetStatusUtil;

import java.io.File;

/**
 * 创建时间：2019/10/22
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class FRWebView extends WebView implements LifecycleObserver {

    private WebView mWebView;
    private Context mContext;

    public FRWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        this.mWebView = this;
        this.mContext = context;
        
        initSetting();
    }

    //初始化操作
    private void initSetting() {
        WebSettings webSettings = getSettings();

        //调用JS方法
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportMultipleWindows(true);

        webSettings.setBuiltInZoomControls(true);  //原网页基础上缩放
        webSettings.setSupportZoom(true);  //支持缩放，默认为true

        //设置自适应屏幕，两者合用
        webSettings.setLoadWithOverviewMode(true); //缩放至屏幕的大小
        webSettings.setUseWideViewPort(true);    //将图片调整到适合webview的大小

        webSettings.setDefaultTextEncodingName("utf-8"); //设置编码格式
        webSettings.setLoadsImagesAutomatically(true);   //支持自动加载图片

        //有时候网页需要自己保存一些关键数据,Android H5WebView 需要自己设置
        saveDate(webSettings);
        newWin(webSettings);

        setWebChromeClient(new BaseWebChromeClient());
        setWebViewClient(new BaseWebViewClient(mContext));
    }

    /**
     * 多窗口的问题
     */
    private void newWin(WebSettings webSettings) {
        //html中的_bank标签就是新建窗口打开，有时会打不开，需要加以下
        //然后 复写 WebChromeClient的onCreateWindow方法
        webSettings.setSupportMultipleWindows(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    }

    /**
     * HTML5数据储存
     * H5缓存主要包括了App Cache、DOM Storage、Local Storage、Web SQL Database 存储机制等
     */
    private void saveDate(WebSettings webSettings) {
        //有时候网页需要自己保存一些关键数据,Android WebView 需要自己设置

        if (NetStatusUtil.isConnected(mContext)) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
        }
        File cacheDir = mContext.getCacheDir();
        if (cacheDir != null) {
            String appCachePath = cacheDir.getAbsolutePath();
            webSettings.setDomStorageEnabled(true);
            webSettings.setDatabaseEnabled(true);
            webSettings.setAppCacheEnabled(true);
            webSettings.setAppCachePath(appCachePath);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.canGoBack()) {
            this.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void viewPause() {
        mWebView.onPause();
        mWebView.pauseTimers();//暂停整个 H5WebView 所有布局、解析、JS
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void viewResume() {
        mWebView.onResume();
        mWebView.resumeTimers();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void viewDestroy() {
        if (mWebView != null) {
            mWebView.clearHistory();
            //不判断在无网络情况下加载错误界面，退出后报空指针问题
            if (mWebView.getParent() != null) {
                ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            }
            mWebView.loadUrl("about:blank");
            mWebView.stopLoading();
            mWebView.setWebChromeClient(null);
            mWebView.setWebViewClient(null);
            mWebView.destroy();
            mWebView = null;
        }
    }
}
