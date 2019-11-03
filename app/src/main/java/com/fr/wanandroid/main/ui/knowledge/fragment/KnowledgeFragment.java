package com.fr.wanandroid.main.ui.knowledge.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.fr.mvvm.base.BaseFragment;
import com.fr.wanandroid.BR;
import com.fr.wanandroid.R;
import com.fr.wanandroid.app.ViewModelFactory;
import com.fr.wanandroid.databinding.FragmentKnowledgeBinding;
import com.fr.wanandroid.main.entity.ChapterBean;
import com.fr.wanandroid.main.ui.knowledge.vm.KnowledgeViewModel;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

public class KnowledgeFragment extends BaseFragment<FragmentKnowledgeBinding, KnowledgeViewModel> {

    private ChapterBean entity;

    private static final String TAG = "KnowledgeFragment";

    @SuppressWarnings("UseBulkOperation")
    @Override
    public void initParam() {

    }

    @Override
    protected int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected int initVariableId() {
        return BR.viewModel;
    }

    public KnowledgeViewModel initViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(getActivity(), factory).get(KnowledgeViewModel.class);
    }

    @Override
    public void initData() {
        binding.setAdapter(new BindingRecyclerViewAdapter());
        viewModel.getKnowledge();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initViewObservable() {
        viewModel.uc.finishRefreshing.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


}
