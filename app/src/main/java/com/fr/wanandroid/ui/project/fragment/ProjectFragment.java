package com.fr.wanandroid.ui.project.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProviders;

import com.fr.mvvm.base.BaseFragment;
import com.fr.wanandroid.BR;
import com.fr.wanandroid.R;
import com.fr.wanandroid.app.ViewModelFactory;
import com.fr.wanandroid.databinding.FragmentProjectBinding;
import com.fr.wanandroid.ui.project.vm.ProjectViewModel;

public class ProjectFragment extends BaseFragment<FragmentProjectBinding, ProjectViewModel> {

    @Override
    protected int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_project;
    }

    @Override
    protected int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public ProjectViewModel initViewModel() {
        if (getActivity() != null) {
            ViewModelFactory factory = ViewModelFactory.getInstance(getActivity().getApplication());
            return ViewModelProviders.of(getActivity(), factory).get(ProjectViewModel.class);
        }
        return null;
    }
}
