package com.fr.wanandroid.ui.knowledge.vm;

import android.annotation.SuppressLint;
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
import com.fr.wanandroid.http.WanResponse;
import com.fr.wanandroid.entity.ArticleBean;
import com.fr.wanandroid.entity.ArticleListBean;
import com.fr.wanandroid.entity.ChapterBean;
import com.fr.wanandroid.model.ModelRepository;
import com.fr.wanandroid.ui.Constants;
import com.fr.wanandroid.ui.main.vm.ItemArticleViewModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * 创建时间：2019/10/22
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class KnowledgeViewModel extends BaseViewModel<ModelRepository> {
    private boolean LOAD_KNOWLEDGE_FIRST = true;
    private int page = 0;
    private int id = -1;  //加载类别
    public BindingRecyclerViewAdapter<ItemKnowledgeViewModel> rvAdapter = new BindingRecyclerViewAdapter<ItemKnowledgeViewModel>();

    public KnowledgeViewModel(@NonNull Application application, ModelRepository model) {
        super(application, model);
    }

    //界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //下拉刷新完成
        public SingleLiveEvent finishRefreshing = new SingleLiveEvent<>();
        //上拉加载完成
        public SingleLiveEvent finishLoadMore = new SingleLiveEvent();
    }

    //知识体系导航
    public final ObservableList<ItemKnowledgeViewModel> itemKnowledgeViewModels = new ObservableArrayList<>();
    public final ItemBinding<ItemKnowledgeViewModel> viewModelItemBinding = ItemBinding.of(BR.viewModel, R.layout.item_knowledge);

    //knowledge_chapter列表
    public final ObservableList<ItemKnowledgeViewModel> itemChapterBeans = new ObservableArrayList<>();
    public final ItemBinding<ItemKnowledgeViewModel> itemChapterBinding = ItemBinding.of(BR.viewModel, R.layout.item_knowledge_chapter);

    //下拉刷新
    public BindingCommand refreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            knowledgeGet();
        }
    });

    public void getKnowledge() {
        if (LOAD_KNOWLEDGE_FIRST) {
            knowledgeGet();
            LOAD_KNOWLEDGE_FIRST = false;
        }
    }

    @SuppressWarnings("unchecked")
    @SuppressLint("CheckResult")
    private void knowledgeGet() {
        model.getKnowledgeChapter()
                .compose(RxUtils.applySchedulers())
                .doOnSubscribe(this)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("正在请求数据...");
                    }
                })
                .subscribe(new Consumer<WanResponse<List<ChapterBean>>>() {
                    @Override
                    public void accept(WanResponse<List<ChapterBean>> response) throws Exception {
                        itemKnowledgeViewModels.clear();
                        if (response.getCode() == 0) {
                            for (ChapterBean entity : response.getData()) {
                                ItemKnowledgeViewModel itemViewModel = new ItemKnowledgeViewModel(KnowledgeViewModel.this, entity);
                                itemKnowledgeViewModels.add(itemViewModel);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        uc.finishRefreshing.call();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        dismissDialog();
                        uc.finishRefreshing.call();
                    }
                });
    }

    //knowledge_list_article
    public final ObservableList<ItemArticleViewModel> itemArticles = new ObservableArrayList<>();
    public final ItemBinding<ItemArticleViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_article);

    //下拉刷新
    public BindingCommand onRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            page = 0;
            getKnowledgeArticles(Constants.TYPE_REFRESH, id);
        }
    });

    //上拉加载
    public BindingCommand onLoadMoreCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            page++;
            getKnowledgeArticles(Constants.TYPE_LOAD_MORE, id);
        }
    });

    public void getKnowledgeArticles(int id) {
        page = 0;
        if (this.id != id) {
            getKnowledgeArticles(Constants.TYPE_REFRESH, id);
            this.id = id;
        }
    }

    @SuppressWarnings("unchecked")
    private void getKnowledgeArticles(final int state, int id) {
        model.getKnowledgeArticles(page, id)
                .compose(RxUtils.applySchedulers())
                .doOnSubscribe(this)
                .subscribe(new Observer<WanResponse<ArticleListBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (state == Constants.TYPE_REFRESH) {
                            showDialog("正在加载中...");
                            if (itemArticles.size() != 0) {
                                itemArticles.clear();
                            }
                        }
                    }

                    @Override
                    public void onNext(WanResponse<ArticleListBean> response) {
                        if (response.getCode() == 0) {
                            if (state == Constants.TYPE_LOAD_MORE && response.getData().getDatas().size() == 0) {
                                ToastUtils.showShort(getApplication(), "已经是选中类别的全部文章了");
                            }
                            for (ArticleBean entity : response.getData().getDatas()) {
                                ItemArticleViewModel itemArticleViewModel = new ItemArticleViewModel(KnowledgeViewModel.this, entity);
                                itemArticles.add(itemArticleViewModel);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (state == Constants.TYPE_REFRESH) {
                            dismissDialog();
                            uc.finishRefreshing.call();
                        } else {
                            uc.finishLoadMore.call();
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (state == Constants.TYPE_REFRESH) {
                            dismissDialog();
                            uc.finishRefreshing.call();
                        } else {
                            uc.finishLoadMore.call();
                        }
                    }
                });
    }
}
