package com.fr.mvvm.base;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fr.mvvm.utils.MaterialDialogUtils;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import com.fr.mvvm.base.BaseViewModel.ParameterFiled;

/**
 * 创建时间：2019/10/9
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public abstract class BaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends RxFragment implements IBaseView {
    protected V binding;
    protected VM viewModel;
    private int viewModelId;
    private MaterialDialog dialog;

    private static final String TAG = "BaseFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "------onCreate: " + viewModel);
        initParam();
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "------onCreateView: "+viewModel);
        binding = DataBindingUtil.inflate(inflater, initContentView(inflater, container, savedInstanceState), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "------onViewCreated: " + viewModel);
        //私有的初始化DataBinding和ViewModel
        initViewDataBinding();
        //私有的ViewModel与View的锲约事件回调逻辑
        registerUIChangeLiveDataCallBack();
        //页面数据初始化方法
        initData();
        //页面事件监听，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
    }

    private void initViewDataBinding() {
        viewModelId = initVariableId();
        viewModel = initViewModel();
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) createViewModel(this, modelClass);
        }
        binding.setVariable(viewModelId, viewModel);

        binding.setLifecycleOwner(this);

        //让viewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);

        //注入RxLifecycle生命周期
        viewModel.injectLifecycleProvider(this);
    }

    //注册ViewModel与View的锲约事件回调逻辑
    @SuppressWarnings("unchecked")
    private void registerUIChangeLiveDataCallBack() {
        //加载对话框提示
        viewModel.getUC().getShowDialogEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String title) {
                showDialog(title);
            }
        });
        //加载对话框消失
        viewModel.getUC().getDismissDialogEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void v) {
                dismissDialog();
            }
        });

        //跳入ContainerActivity
        viewModel.getUC().getStartContainerActivityEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(Map<String, Object> params) {
                String canonicalName = (String) params.get(ParameterFiled.CANONICAL_NAME);
                Bundle bundle = (Bundle) params.get(ParameterFiled.BUNDLE);
                startContainerActivity(canonicalName, bundle);
            }
        });
    }

    public void showDialog(String title) {
        if (dialog != null) {
            dialog = dialog.getBuilder().title(title).build();
        } else {
            dialog = MaterialDialogUtils.showDialog(getActivity(), title, true).build();
        }
        dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void startContainerActivity(String canonicalName, Bundle bundle) {
        Intent intent = new Intent(getContext(), ContainerActivity.class);
        intent.putExtra(ContainerActivity.FRAGMENT, canonicalName);
        if (bundle != null) {
            intent.putExtra(ContainerActivity.BUNDLE, bundle);
        }
        startActivity(intent);
    }

    /**
     * 创建ViewModel
     */
    @SuppressWarnings("unchecked")
    private <T extends ViewModel> T createViewModel(Fragment fragment, Class clzz) {
        return (T) ViewModelProviders.of(this).get(clzz);
    }

    @Override
    public void initParam() {

    }

    /**
     * 初始化根布局
     *
     * @return layout的id
     */
    protected abstract int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    protected abstract int initVariableId();

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public VM initViewModel() {
        return null;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initViewObservable() {

    }

    public boolean isBackPressed() {
        return false;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG, "------onViewStateRestored: " + viewModel);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "------onStart: " + viewModel);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "------onResume: " + viewModel);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "------onPause: " + viewModel);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "------onStop: " + viewModel);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "------onDetach: " + viewModel);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "------onDestroy: " + viewModel);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "------onDestroyView: " + viewModel);
        if (binding != null) {
            binding.unbind();
        }
    }
}
