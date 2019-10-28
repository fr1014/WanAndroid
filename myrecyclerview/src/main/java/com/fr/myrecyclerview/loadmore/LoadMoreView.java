package com.fr.myrecyclerview.loadmore;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

import com.fr.myrecyclerview.holder.BaseViewHolder;

/**
 * 创建时间：2019/9/24
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public abstract class LoadMoreView {

    public static final int STATUS_DEFAULT = 1;
    public static final int STATUS_LOADING = 2;
    public static final int STATUS_FAIL = 3;
    public static final int STATUS_END = 4;

    private int mLoadMoreStatus = STATUS_DEFAULT;
    private boolean mLoadMoreEndGone = false;

    public void setLoadMoreStatus(int mLoadMoreStatus) {
        this.mLoadMoreStatus = mLoadMoreStatus;
    }

    public int getLoadMoreStatus() {
        return mLoadMoreStatus;
    }

    public void convert(BaseViewHolder holder) {
        switch (mLoadMoreStatus) {
            case STATUS_LOADING:
                visibleLoading(holder, true);
                visibleLoadFail(holder, false);
                visibleLoadEnd(holder, false);
                break;
            case STATUS_FAIL:
                visibleLoading(holder, false);
                visibleLoadFail(holder, true);
                visibleLoadEnd(holder, false);
                break;
            case STATUS_END:
                visibleLoading(holder, false);
                visibleLoadFail(holder, false);
                visibleLoadEnd(holder, true);
                break;
            case STATUS_DEFAULT:
                visibleLoading(holder, false);
                visibleLoadFail(holder, false);
                visibleLoadEnd(holder, false);
                break;
            default:
                break;
        }
    }

    private void visibleLoading(BaseViewHolder holder, boolean visible) {
        holder.setGone(getLoadingViewId(),visible);
    }

    private void visibleLoadFail(BaseViewHolder holder, boolean visible) {
        holder.setGone(getLoadFailViewId(),visible);
    }

    private void visibleLoadEnd(BaseViewHolder holder, boolean visible) {
        final int loadEndViewId = getLoadEndViewId();
        if (loadEndViewId != 0){
            holder.setGone(loadEndViewId,visible);
        }
    }

    public final void setLoadMoreEndGone(boolean loadMoreEndGone) {
        this.mLoadMoreEndGone = loadMoreEndGone;
    }

    public final boolean isLoadEndMoreGone() {
        if (getLoadEndViewId() == 0) {
            return true;
        }
        return mLoadMoreEndGone;
    }

    /**
     * load more layout
     *
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    /**
     * load end view
     */
    protected abstract
    @IdRes
    int getLoadEndViewId();

    /**
     * load fail view
     */
    protected abstract
    @IdRes
    int getLoadFailViewId();

    /**
     * loading view
     * @return
     */
    protected abstract
    @IdRes
    int getLoadingViewId();
}
