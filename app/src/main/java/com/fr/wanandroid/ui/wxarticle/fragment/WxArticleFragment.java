package com.fr.wanandroid.ui.wxarticle.fragment;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.fr.mvvm.base.BaseFragment;
import com.fr.wanandroid.BR;
import com.fr.wanandroid.R;
import com.fr.wanandroid.app.ViewModelFactory;
import com.fr.wanandroid.databinding.FragmentWxArticleBinding;
import com.fr.wanandroid.entity.ChapterBean;
import com.fr.wanandroid.ui.wxarticle.vm.WxViewModel;

public class WxArticleFragment extends BaseFragment<FragmentWxArticleBinding, WxViewModel> {

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
        return R.layout.fragment_wx_article;
    }

    @Override
    protected int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public WxViewModel initViewModel() {
        if (getActivity() != null) {
            ViewModelFactory factory = ViewModelFactory.getInstance(getActivity().getApplication());
            return ViewModelProviders.of(getActivity(), factory).get(WxViewModel.class);
        }
        return null;
    }

    @Override
    public void initData() {
        if (bean != null) {
            viewModel.getWxArticles(bean.getId());
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
