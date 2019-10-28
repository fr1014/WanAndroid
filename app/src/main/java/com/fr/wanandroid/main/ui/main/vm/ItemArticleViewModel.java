package com.fr.wanandroid.main.ui.main.vm;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.ObservableField;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.fr.mvvm.base.BaseViewModel;
import com.fr.mvvm.base.ItemViewModel;
import com.fr.mvvm.binding.command.BindingCommand;
import com.fr.mvvm.binding.command.BindingConsumer;
import com.fr.wanandroid.R;
import com.fr.wanandroid.main.entity.ArticleBean;

import org.jetbrains.annotations.NotNull;

/**
 * 创建时间：2019/10/14
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class ItemArticleViewModel extends ItemViewModel<BaseViewModel> {
    public ObservableField<ArticleBean> entity = new ObservableField<>();
    public ObservableField<Boolean> isThrottleFirst = new ObservableField<>(); //是否开启防抖动
//    public Drawable drawableImg;

    public ItemArticleViewModel(@NotNull BaseViewModel viewModel, ArticleBean entity) {
        super(viewModel);
        this.entity.set(entity);
        this.isThrottleFirst.set(true);
        //ImageView的占位图片，可以解决RecyclerView中图片错误问题
//        drawableImg = ContextCompat.getDrawable(viewModel.getApplication(), R.mipmap.ic_launcher);
    }

    public BindingCommand<View> itemClick = new BindingCommand<View>(new BindingConsumer<View>() {
        @Override
        public void call(View view) {
            //跳转到网页界面，传入条目的实体对象
            Bundle bundle = new Bundle();
            bundle.putParcelable("entity", entity.get());
            NavController controller = Navigation.findNavController(view);
            controller.navigate(R.id.webViewFragment, bundle);
        }
    });
}
