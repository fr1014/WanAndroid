package com.fr.wanandroid.main.ui.wxarticle.fragment;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProviders;

import com.fr.mvvm.base.BaseFragment;
import com.fr.wanandroid.R;
import com.fr.wanandroid.app.ViewModelFactory;
import com.fr.wanandroid.app.MyApplication;
import com.fr.wanandroid.databinding.FragmentHomeBinding;
import com.fr.wanandroid.main.ui.wxarticle.vm.ArticleViewModel;

public class ArticleFragment extends BaseFragment<FragmentHomeBinding, ArticleViewModel> {

    @Override
    protected int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_home;
    }

    @Override
    protected int initVariableId() {
        return 0;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
    }

    public ArticleViewModel initViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance((Application) MyApplication.getContext());
        return ViewModelProviders.of(this,factory).get(ArticleViewModel.class);
    }

}
