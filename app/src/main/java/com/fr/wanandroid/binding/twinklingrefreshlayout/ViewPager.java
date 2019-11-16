package com.fr.wanandroid.binding.twinklingrefreshlayout;

import androidx.databinding.BindingAdapter;

import com.fr.mvvm.binding.command.BindingCommand;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

/**
 * 创建时间：2019/11/8
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class ViewPager {

    @BindingAdapter(value = {"onRefreshCommand", "onLoadMoreCommand"}, requireAll = false)
    public static void onRefreshAndLoadMoreCommand(TwinklingRefreshLayout twinklingRefreshLayout, final BindingCommand onRefreshCommand, final BindingCommand onLoadMoreCommand) {
        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                if (onRefreshCommand != null) {
                    super.onRefresh(refreshLayout);
                    onRefreshCommand.execute();
                }
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (onLoadMoreCommand != null) {
                    super.onLoadMore(refreshLayout);
                    onLoadMoreCommand.execute();
                }
            }
        });
    }
}
