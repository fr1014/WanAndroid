package com.fr.wanandroid.ui.wxarticle.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.fr.mvvm.base.BaseViewModel;
import com.fr.mvvm.binding.command.BindingAction;
import com.fr.mvvm.binding.command.BindingCommand;
import com.fr.mvvm.bus.event.SingleLiveEvent;
import com.fr.mvvm.utils.RxUtils;
import com.fr.mvvm.utils.ToastUtils;
import com.fr.wanandroid.BR;
import com.fr.wanandroid.R;
import com.fr.wanandroid.entity.ArticleBean;
import com.fr.wanandroid.entity.ArticleListBean;
import com.fr.wanandroid.entity.ChapterBean;
import com.fr.wanandroid.http.WanResponse;
import com.fr.wanandroid.model.ModelRepository;
import com.fr.wanandroid.ui.Constants;
import com.fr.wanandroid.ui.main.vm.ItemArticleViewModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * 创建时间：2019/10/9
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class WxViewModel extends BaseViewModel<ModelRepository> {
    private boolean IS_FIRST = true;
    private int id = -1;
    private int page = 0;
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent finishRefreshing = new SingleLiveEvent<>();
        public SingleLiveEvent finishLoadMore = new SingleLiveEvent<>();
    }

    public WxViewModel(@NonNull Application application, ModelRepository model) {
        super(application, model);
    }

    public ObservableList<ItemWxViewModel> wxChapterObservableList = new ObservableArrayList<>();
    public ItemBinding<ItemWxViewModel> wxChapterItemBinding = ItemBinding.of(BR.itemViewModel, R.layout.item_wxchapter);

    public void wxChapter() {
        if (IS_FIRST) {
            getWxChapter();
            IS_FIRST = false;
        }
    }

    @SuppressWarnings("unchecked")
    public void getWxChapter() {
        model.getWxChapter()
                .compose(RxUtils.applySchedulers())
                .doOnSubscribe(this)
                .subscribe(new Observer<WanResponse<List<ChapterBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        showDialog("正在加载数据...");
                    }

                    @Override
                    public void onNext(WanResponse<List<ChapterBean>> response) {
                        wxChapterObservableList.clear();
                        //请求数据成功
                        if (response.getCode() == 0) {
                            for (ChapterBean bean : response.getData()) {
                                ItemWxViewModel itemWxViewModel = new ItemWxViewModel(WxViewModel.this, bean);
                                wxChapterObservableList.add(itemWxViewModel);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }
                });

    }

    public ObservableList<ItemArticleViewModel> wxArticleObservableList = new ObservableArrayList<>();
    public ItemBinding<ItemArticleViewModel> wxArticleItemBinding = ItemBinding.of(BR.viewModel, R.layout.item_article);

    public void getWxArticles(int id) {
        if (this.id != id) {
            getWxArticles(Constants.TYPE_REFRESH, id, 0);
            this.id = id;
        }
    }

    public BindingCommand onRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            getWxArticles(Constants.TYPE_REFRESH, id, 0);
        }
    });

    public BindingCommand onLoadMoreCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            page++;
            getWxArticles(Constants.TYPE_LOAD_MORE, id, page);
        }
    });

    @SuppressWarnings("unchecked")
    private void getWxArticles(final int state, int id, int page) {
        model.getWxArticles(id, page)
                .compose(RxUtils.applySchedulers())
                .doOnSubscribe(this)
                .subscribe(new Observer<WanResponse<ArticleListBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (state == Constants.TYPE_REFRESH) {
                            showDialog("正在请求数据...");
                            if (wxArticleObservableList.size() != 0) {
                                wxArticleObservableList.clear();
                            }
                        } else {
                            ToastUtils.showShort(getApplication(), "正在加载数据...");
                        }
                    }

                    @Override
                    public void onNext(WanResponse<ArticleListBean> response) {
                        if (response.getCode() == 0) {
                            for (ArticleBean bean : response.getData().getDatas()) {
                                ItemArticleViewModel itemArticleViewModel = new ItemArticleViewModel(WxViewModel.this, bean);
                                wxArticleObservableList.add(itemArticleViewModel);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (state == Constants.TYPE_REFRESH) {
                            uc.finishRefreshing.call();
                            dismissDialog();
                        } else {
                            uc.finishLoadMore.call();
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (state == Constants.TYPE_REFRESH) {
                            uc.finishRefreshing.call();
                            dismissDialog();
                        } else {
                            uc.finishLoadMore.call();
                        }
                    }
                });
    }

}
