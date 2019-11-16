package com.fr.wanandroid.ui.project.vm;

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
import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * 创建时间：2019/11/7
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class ProjectViewModel extends BaseViewModel<ModelRepository> {
    private boolean IS_FIRST_LOAD = true;
    private int id = -1;
    private int page = 0;
    public BindingRecyclerViewAdapter rvAdapter = new BindingRecyclerViewAdapter();
    public UIChangeObservable uc = new UIChangeObservable();

    public ProjectViewModel(@NonNull Application application, ModelRepository model) {
        super(application, model);
    }

    public ObservableList<ItemProjectViewModel> itemProjectViewModelObservableList = new ObservableArrayList<>();
    public ItemBinding<ItemProjectViewModel> itemProjectViewModelItemBinding = ItemBinding.of(BR.viewModel, R.layout.item_project_chapter);

    public void getProjectChapter() {
        if (IS_FIRST_LOAD) {
            getProjectChapters();
            IS_FIRST_LOAD = false;
        }
    }

    @SuppressWarnings("unchecked")
    private void getProjectChapters() {
        model.getProjectChapters()
                .compose(RxUtils.applySchedulers())
                .doOnSubscribe(this)
                .subscribe(new Observer<WanResponse<List<ChapterBean>>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        showDialog("正在加载数据...");
                    }

                    @Override
                    public void onNext(WanResponse<List<ChapterBean>> response) {
                        if (response.getCode() == 0) {
                            for (ChapterBean bean : response.getData()) {
                                ItemProjectViewModel itemProjectViewModel = new ItemProjectViewModel(ProjectViewModel.this, bean);
                                itemProjectViewModelObservableList.add(itemProjectViewModel);
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

    public class UIChangeObservable {
        public SingleLiveEvent finishRefreshing = new SingleLiveEvent<>();
        public SingleLiveEvent finishLoadMore = new SingleLiveEvent<>();
    }

    public ObservableList<ItemArticleViewModel> itemArticleViewModelObservableList = new ObservableArrayList<>();
    public ItemBinding<ItemArticleViewModel> itemArticleViewModelItemBinding = ItemBinding.of(BR.viewModel, R.layout.item_article);

    public void getProjectArticle(int id) {
        if (this.id != id) {
            getProjectArticles(Constants.TYPE_REFRESH, 0, id);
            this.id = id;
        }
    }

    public BindingCommand onRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            getProjectArticles(Constants.TYPE_REFRESH, 0, id);
        }
    });

    public BindingCommand onLoadCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            page++;
            getProjectArticles(Constants.TYPE_LOAD_MORE, page, id);
        }
    });

    @SuppressWarnings("unchecked")
    private void getProjectArticles(final int state, final int page, final int id) {
        model.getProjectArticles(page, id)
                .compose(RxUtils.applySchedulers())
                .doOnSubscribe(this)
                .subscribe(new Observer<WanResponse<ArticleListBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (state == Constants.TYPE_REFRESH) {
                            showDialog("正在加载数据...");
                            if (itemArticleViewModelObservableList.size() != 0) {
                                itemArticleViewModelObservableList.clear();
                            }
                        }
                    }

                    @Override
                    public void onNext(WanResponse<ArticleListBean> response) {
                        if (response.getCode() == 0) {
                            if (state == Constants.TYPE_LOAD_MORE && response.getData().getDatas().size() == 0){
                                ToastUtils.showShort(getApplication(),"已经是此类的全部文章了");
                            }
                            for (ArticleBean bean : response.getData().getDatas()) {
                                ItemArticleViewModel itemArticleViewModel = new ItemArticleViewModel(ProjectViewModel.this, bean);
                                itemArticleViewModelObservableList.add(itemArticleViewModel);
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
