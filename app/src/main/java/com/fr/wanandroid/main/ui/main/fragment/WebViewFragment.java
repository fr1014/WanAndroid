package com.fr.wanandroid.main.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.fr.mvvm.base.BaseFragment;
import com.fr.wanandroid.BR;
import com.fr.wanandroid.R;
import com.fr.wanandroid.databinding.FragmentWebviewBinding;
import com.fr.wanandroid.main.entity.ArticleBean;
import com.fr.wanandroid.main.ui.main.vm.DetailViewModel;

public class WebViewFragment extends BaseFragment<FragmentWebviewBinding, DetailViewModel> {
    private ArticleBean entity;

    @Override
    public void initParam() {
        //获取列表传入的实体
        Bundle bundle = getArguments();
        if (bundle!=null){
            entity = bundle.getParcelable("entity");
        }
    }

    @Override
    protected int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_webview;
    }

    @Override
    protected int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.setEntity(entity);
//        getLifecycle().addObserver(viewModel);
    }
}
