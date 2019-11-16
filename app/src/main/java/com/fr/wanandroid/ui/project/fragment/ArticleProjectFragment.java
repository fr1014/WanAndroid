package com.fr.wanandroid.ui.project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.fr.mvvm.base.BaseFragment;
import com.fr.wanandroid.BR;
import com.fr.wanandroid.R;
import com.fr.wanandroid.app.ViewModelFactory;
import com.fr.wanandroid.databinding.FragmentProjectArticleBinding;
import com.fr.wanandroid.entity.ChapterBean;
import com.fr.wanandroid.ui.project.vm.ProjectViewModel;

/**
 * 创建时间：2019/11/8
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class ArticleProjectFragment extends BaseFragment<FragmentProjectArticleBinding, ProjectViewModel> {

    private ChapterBean bean = null;

    @Override
    public void initParam() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            bean = bundle.getParcelable("entity");
        }
    }

    @Override
    protected int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_project_article;
    }

    @Override
    protected int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public ProjectViewModel initViewModel() {
        if (getActivity() != null) {
            ViewModelFactory factory = ViewModelFactory.getInstance(getActivity().getApplication());
            return ViewModelProviders.of(getActivity()).get(ProjectViewModel.class);
        }
        return null;
    }

    @Override
    public void initData() {
        if (bean != null) {
            viewModel.getProjectArticle(bean.getId());
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
