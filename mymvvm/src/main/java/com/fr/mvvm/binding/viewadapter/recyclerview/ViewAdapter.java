package com.fr.mvvm.binding.viewadapter.recyclerview;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fr.mvvm.binding.command.BindingCommand;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * 创建时间：2019/10/16
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class ViewAdapter {

    private static final String TAG = "ViewAdapter";

    @BindingAdapter({"onLoadMoreCommand"})
    public static void onLoadMoreCommand(final RecyclerView recyclerView, final BindingCommand<Integer> onLoadMoreCommand) {
        RecyclerView.OnScrollListener listener = new OnScrollListener(onLoadMoreCommand);
        recyclerView.addOnScrollListener(listener);
    }

    public static class OnScrollListener extends RecyclerView.OnScrollListener {
        private PublishSubject<Integer> methodInvoke = PublishSubject.create();
        private BindingCommand<Integer> onLoadMoreCommand;

        @SuppressLint("CheckResult")
        public OnScrollListener(final BindingCommand<Integer> onLoadMoreCommand) {
            this.onLoadMoreCommand = onLoadMoreCommand;
            methodInvoke.throttleFirst(1, TimeUnit.SECONDS)
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            onLoadMoreCommand.execute(integer);
                        }
                    });
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int visibleItemCount = layoutManager != null ? layoutManager.getChildCount() : 0;
            int totalItemCount = layoutManager != null ? layoutManager.getItemCount() : 0;
            int pastVisibleItems = layoutManager != null ? layoutManager.findFirstVisibleItemPosition() : 0;
            Log.d(TAG, "onScrolled: "+"\nvisibleItemCount: " + visibleItemCount
                    + "\ntotalItemCount : "+totalItemCount + "\npastVisibleItems: " + pastVisibleItems);
            if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                if (onLoadMoreCommand != null) {
                    RecyclerView.Adapter adapter = recyclerView.getAdapter();
                    int page = adapter != null ? adapter.getItemCount()/totalItemCount : 0;
                    Log.d(TAG, "onScrolled: " + "\npage"+ page);
                    methodInvoke.onNext(page);
                }
            }
        }

        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }
    }

    @BindingAdapter({"adapter"})
    public static void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter){
        recyclerView.setAdapter(adapter);
    }
}
