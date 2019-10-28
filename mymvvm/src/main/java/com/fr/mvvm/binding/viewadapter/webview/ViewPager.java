package com.fr.mvvm.binding.viewadapter.webview;

import android.webkit.WebView;

import androidx.databinding.BindingAdapter;


/**
 * 创建时间：2019/10/22
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class ViewPager {

    @BindingAdapter({"loadUrl"})
    public static void loadUrl(WebView webView,final String url){
        webView.loadUrl(url);
    }
}
