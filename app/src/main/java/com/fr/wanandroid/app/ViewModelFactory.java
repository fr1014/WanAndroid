package com.fr.wanandroid.app;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.fr.wanandroid.main.model.ModelRepository;
import com.fr.wanandroid.main.ui.home.vm.HomeViewModel;
import com.fr.wanandroid.main.ui.knowledge.vm.KnowledgeViewModel;
import com.fr.wanandroid.main.ui.main.vm.MainViewModel;

import java.lang.reflect.InvocationTargetException;

/**
 * ViewModel工厂类
 * <p>
 * 创建时间：2019/10/11
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static volatile ViewModelFactory INSTANCE = null;
    private Application mApplication;
    private ModelRepository mRepository;

    private ViewModelFactory(Application application, ModelRepository modelRepository) {
        this.mApplication = application;
        this.mRepository = modelRepository;
    }

    public static ViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(application, Injection.provideModelRepository());
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (AndroidViewModel.class.isAssignableFrom(modelClass)){
            try {
                return modelClass.getConstructor(Application.class, ModelRepository.class).newInstance(mApplication,mRepository);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        throw new IllegalArgumentException("UnKnow ViewModel class:" + modelClass.getName());
    }


//    @SuppressWarnings("unchecked")
//    @NonNull
//    @Override
//    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//        if (modelClass.isAssignableFrom(MainViewModel.class)) {
//            return (T) new MainViewModel(mApplication, mRepository);
//        } else if (modelClass.isAssignableFrom(HomeViewModel.class)) {
//            return (T) new HomeViewModel(mApplication, mRepository);
//        } else if (modelClass.isAssignableFrom(KnowledgeViewModel.class)) {
//            return (T) new KnowledgeViewModel(mApplication, mRepository);
//        }
//        throw new IllegalArgumentException("UnKnow ViewModel class:" + modelClass.getName());
//    }
}
