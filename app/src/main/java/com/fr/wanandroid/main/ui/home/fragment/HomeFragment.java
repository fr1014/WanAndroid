package com.fr.wanandroid.main.ui.home.fragment;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.fr.mvvm.base.BaseFragment;
import com.fr.wanandroid.BR;
import com.fr.wanandroid.R;
import com.fr.wanandroid.app.ViewModelFactory;
import com.fr.wanandroid.databinding.FragmentHomeBinding;
import com.fr.wanandroid.main.ui.home.vm.HomeViewModel;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel>{

    @Override
    public void initParam() {
        super.initParam();
    }

    @Override
    protected int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_home;
    }

    @Override
    protected int initVariableId() {
        return BR.viewModel;
    }

     public HomeViewModel initViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(getActivity(),factory).get(HomeViewModel.class);
    }

    @Override
    public void initData() {
        //给RecyclerView添加Adapter，请使用自定义的Adapter继承BindingRecyclerViewAdapter，重写onBindBinding方法，里面有你要的Item对应的binding对象。
        // Adapter属于View层的东西, 不建议定义到ViewModel中绑定，以免内存泄漏
        binding.setAdapter(new BindingRecyclerViewAdapter());
        //请求网络数据
        viewModel.getArticle();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initViewObservable() {
        //监听下拉刷新
        viewModel.uc.finishRefreshing.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                //结束刷新
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
