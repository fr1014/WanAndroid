package com.fr.wanandroid.ui.wxarticle.fragment;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProviders;

import com.fr.mvvm.base.BaseFragment;
import com.fr.wanandroid.BR;
import com.fr.wanandroid.R;
import com.fr.wanandroid.app.ViewModelFactory;
import com.fr.wanandroid.app.MyApplication;
import com.fr.wanandroid.databinding.FragmentWxchapterBinding;
import com.fr.wanandroid.ui.wxarticle.vm.WxViewModel;

public class WxChapterFragment extends BaseFragment<FragmentWxchapterBinding, WxViewModel> {

    @Override
    protected int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_wxchapter;
    }

    @Override
    protected int initVariableId() {
        return BR.viewModel;
    }

    public WxViewModel initViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance((Application) MyApplication.getContext());
        return ViewModelProviders.of(this,factory).get(WxViewModel.class);
    }

    @Override
    public void initData() {
        viewModel.wxChapter();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
    }

}
