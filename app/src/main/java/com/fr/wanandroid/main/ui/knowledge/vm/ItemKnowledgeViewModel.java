package com.fr.wanandroid.main.ui.knowledge.vm;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.ObservableField;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.fr.mvvm.base.ItemViewModel;
import com.fr.mvvm.binding.command.BindingCommand;
import com.fr.mvvm.binding.command.BindingConsumer;
import com.fr.wanandroid.R;
import com.fr.wanandroid.main.entity.ChapterBean;

import org.jetbrains.annotations.NotNull;

/**
 * 创建时间：2019/10/22
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class ItemKnowledgeViewModel extends ItemViewModel<KnowledgeViewModel> {
    public ObservableField<ChapterBean> entity = new ObservableField<>();
    public ObservableField<String> detailString = new ObservableField<>();
    public ObservableField<Boolean> isThrottleFirst = new ObservableField<>();

    public ItemKnowledgeViewModel(@NotNull KnowledgeViewModel viewModel, ChapterBean entity) {
        super(viewModel);
        this.entity.set(entity);
        detailString.set(detail());
        isThrottleFirst.set(true);
    }

    private String detail() {
        StringBuilder sb = new StringBuilder();
        for (ChapterBean bean : entity.get().getChildren()) {
            sb.append(bean.getName());
            sb.append(" ");
        }
        return sb.toString();
    }

    public BindingCommand<View> clickToItemCommand = new BindingCommand<View>(new BindingConsumer<View>() {
        @Override
        public void call(View view) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("entity",entity.get());
            NavController controller = Navigation.findNavController(view);
            controller.navigate(R.id.action_knowledgeFragment_to_listKnowledgeFragment,bundle);
        }
    });

}