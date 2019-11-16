package com.fr.wanandroid.ui.knowledge.fragment;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.fr.mvvm.base.BaseFragment;
import com.fr.wanandroid.BR;
import com.fr.wanandroid.R;
import com.fr.wanandroid.app.ViewModelFactory;
import com.fr.wanandroid.databinding.FragmentKnowledgeBinding;
import com.fr.wanandroid.ui.knowledge.vm.KnowledgeViewModel;

public class KnowledgeFragment extends BaseFragment<FragmentKnowledgeBinding, KnowledgeViewModel> {

    @Override
    protected int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected int initVariableId() {
        return BR.viewModel;
    }

    public KnowledgeViewModel initViewModel() {
        if (getActivity()!=null){
            ViewModelFactory factory = ViewModelFactory.getInstance(getActivity().getApplication());
            return ViewModelProviders.of(getActivity(), factory).get(KnowledgeViewModel.class);
        }
        return null;
    }

    @Override
    public void initData() {
        viewModel.getKnowledge();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initViewObservable() {
    }


}
