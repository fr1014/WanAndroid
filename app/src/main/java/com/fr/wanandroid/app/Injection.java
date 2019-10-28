package com.fr.wanandroid.app;

import com.fr.mvvm.http.RetrofitManager;
import com.fr.wanandroid.http.api.WanApiService;
import com.fr.wanandroid.main.model.ModelRepository;
import com.fr.wanandroid.main.model.http.HttpDataSource;
import com.fr.wanandroid.main.model.http.HttpDataSourceImpl;
import com.fr.wanandroid.main.model.local.LocalDataSource;
import com.fr.wanandroid.main.model.local.LocalDataSourceImpl;

/**
 * 注入全局的数据仓库，可以考虑使用Dagger2
 *
 * 创建时间：2019/10/10
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class Injection {

    public static ModelRepository provideModelRepository(){
        //网络API服务
        WanApiService apiService = RetrofitManager
                .getInstance()
                .init(MyApplication.getApplication(),"https://wanandroid.com/")
                .create(WanApiService.class);
        //网络数据来源
        HttpDataSource httpDataSource = HttpDataSourceImpl.getInstance(apiService);
        //本地数据来源
        LocalDataSource localDataSource = LocalDataSourceImpl.getInstance();
        //两条分支组成一个数据仓库
        return ModelRepository.getInstance(httpDataSource,localDataSource);
    }
}
