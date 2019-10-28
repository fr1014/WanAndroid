package com.fr.wanandroid.customview.webview;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.util.List;

/**
 * 创建时间：2019/9/18
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 * <p>
 * 实现一个基础的 WebViewClient ，如果有更多的需要，直接继承它
 */
public class BaseWebViewClient extends WebViewClient {

    private Context mContext;

    public BaseWebViewClient(Context context) {
        this.mContext = context;
    }

    /**
     * 多页面在同一个WebView中打开，就是不新建activity或者调用系统浏览器打开
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        final Uri uri = request.getUrl();
        String url = String.valueOf(uri);
        try {
            //处理intent协议
            if (url.startsWith("intent://")) {
                Intent intent;
                try {
                    intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    intent.addCategory("android.intent.category.BROWSABLE");
                    intent.setComponent(null);
                    List<ResolveInfo> resolves = mContext.getPackageManager().queryIntentActivities(intent, 0);
                    if (resolves.size() > 0) {
                        mContext.startActivity(intent);
                    }
                    return true;
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }

            if (!url.startsWith("http")) {
                try {
                    // 以下固定写法
                    final Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(url));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mContext.startActivity(intent);

                } catch (Exception e) {
                    // 防止没有安装的情况
                    e.printStackTrace();
                    Toast.makeText(mContext, "您所打开的第三方App未安装！", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        //接受证书
        handler.proceed();
    }
}
