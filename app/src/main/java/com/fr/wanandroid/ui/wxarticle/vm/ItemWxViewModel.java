package com.fr.wanandroid.ui.wxarticle.vm;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.ObservableField;
import androidx.navigation.NavAction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.fr.mvvm.base.ItemViewModel;
import com.fr.mvvm.binding.command.BindingCommand;
import com.fr.mvvm.binding.command.BindingConsumer;
import com.fr.wanandroid.R;
import com.fr.wanandroid.entity.ChapterBean;

import org.jetbrains.annotations.NotNull;

/**
 * 创建时间：2019/11/5
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class ItemWxViewModel extends ItemViewModel<WxViewModel> {

    public ObservableField<ChapterBean> entity = new ObservableField<>();
    public ObservableField<Boolean> is = new ObservableField<>();

    public ItemWxViewModel(@NotNull WxViewModel viewModel,ChapterBean bean) {
        super(viewModel);
        entity.set(bean);
        is.set(true);
    }

    public BindingCommand<View> onClickCommand = new BindingCommand<View>(new BindingConsumer<View>() {
        @Override
        public void call(View view) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("entity",entity.get());
            NavController controller = Navigation.findNavController(view);
            controller.navigate(R.id.action_wxChapterFragment_to_wxArticleFragment,bundle);
        }
    });
}
