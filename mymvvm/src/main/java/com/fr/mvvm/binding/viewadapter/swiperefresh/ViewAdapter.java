package com.fr.mvvm.binding.viewadapter.swiperefresh;

import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fr.mvvm.binding.command.BindingCommand;

/**
 * 创建时间：2019/10/19
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class ViewAdapter {

    //下拉刷新命令
    @BindingAdapter({"onRefreshCommand"})
    public static void onRefreshCommand(SwipeRefreshLayout swipeRefreshLayout, final BindingCommand onRefreshCommand){
         swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
             @Override
             public void onRefresh() {
                 if (onRefreshCommand != null){
                     onRefreshCommand.execute();
                 }
             }
         });
    }

    //是否刷新中
    @BindingAdapter({"refreshing"})
    public static void setRefreshing(SwipeRefreshLayout swipeRefreshLayout,boolean refreshing){
        swipeRefreshLayout.setRefreshing(refreshing);
    }
}
