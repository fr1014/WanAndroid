package com.fr.wanandroid.main.ui.home.vm;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.fr.mvvm.base.BaseViewModel;
import com.fr.mvvm.binding.command.BindingAction;
import com.fr.mvvm.binding.command.BindingCommand;
import com.fr.mvvm.binding.command.BindingConsumer;
import com.fr.mvvm.bus.event.SingleLiveEvent;
import com.fr.mvvm.utils.RxUtils;
import com.fr.mvvm.utils.ToastUtils;
import com.fr.wanandroid.BR;
import com.fr.wanandroid.R;
import com.fr.wanandroid.http.WanResponse;
import com.fr.wanandroid.main.entity.ArticleBean;
import com.fr.wanandroid.main.entity.ArticleListBean;
import com.fr.wanandroid.main.model.ModelRepository;
import com.fr.wanandroid.main.ui.main.vm.ItemArticleViewModel;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

import static com.fr.wanandroid.main.ui.Constants.TYPE_LOAD_MORE;
import static com.fr.wanandroid.main.ui.Constants.TYPE_REFRESH;

/**
 * 创建时间：2019/10/12
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class HomeViewModel extends BaseViewModel<ModelRepository> {

    private Boolean LOAD_FIRST = true;
    private int page = 0;

    //界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //下拉刷新完成
        public SingleLiveEvent finishRefreshing = new SingleLiveEvent<>();
    }

    public HomeViewModel(@NonNull Application application, ModelRepository repository) {
        super(application, repository);
    }

    //viewModel for RecyclerView
    public final ObservableList<ItemArticleViewModel> itemArticleViewModels = new ObservableArrayList<>();
    // view layout for RecyclerView
    public final ItemBinding<ItemArticleViewModel> itemArticleBinding = ItemBinding.of(BR.viewModel, R.layout.item_article);

    public void getArticle() {
        if (LOAD_FIRST) {
            articleGet(TYPE_REFRESH);
            LOAD_FIRST = false;
        }
    }

    //下拉刷新
    public BindingCommand onRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //请求数据
            page = 0;
            articleGet(TYPE_REFRESH);
        }
    });

    /**
     * 上拉加载
     */
    public BindingCommand<Integer> onLoadMoreCommand = new BindingCommand<>(new BindingConsumer<Integer>() {
        @SuppressLint("CheckResult")
        @Override
        public void call(Integer integer) {
            page = integer;
            articleGet(TYPE_LOAD_MORE);
        }
    });

    /**
     * 网络请求方法，在viewModel层中调用Model层
     */
    @SuppressWarnings("unchecked")
    @SuppressLint("CheckResult")
    protected void articleGet(final int state) {

        model.articleGet(page)
                .compose(RxUtils.applySchedulers())//线程调度
                //doOnSubscribe() 的作用就是只有订阅时才会发送事件
                //请求与ViewModel周期同步(Observable 每发送 onSubscribe() 之前都会回调这个方法)
                .doOnSubscribe(this)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (state == TYPE_LOAD_MORE) {
                            ToastUtils.showShort(getApplication(), "正在加载数据...");
                        } else if (state == TYPE_REFRESH) {
                            showDialog("正在请求数据...");
                            //清除列表
                            itemArticleViewModels.clear();
                        }
                    }
                })
                .subscribe((Consumer<WanResponse<ArticleListBean>>) response -> {
                    //请求成功
                    if (response.getCode() == 0) {
                        List<ArticleBean> list = response.getData().getDatas();
                        for (ArticleBean bean : list) {
                            ItemArticleViewModel itemViewModel = new ItemArticleViewModel(HomeViewModel.this, bean);
                            //双向绑定动态添加Item
                            itemArticleViewModels.add(itemViewModel);
                        }
                    } else {
                        //请求失败
                        //TODO
                    }
                }, throwable -> {
                    //关闭对话框
                    dismissDialog();
//                    请求刷新完成回收
                    uc.finishRefreshing.call();

                    //可以自定义Exception类处理异常信息
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //关闭对话框
                        dismissDialog();
                        uc.finishRefreshing.call();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
