package com.fr.mvvm.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * 创建时间：2019/10/19
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class MaterialDialogUtils {

    /**
     * 获取一个耗时等待的对话框
     */
    public static MaterialDialog.Builder showDialog(Context context, String content, boolean horizontal) {
         MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title(content)
                .progress(true, 0)
                .progressIndeterminateStyle(horizontal)
                .canceledOnTouchOutside(false)
                .backgroundColor(context.getColor(android.R.color.white))
                .keyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {  //如果是按下，则响应，否则，一次按下会响应两次
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                //
                            }
                        }
                        return false;//false允许按返回键取消对话框，true除了调用取消，其他情况下不会取消
                    }
                });
        return builder;
    }
}
