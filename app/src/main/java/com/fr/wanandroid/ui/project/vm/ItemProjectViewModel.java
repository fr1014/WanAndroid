package com.fr.wanandroid.ui.project.vm;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.ObservableField;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.fr.mvvm.base.ItemViewModel;
import com.fr.mvvm.binding.command.BindingCommand;
import com.fr.mvvm.binding.command.BindingConsumer;
import com.fr.wanandroid.R;
import com.fr.wanandroid.entity.ChapterBean;

import org.jetbrains.annotations.NotNull;

/**
 * 创建时间：2019/11/7
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class ItemProjectViewModel extends ItemViewModel<ProjectViewModel> {
    public ObservableField<ChapterBean> entity = new ObservableField<>();

    public ItemProjectViewModel(@NotNull ProjectViewModel viewModel,ChapterBean entity) {
        super(viewModel);
        this.entity.set(entity);
    }

    public BindingCommand<View> onClickCommand = new BindingCommand<View>(new BindingConsumer<View>() {
        @Override
        public void call(View view) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("entity",entity.get());
            NavController controller = Navigation.findNavController(view);
            controller.navigate(R.id.action_projectFragment_to_articleProjectFragment,bundle);
        }
    });
}
