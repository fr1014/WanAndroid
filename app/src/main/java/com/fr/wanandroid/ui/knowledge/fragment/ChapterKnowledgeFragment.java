package com.fr.wanandroid.ui.knowledge.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.fr.mvvm.base.BaseFragment;
import com.fr.wanandroid.BR;
import com.fr.wanandroid.R;
import com.fr.wanandroid.app.ViewModelFactory;
import com.fr.wanandroid.databinding.RvKnowledgeChapterBinding;
import com.fr.wanandroid.ui.knowledge.vm.KnowledgeViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChapterKnowledgeFragment extends BaseFragment<RvKnowledgeChapterBinding, KnowledgeViewModel> {

    @Override
    protected int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.rv_knowledge_chapter;
    }

    @Override
    protected int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public KnowledgeViewModel initViewModel() {
        if (getActivity()!=null){
            ViewModelFactory factory = ViewModelFactory.getInstance(getActivity().getApplication());
            return ViewModelProviders.of(getActivity(),factory).get(KnowledgeViewModel.class);
        }
        return null;
    }

    @Override
    public void initData() {
    }
}
