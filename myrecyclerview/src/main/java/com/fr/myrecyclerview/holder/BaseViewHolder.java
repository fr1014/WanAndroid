package com.fr.myrecyclerview.holder;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import com.fr.myrecyclerview.adapter.BaseAdapter;

import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * 创建时间：2019/9/23
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 * <p>
 * 基础的ViewHolder
 * ViewHolder只作View的缓存，避免每次findViewById,从而提升运行的效率
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViewArray;
    private View mItemView;
    private final HashSet<Integer> mNestViews;
    private final LinkedHashSet<Integer> mChildClickViewIds;
    private final LinkedHashSet<Integer> mItemChildLongClickViewIds;
    private BaseAdapter adapter;

    /**
     * 构造ViewHolder
     */
    public BaseViewHolder(View view) {
        super(view);
        this.mViewArray = new SparseArray<>();
        this.mNestViews = new HashSet<>();
        this.mChildClickViewIds = new LinkedHashSet<>();
        this.mItemChildLongClickViewIds = new LinkedHashSet<>();
        mItemView = view;
    }

    /**
     * 获取ItemView
     *
     * @return ItemView
     */
    public View getItemView() {
        return this.itemView;
    }

    public LinkedHashSet<Integer> getChildClickViewIds() {
        return mChildClickViewIds;
    }

    public LinkedHashSet<Integer> getItemChildLongClickViewIds() {
        return mItemChildLongClickViewIds;
    }

    /**
     * 给TextView设置内容
     *
     * @param viewId TextView的id
     * @param strId  需要设置的内容
     */
    public BaseViewHolder setText(@IdRes int viewId, @StringRes int strId) {
        TextView tv = getView(viewId);
        tv.setText(strId);
        return this;
    }

    public BaseViewHolder setText(@IdRes int viewId, CharSequence value) {
        TextView tv = getView(viewId);
        tv.setText(value);
        return this;
    }

    /**
     * 通过资源ID设置ImageView的图像
     *
     * @param viewId     ImageView的id
     * @param imageResId 需要设置的图片资源
     */
    public BaseViewHolder setImageResource(@IdRes int viewId, @DrawableRes int imageResId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(imageResId);
        return this;
    }

    /**
     * 给ImageView设置图片
     *
     * @param viewId ImageView的id
     * @param bitmap 需要设置的图片资源
     */
    public BaseViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 给ImageView设置图片
     *
     * @param viewId   ImageView的id
     * @param drawable 需要设置的图片资源
     */
    public BaseViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView iv = getView(viewId);
        iv.setImageDrawable(drawable);
        return this;
    }

    /**
     * 将设置视图的背景
     *
     * @param viewId        视图ID
     * @param backgroundRes 用作背景的资源
     */
    public BaseViewHolder setBackgroundRes(@IdRes int viewId, @DrawableRes int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    /**
     * 将设置TextView的文本颜色
     *
     * @param viewId    视图ID
     * @param textColor 文字颜色（不是资源ID)
     */
    public BaseViewHolder setTextColor(@IdRes int viewId, @ColorInt int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    /**
     * 此方法可以设置视图的Alpha（可多次调用）
     * Alpha between 0-1.
     */
    public BaseViewHolder setAlpha(@IdRes int viewId, float value) {
        AlphaAnimation alpha = new AlphaAnimation(value, value);
        alpha.setDuration(0);
        alpha.setFillAfter(true);
        getView(viewId).startAnimation(alpha);
        return this;
    }

    /**
     * 将视图可见性设置为“可见”（true）或“消失”（false）
     *
     * @param viewId  视图ID
     * @param visible True for VISIBLE, false for GONE.
     */
    public BaseViewHolder setGone(@IdRes int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 将视图可见性设置为VISIBLE（true）或INVISIBLE（false）
     *
     * @param viewId  视图ID
     * @param visible True for VISIBLE, false for INVISIBLE.
     */
    public BaseViewHolder setVisible(@IdRes int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        return this;
    }

    /**
     * 将链接添加到TextView中
     *
     * @param viewId 要链接的TextView的ID
     */
    public BaseViewHolder linkify(@IdRes int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    /**
     * 将字体应用于给定的viewId
     */
    public BaseViewHolder setTypeface(@IdRes int viewId, Typeface typeface) {
        TextView view = getView(viewId);
        view.setTypeface(typeface);
        view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        return this;
    }

    /**
     * Apply the typeface to all the given viewIds, and enable subpixel rendering.
     */
    public BaseViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    /**
     * 设置ProgressBar的进度
     *
     * @param viewId   视图ID
     * @param progress 进度
     */
    public BaseViewHolder setProgress(@IdRes int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    /**
     * Sets the progress and max of a ProgressBar.
     *
     * @param viewId   The view id.
     * @param progress The progress.
     * @param max      The max value of a ProgressBar.
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setProgress(@IdRes int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    /**
     * Sets the range of a ProgressBar to 0...max.
     *
     * @param viewId The view id.
     * @param max    The max value of a ProgressBar.
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setMax(@IdRes int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    /**
     * Sets the rating (the number of stars filled) of a RatingBar.
     *
     * @param viewId The view id.
     * @param rating The rating.
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setRating(@IdRes int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    /**
     * Sets the rating (the number of stars filled) and max of a RatingBar.
     *
     * @param viewId The view id.
     * @param rating The rating.
     * @param max    The range of the RatingBar to 0...max.
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setRating(@IdRes int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

//    public BaseViewHolder addOnClickListener(@IdRes final int... viewIds) {
//        for (int viewId : viewIds) {
//            mChildClickViewIds.add(viewId);
//            final View view = getView(viewId);
//            if (view != null){
//                if (!view.isClickable()){
//                    view.setClickable(true);
//                }
//                view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if ()
//                    }
//                });
//            }
//        }
//        return this;
//    }

    /**
     * 设置适配器视图的适配器
     *
     * @param adapter 适配器
     */
    public BaseViewHolder setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
        return this;
    }

    @SuppressWarnings("unchecked")
    public BaseViewHolder setAdapter(@IdRes int viewId, Adapter adapter) {
        AdapterView view = getView(viewId);
        view.setAdapter(adapter);
        return this;
    }

    /**
     * 获取布局中的View
     *
     * @param viewId view的Id
     * @param <T>    View的类型
     * @return view
     */
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViewArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViewArray.put(viewId, view);
        }
        return (T) view;
    }
}
