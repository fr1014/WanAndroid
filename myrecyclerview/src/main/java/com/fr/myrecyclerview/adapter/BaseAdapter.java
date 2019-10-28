package com.fr.myrecyclerview.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutParams;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.fr.myrecyclerview.holder.BaseViewHolder;
import com.fr.myrecyclerview.loadmore.LoadMoreView;
import com.fr.myrecyclerview.loadmore.SimpleLoadMoreView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * 创建时间：2019/9/23
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public abstract class BaseAdapter<T, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {
    private static final String TAG = "BaseAdapter";

    //load more
    private boolean mNextLoadEnable = false;
    private boolean mLoadMoreEnable = false;
    private boolean mLoading = false;
    private LoadMoreView mLoadMoreView = new SimpleLoadMoreView();
    private RequestLoadMoreListener mRequestLoadMoreListener;
    private boolean mEnableLoadMoreEndClick = false;

    //Animation
    public static final int ALPHA = 0x00000001;
    public static final int SCALE = 0x00000002;
    public static final int SLIDE_BOTTOM = 0x00000003;
    public static final int SLIDE_LEFT = 0x00000004;
    public static final int SLIDE_RIGHT = 0x00000005;

    //header footer
    private LinearLayout mHeaderLayout;
    private LinearLayout mFooterLayout;

    //empty
    private FrameLayout mEmptyLayout;
    private boolean mIsUseEmpty = true;
    private boolean mHeadAndEmptyEnable;
    private boolean mFootAndEmptyEnable;

    protected Context mContext;
    protected int mLayoutResId;
    protected List<T> mData;
    protected LayoutInflater mLayoutInflater;
    public static final int HEADER_VIEW = 0x00000111;
    public static final int LOADING_VIEW = 0x00000222;
    public static final int FOOTER_VIEW = 0x00000333;
    public static final int EMPTY_VIEW = 0x00000555;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private OnItemChildClickListener mOnItemChildClickListener;
    private OnItemChildLongClickListener mOnItemChildLongClickListener;

    /**
     * up fetch start
     */
    private boolean mUpFetchEnable;
    private boolean mUpFetching;
    //    private UpFetchListener mUpFetchListener;
    private RecyclerView mRecyclerView;
    private int mPreLoadNumber = 1;

    /**
     * start up fetch position, default is 1.
     */
    private int mStartUpFetchPosition = 1;
    /**
     * 如果asFlow为true，则页脚/页眉将像正常项目视图一样排列
     * only works when use {@link GridLayoutManager},and it will ignore span size.
     */
    private boolean headerViewAsFlow, footerViewAsFlow;

    /**
     * @param layoutResId 每个项目的布局资源id
     * @param data        创建一个新列表是为了避免可变列表
     */
    public BaseAdapter(@LayoutRes int layoutResId, List<T> data) {
        this.mData = data == null ? new ArrayList<T>() : data;
        if (layoutResId != 0) {
            this.mLayoutResId = layoutResId;
        }
    }

    public BaseAdapter(List<T> data) {
        this(0, data);
    }

    public BaseAdapter(@LayoutRes int layoutResId) {
        this(layoutResId, null);
    }

    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    private void setRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    private void openLoadMore(RequestLoadMoreListener requestLoadMoreListener) {
        this.mRequestLoadMoreListener = requestLoadMoreListener;
        mNextLoadEnable = true;
        mLoadMoreEnable = true;
        mLoading = false;
    }

    public void setOnLoadMoreListener(RequestLoadMoreListener requestLoadMoreListener, RecyclerView recyclerView) {
        openLoadMore(requestLoadMoreListener);
        if (getRecyclerView() == null) {
            setRecyclerView(recyclerView);
        }
    }

    /**
     * Adapter与RecyclerView关联起来 这里面主要是做表格布局管理器的头布局和脚布局自占一行的适配
     */
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        Log.d(TAG, "--------onAttachedToRecyclerView: " + "Adapter与RecyclerView关联起来");
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            final GridLayoutManager.SpanSizeLookup defSpanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    if (viewType == HEADER_VIEW && isHeaderViewAsFlow()) {
                        return 1;
                    }
                    if (viewType == FOOTER_VIEW && isFooterViewAsFlow()) {
                        return 1;
                    }
                    if (mSpanSizeLookup == null) {
                        return isFixedViewType(viewType) ? gridLayoutManager.getSpanCount() : defSpanSizeLookup.getSpanSize(position);
                    } else {
                        return (isFixedViewType(viewType)) ? gridLayoutManager.getSpanCount() : mSpanSizeLookup.getSpanSize(gridLayoutManager, position - getHeaderLayoutCount());
                    }
                }
            });
        }
    }

    private boolean isFixedViewType(int viewType) {
        return viewType == EMPTY_VIEW || viewType == HEADER_VIEW ||
                viewType == FOOTER_VIEW || viewType == LOADING_VIEW;
    }

    /**
     * 当列表项出现到可视界面的时候调用
     */
    @Override
    public void onViewAttachedToWindow(@NonNull VH holder) {
        super.onViewAttachedToWindow(holder);
        Log.d(TAG, "--------onViewAttachedToWindow: " + "视图可见");
        int viewType = holder.getItemViewType();
        if (viewType == EMPTY_VIEW || viewType == HEADER_VIEW || viewType == FOOTER_VIEW || viewType == LOADING_VIEW) {
            setFullSpan(holder);
        } else {
            addAnimation(holder);
        }
    }

    /**
     * 设置为true时，该项目将使用所有跨度区域进行布局: 如果方向为垂直，则视图将具有完整宽度；
     * 如果方向是水平的，则视图将具有全高
     * 如果方向是水平的，则视图将具有完整的高度
     * 如果保留视图使用StaggeredGridLayoutManager，则应使用所有跨度区域
     */
    private void setFullSpan(VH holder) {
        if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            params.setFullSpan(true);
        }
    }

    private void addAnimation(VH holder) {

    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VH holder = null;
        this.mContext = parent.getContext();
        this.mLayoutInflater = LayoutInflater.from(mContext);

        switch (viewType) {
            case LOADING_VIEW:
                holder = getLoadingView(parent);
                break;
            case HEADER_VIEW:
                ViewParent headerLayoutVp = mHeaderLayout.getParent();
                if (headerLayoutVp instanceof ViewGroup) {
                    ((ViewGroup) headerLayoutVp).removeView(mHeaderLayout);
                }

                holder = createBaseViewHolder(mHeaderLayout);
                break;
            case EMPTY_VIEW:
                ViewParent emptyLayoutVp = mEmptyLayout.getParent();
                if (emptyLayoutVp instanceof ViewGroup) {
                    ((ViewGroup) emptyLayoutVp).removeView(mEmptyLayout);
                }

                holder = createBaseViewHolder(mEmptyLayout);
                break;
            case FOOTER_VIEW:
                ViewParent footerLayoutVp = mFooterLayout.getParent();
                if (footerLayoutVp instanceof ViewGroup) {
                    ((ViewGroup) footerLayoutVp).removeView(mFooterLayout);
                }
                holder = createBaseViewHolder(mFooterLayout);
                break;
            default:
                //创建BaseViewHolder或者其拓展的类
                holder = onCreateDefViewHolder(parent, viewType);
                bindViewClickListener(holder);
                Log.d(TAG, "--------onCreateViewHolder: " + viewType + "被创建");
        }
        holder.setAdapter(this);
        return holder;
    }

    //处理点击事件
    private void bindViewClickListener(final VH holder) {
        if (holder == null) {
            return;
        }

        final View view = holder.itemView;
        if (getOnItemClickListener() != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    if (position == RecyclerView.NO_POSITION) {
                        return;
                    }
                    position -= getHeaderLayoutCount();
                    Log.d(TAG, "--------onClick: " + position + "被点击");
                    setOnItemClick(v, position);
                }
            });
        }
        if (getOnItemLongClickListener() != null) {
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getAdapterPosition();
                    if (position == RecyclerView.NO_POSITION) {
                        return false;
                    }
                    position -= getHeaderLayoutCount();
                    return setOnItemLongClick(v, position);
                }
            });
        }
    }

    /**
     * 如果要覆盖longClick事件逻辑，请覆盖此方法
     */
    private boolean setOnItemLongClick(View v, int position) {
        return getOnItemLongClickListener().onItemLongClick(BaseAdapter.this, v, position);
    }

    /**
     * 如果要覆盖单击事件逻辑，请覆盖此方法
     */
    private void setOnItemClick(View v, int position) {
        getOnItemClickListener().onItemClick(BaseAdapter.this, v, position);
    }

    protected VH onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, mLayoutResId);
    }

    protected VH createBaseViewHolder(ViewGroup parent, int layoutResId) {
        return createBaseViewHolder(getItemView(layoutResId, parent));
    }

    /**
     * 如果要在适配器中使用BaseViewHolder的子类，则必须重写该方法才能创建新的ViewHolder。
     *
     * @param view view
     * @return new ViewHolder
     */
    @SuppressWarnings("unchecked")
    protected VH createBaseViewHolder(View view) {
        Class temp = getClass();
        Class clazz = null;
        while (clazz == null && null != temp) {
            clazz = getInstanceGenericClass(temp);
            temp = temp.getSuperclass();
        }
        VH holder;
        //泛型擦除会导致clazz为null
        if (clazz == null) {
            holder = (VH) new BaseViewHolder(view);
        } else {
            holder = createGenericInstance(clazz, view);
        }
        return holder != null ? holder : (VH) new BaseViewHolder(view);
    }

    @SuppressWarnings("unchecked")
    private VH createGenericInstance(Class clazz, View view) {
        try {
            Constructor constructor;
            // inner and unStatic class
            if (clazz.isMemberClass() && !Modifier.isStatic(clazz.getModifiers())) {
                constructor = clazz.getDeclaredConstructor(getClass(), View.class);
                constructor.setAccessible(true);
                return (VH) constructor.newInstance(this, view);
            } else {
                constructor = clazz.getDeclaredConstructor(View.class);
                constructor.setAccessible(true);
                return (VH) constructor.newInstance(view);
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Class getInstanceGenericClass(Class clazz) {
        Type type = clazz.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            for (Type temp : types) {
                if (temp instanceof Class) {
                    Class tempClass = (Class) temp;
                    if (BaseViewHolder.class.isAssignableFrom(tempClass)) {
                        return tempClass;
                    }
                } else if (temp instanceof ParameterizedType) {
                    Type rawType = ((ParameterizedType) temp).getRawType();
                    if (rawType instanceof Class && BaseViewHolder.class.isAssignableFrom((Class<?>) rawType)) {
                        return (Class<?>) rawType;
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param layoutResId 要加载的XML布局资源的ID
     * @param parent      可选视图，作为生成层次结构的父级，或者仅是一个对象，该对象为返回的层次结构的根提供一组LayoutParams值
     * @return view
     */
    private View getItemView(@LayoutRes int layoutResId, ViewGroup parent) {
        return mLayoutInflater.inflate(layoutResId, parent, false);
    }

    /**
     * 首先判断是否是空布局，如果是，则根据位置来判断是空布局、头布局、脚布局的哪一个
     * 不是空布局的情况下:
     * 1.首先判断是否是头布局，是就返回头布局
     * 2.否则，拿到条目位置，然后和数据位置做比对，如果位置和数据大小一致，则判断是否是加载布局
     * 3.如果位置是在数据列表中则调用getDefItemViewType()
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (getEmptyViewCount() == 1) {
            boolean header = mHeadAndEmptyEnable && getHeaderLayoutCount() != 0;
            switch (position) {
                case 0:
                    if (header)
                        return HEADER_VIEW;
                    else
                        return EMPTY_VIEW;
                case 1:
                    if (header)
                        return EMPTY_VIEW;
                    else
                        return FOOTER_VIEW;
                case 2:
                    return FOOTER_VIEW;
                default:
                    return EMPTY_VIEW;

            }
        }

        int numHeaders = getHeaderLayoutCount();
        if (position < numHeaders) {
            return HEADER_VIEW;
        } else {
            int adjPosition = position - numHeaders;
            int adapterCount = mData.size();
            if (adjPosition < adapterCount) {
                return getDefItemViewType(adjPosition);
            } else {
                adjPosition = adjPosition - adapterCount;
                int numFooters = getFooterLayoutCount();
                if (adjPosition < numFooters) {
                    return FOOTER_VIEW;
                } else {
                    return LOADING_VIEW;
                }
            }
        }
    }

    private int getDefItemViewType(int position) {
        return super.getItemViewType(position);
    }

    private VH getLoadingView(ViewGroup parent) {
        View view = getItemView(mLoadMoreView.getLayoutId(), parent);
        VH holder = createBaseViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLoadMoreView.getLoadMoreStatus() == LoadMoreView.STATUS_FAIL) {
                    //加载更多失败，点击视图重新加载
                    notifyLoadMoreToLoading();
                }

                if (mEnableLoadMoreEndClick && mLoadMoreView.getLoadMoreStatus() == LoadMoreView.STATUS_END) {
                    notifyLoadMoreToLoading();
                }
            }
        });
        return holder;
    }

    /**
     * 通知启动回调并加载更多
     */
    private void notifyLoadMoreToLoading() {
        if (mLoadMoreView.getLoadMoreStatus() == LoadMoreView.STATUS_LOADING) {
            return;
        }
        mLoadMoreView.setLoadMoreStatus(LoadMoreView.STATUS_DEFAULT);
        notifyItemChanged(getLoadMoreViewPosition());
    }

    /**
     * @return load more 视图的位置
     */
    private int getLoadMoreViewPosition() {
        return getHeaderLayoutCount() + mData.size() + getFooterLayoutCount();
    }

    /**
     * Load more view count
     *
     * @return 0 or 1
     */
    private int getLoadMoreViewCount() {
        if (mRequestLoadMoreListener == null || !mLoadMoreEnable) {
            return 0;
        }
        if (!mNextLoadEnable && mLoadMoreView.isLoadEndMoreGone()) {
            return 0;
        }
        if (mData.size() == 0) {
            return 0;
        }
        return 1;
    }

    public interface RequestLoadMoreListener {
        void onLoadMoreRequested();
    }

    public void setPreLoadNumber(int preLoadNumber) {
        if (preLoadNumber > 1) {
            mPreLoadNumber = preLoadNumber;
        }
    }

    private void autoLoadMore(int position) {
        if (getLoadMoreViewCount() == 0) {
            return;
        }
        if (position < getItemCount() - mPreLoadNumber) {
            return;
        }
        if (mLoadMoreView.getLoadMoreStatus() != LoadMoreView.STATUS_DEFAULT) {
            return;
        }
        mLoadMoreView.setLoadMoreStatus(LoadMoreView.STATUS_LOADING);
        if (!mLoading) {
            mLoading = true;
            if (getRecyclerView() != null) {
                getRecyclerView().post(new Runnable() {
                    @Override
                    public void run() {
                        mRequestLoadMoreListener.onLoadMoreRequested();
                    }
                });
            } else {
                mRequestLoadMoreListener.onLoadMoreRequested();
            }
        }
    }

    /**
     * 如果addFooterView将返回1，否则将返回0
     */
    private int getFooterLayoutCount() {
        if (mFooterLayout == null || mFooterLayout.getChildCount() == 0) {
            return 0;
        }
        return 1;
    }

    /**
     * 如果addHeaderView将返回1，否则将返回0
     */
    private int getHeaderLayoutCount() {
        if (mHeaderLayout == null || mHeaderLayout.getChildCount() == 0) {
            return 0;
        }
        return 1;
    }

    /**
     * 绑定不同类型holder并解决不同的绑定事件
     */
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        int viewType = holder.getItemViewType();

        switch (viewType) {
            case LOADING_VIEW:
                mLoadMoreView.convert(holder);
                break;
            case HEADER_VIEW:
            case FOOTER_VIEW:
            case EMPTY_VIEW:
                break;
            default:
                convert(holder, getItem(position - getHeaderLayoutCount()));
                Log.d(TAG, "--------onBindViewHolder: " + viewType);
                break;
        }
    }

    protected abstract void convert(@NonNull VH holder, T item);

    public void setHeaderViewAsFlow(boolean headerViewAsFlow) {
        this.headerViewAsFlow = headerViewAsFlow;
    }

    public void setFooterViewAsFlow(boolean footerViewAsFlow) {
        this.footerViewAsFlow = footerViewAsFlow;
    }

    private boolean isHeaderViewAsFlow() {
        return headerViewAsFlow;
    }

    private boolean isFooterViewAsFlow() {
        return footerViewAsFlow;
    }

    private SpanSizeLookup mSpanSizeLookup;

    public interface SpanSizeLookup {
        int getSpanSize(GridLayoutManager gridLayoutManager, int position);
    }

    /**
     * @param spanSizeLookup 用于查询每个项目所占跨度的实例
     */
    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup;
    }

    /**
     * 获取与数据集中指定位置关联的数据项
     *
     * @param position 数据在适配器的数据集中的位置
     * @return 指定位置的数据
     */
    public T getItem(int position) {
        if (position >= 0 && position < mData.size()) {
            return mData.get(position);
        }
        return null;
    }

    /**
     * 是空布局：
     * 根据是否有头布局和脚布局来增加数量
     * 没有空布局：
     * 数量 = 头布局+数据条目数量 + 脚布局 + 加载更多布局
     */
    @Override
    public int getItemCount() {
        int count;
        if (1 == getEmptyViewCount()) {
            count = 1;
            if (mHeadAndEmptyEnable && getHeaderLayoutCount() != 0) {
                count++;
            }
            if (mFootAndEmptyEnable && getFooterLayoutCount() != 0) {
                count++;
            }
        } else {
            count = getHeaderLayoutCount() + mData.size() + getFooterLayoutCount() + getLoadMoreViewCount();
        }
        return count;
    }

    /**
     * 将页脚追加到mFooterLayout的后面
     */
    public int addFooterView(View footer) {
        return addFooterView(footer, -1);
    }

    private int addFooterView(View footer, int index) {
        return addFooterView(footer, index, LinearLayout.VERTICAL);
    }

    /**
     * 将页脚视图添加到mFooterLayout并在mFooterLayout中设置页脚视图的位置。
     * 当mFooterLayout中的index = -1或index> = child count时,
     * 此方法的效果与{@link #addFooterView（View）}相同。
     */
    private int addFooterView(View footer, int index, int orientation) {
        if (mFooterLayout == null) {
            mFooterLayout = new LinearLayout(footer.getContext());
            if (orientation == LinearLayout.VERTICAL) {
                mFooterLayout.setOrientation(LinearLayout.VERTICAL);
                mFooterLayout.setLayoutParams(new LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            } else {
                mFooterLayout.setOrientation(LinearLayout.HORIZONTAL);
                mFooterLayout.setLayoutParams(new LayoutParams(WRAP_CONTENT, MATCH_PARENT));
            }
        }
        final int childCount = mFooterLayout.getChildCount();
        if (index < 0 || index > childCount) {
            index = childCount;
        }
        mFooterLayout.addView(footer, index);
        if (mFooterLayout.getChildCount() == 1) {
            int position = getFooterViewPosition();
            if (position != -1) {
                notifyItemInserted(position);
            }
        }
        return index;
    }

    /**
     * @return 页脚视图通知位置
     */
    private int getFooterViewPosition() {
        if (getEmptyViewCount() == 1) {
            int position = 1;
            if (mHeadAndEmptyEnable && getHeaderLayoutCount() != 0) {
                position++;
            }
            if (mFootAndEmptyEnable) {
                return position;
            }
        } else {
            return getHeaderLayoutCount() + mData.size();
        }
        return -1;
    }

    /**
     * @param header 添加的header视图
     */
    public int addHeaderView(View header) {
        return addHeaderView(header, -1);
    }

    /**
     * @param header 需要添加的header视图
     * @param index  此标头在mHeaderLayout中的位置。当mHeaderLayout中的index = -1或index> = child count时，
     *               此方法的效果与{@link #addHeaderView（View）}相同
     */
    public int addHeaderView(View header, int index) {
        return addHeaderView(header, index, LinearLayout.VERTICAL);
    }

    public int addHeaderView(View header, final int index, int orientation) {
        if (mHeaderLayout == null) {
            mHeaderLayout = new LinearLayout(header.getContext());
            if (orientation == LinearLayout.VERTICAL) {
                mHeaderLayout.setOrientation(LinearLayout.VERTICAL);
                mHeaderLayout.setLayoutParams(new LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            } else {
                mHeaderLayout.setOrientation(LinearLayout.HORIZONTAL);
                mHeaderLayout.setLayoutParams(new LayoutParams(WRAP_CONTENT, MATCH_PARENT));
            }
        }
        final int childCount = mHeaderLayout.getChildCount();
        int mIndex = index;
        if (index < 0 || index > childCount) {
            mIndex = childCount;
        }
        mHeaderLayout.addView(header, mIndex);
        if (mHeaderLayout.getChildCount() == 1) {
            int position = getHeaderViewPosition();
            if (position != -1) {
                notifyItemInserted(position);
            }
        }
        return mIndex;
    }

    /**
     * @return 标题视图通知位置
     */
    private int getHeaderViewPosition() {
        if (getEmptyViewCount() == 1) {
            if (mHeadAndEmptyEnable) {
                return 0;
            }
        } else {
            return 0;
        }
        return -1;
    }

    /**
     * @return 如果显示空视图将返回1，否则将返回0
     */
    private int getEmptyViewCount() {
        if (mEmptyLayout == null || mEmptyLayout.getChildCount() == 0) {
            return 0;
        }
        if (!mIsUseEmpty) {
            return 0;
        }
        if (mData.size() != 0) {
            return 0;
        }
        return 1;
    }

    /**
     * 单击此视图中的itemChild时要调用的回调的接口定义
     */
    public interface OnItemChildClickListener {
        /**
         * @param adapter  适配器
         * @param view     单击的itemView的childView
         * @param position 位置
         */
        void onItemChildClick(BaseAdapter adapter, View view, int position);
    }

    /**
     * 长按视图中的childView时要调用的回调的接口定义。
     */
    public interface OnItemChildLongClickListener {
        /**
         * @param adapter  this BaseAdapter adapter
         * @param view     单击并按住的itemView的childView
         * @param position 位置
         * @return 如果回调消耗了长按，则返回true，否则返回false
         */
        boolean onItemChildLongClick(BaseAdapter adapter, View view, int position);
    }

    /**
     * 长按视图时要调用的回调的接口定义
     */
    public interface OnItemLongClickListener {
        /**
         * @param adapter  the adapter
         * @param view     单击并按住RecyclerView中的视图
         * @param position 位置
         * @return 如果回调消耗了长按，则返回true，否则返回false
         */
        boolean onItemLongClick(BaseAdapter adapter, View view, int position);
    }

    /**
     * 单击此RecyclerView的itemView要调用的回调的接口定义
     */
    public interface OnItemClickListener {

        /**
         * @param adapter  the adapter
         * @param view     单击的RecyclerView中的itemView
         * @param position 位置
         */
        void onItemClick(BaseAdapter adapter, View view, int position);
    }

    /**
     * 注册一个回调，当单击此RecyclerView中的某个项目时将调用该回调。
     *
     * @param listener 将被调用的回调
     */
    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * 注册一个回调，以在单击View中的itemChild后被调用
     *
     * @param listener 将被调用的回调
     */
    public void setOnItemChildClickListener(OnItemChildClickListener listener) {
        mOnItemChildClickListener = listener;
    }

    /**
     * 注册一个RecyclerView中的itemView已被长按并保持时要调用的回调
     *
     * @param listener 将被调用的回调
     */
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    /**
     * 注册一个回调，当长按并按住此视图中的itemChild时调用
     *
     * @param listener 将被调用的回调
     */
    public void setOnItemChildLongClickListener(OnItemChildLongClickListener listener) {
        mOnItemChildLongClickListener = listener;
    }


    public final OnItemLongClickListener getOnItemLongClickListener() {
        return mOnItemLongClickListener;
    }

    public final OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    @Nullable
    public final OnItemChildClickListener getOnItemChildClickListener() {
        return mOnItemChildClickListener;
    }

    @Nullable
    public final OnItemChildLongClickListener getOnItemChildLongClickListener() {
        return mOnItemChildLongClickListener;
    }

}
