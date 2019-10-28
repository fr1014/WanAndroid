package com.fr.mvvm.base;

import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseActivity<V extends ViewDataBinding,VM extends BaseViewModel> extends RxAppCompatActivity implements IBaseView{

    protected V binding;
    protected VM viewModel;
    private int viewModelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //页面接受的参数
        initParam();
        //私有的初始化DataBinding和ViewModel方法
        initViewDataBinding(savedInstanceState);
        initView(savedInstanceState);
        //页面数据初始化
        initData();
        //页面时间监听的方法，一般用于ViewModel层转到View层的时间注册
        initViewObservable();
    }

    public void initView(Bundle savedInstanceState) {
    }

    /**
     * 注入绑定
     */
    private void initViewDataBinding(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this,initContentView(savedInstanceState));
        viewModelId = initVariableId();
        viewModel = initViewModel();
        if (viewModel == null){
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType){
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            }else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = createViewModel(this,modelClass);
        }
        //关联ViewModel
        binding.setVariable(viewModelId,viewModel);

        //将当前活动指定为生命周期所有者
        binding.setLifecycleOwner(this);

        getLifecycle().addObserver(viewModel);

        //注入RxLifecycle生命周期
        viewModel.injectLifecycleProvider(this);
    }

    /**
     *创建ViewModel
     */
    @SuppressWarnings("unchecked")
    private <T extends ViewModel> T createViewModel(FragmentActivity activity, Class clzz) {
        return (T) ViewModelProviders.of(this).get(clzz);
    }

    /**
     * 初始化ViewModel
     * @return 继承BaseViewModel的ViewModel
     */
    public VM initViewModel(){
        return null;
    }

    /**
     * 初始化ViewModel的id
     * @return BR的id
     */
    protected abstract int initVariableId();

    /**
     * 初始化根布局
     * @return 布局layout的id
     */
    protected abstract int initContentView(Bundle savedInstanceState);

    @Override
    public void initParam() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initViewObservable() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binding != null){
            binding.unbind();
        }
    }
}
