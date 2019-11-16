package com.fr.wanandroid.ui.main.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.ui.NavigationUI;

import com.fr.mvvm.base.BaseActivity;
import com.fr.wanandroid.BR;
import com.fr.wanandroid.R;
import com.fr.wanandroid.app.ViewModelFactory;
import com.fr.wanandroid.databinding.ActivityMainBinding;
import com.fr.wanandroid.ui.main.vm.MainViewModel;
import com.fr.wanandroid.utils.NavigationExtensions;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private LiveData<NavController> currentNavController = null;

    @Override
    public void initParam() {
        super.initParam();
    }

    @Override
    protected int initVariableId() {
        return BR.viewModel;
    }

    public MainViewModel initViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(MainViewModel.class);
    }

    @Override
    protected int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            setupBottomNavigationBar();
        }
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //现在BottomNavigationBar已恢复其实例状态
        //及其selectedItemId，我们可以继续设置
        //带有导航的BottomNavigationBar
        setupBottomNavigationBar();
    }

    /**
     * 在首次创建以及恢复状态时调用
     */
    private void setupBottomNavigationBar() {
        List<Integer> navGraphIds = Arrays.asList(R.navigation.home_nag_graph,
                R.navigation.knowledge_nav_graph, R.navigation.wxarticle_nav_graph,
                R.navigation.project_nav_graph);
      LiveData<NavController> controller = NavigationExtensions.setupWithNavController(
                binding.navigationView,
                navGraphIds,
                getSupportFragmentManager(),
                R.id.fragment_container_view,
                getIntent()
        );
//        LiveData<NavController> controller = NavigationExtensionsKt.setupWithNavController(
//                binding.navigationView,
//                navGraphIds,
//                getSupportFragmentManager(),
//                R.id.fragment_container_view,
//                getIntent()
//        );
        controller.observe(this, new Observer<NavController>() {
            @Override
            public void onChanged(NavController navController) {
                NavigationUI.setupActionBarWithNavController(MainActivity.this, navController);
            }
        });

        currentNavController = controller;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController controller = currentNavController.getValue();
        if (controller != null) {
            return controller.navigateUp();
        }
        return false;
    }

    @Override
    public void initViewObservable() {

    }

}
