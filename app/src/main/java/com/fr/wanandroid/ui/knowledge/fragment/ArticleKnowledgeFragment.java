package com.fr.wanandroid.ui.knowledge.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.fr.mvvm.base.BaseFragment;
import com.fr.wanandroid.BR;
import com.fr.wanandroid.R;
import com.fr.wanandroid.app.ViewModelFactory;
import com.fr.wanandroid.databinding.FragmentKnowledgeArticleBinding;
import com.fr.wanandroid.entity.ChapterBean;
import com.fr.wanandroid.ui.knowledge.vm.KnowledgeViewModel;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

public class ArticleKnowledgeFragment extends BaseFragment<FragmentKnowledgeArticleBinding, KnowledgeViewModel> {

    private ChapterBean bean;

    @Override
    public void initParam() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            bean = bundle.getParcelable("entity");
        }
    }

    @Override
    protected int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_knowledge_article;
    }

    @Override
    protected int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public KnowledgeViewModel initViewModel() {
        if (getActivity()!=null){
            ViewModelFactory factory = ViewModelFactory.getInstance(getActivity().getApplication());
            return ViewModelProviders.of(getActivity(), factory).get(KnowledgeViewModel.class);
        }
        return null;
    }

    @Override
    public void initData() {
        binding.setAdapter(new BindingRecyclerViewAdapter());
        if (bean != null) {
            viewModel.getKnowledgeArticles(bean.getId());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initViewObservable() {
        viewModel.uc.finishRefreshing.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                binding.twinklingRefreshLayout.finishRefreshing();
            }
        });

        viewModel.uc.finishLoadMore.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                binding.twinklingRefreshLayout.finishLoadmore();
            }
        });
    }
}
