package com.fr.wanandroid.model;

import com.fr.mvvm.base.BaseModel;
import com.fr.wanandroid.http.WanResponse;
import com.fr.wanandroid.entity.ArticleListBean;
import com.fr.wanandroid.entity.ChapterBean;
import com.fr.wanandroid.model.http.HttpDataSource;
import com.fr.wanandroid.model.local.LocalDataSource;

import java.util.List;

import io.reactivex.Observable;

/**
 * MVVM的Model层，统一模块的数据仓库，包含网络数据和本地数据（一个应用可以有多个Repository）
 *
 * 创建时间：2019/10/10
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class ModelRepository extends BaseModel implements HttpDataSource, LocalDataSource {
    private volatile static ModelRepository INSTANCE = null;
    private final HttpDataSource mHttpDataSource;
    private final LocalDataSource mLocalDataSource;

    private ModelRepository(HttpDataSource mHttpDataSource, LocalDataSource mLocalDataSource) {
        this.mHttpDataSource = mHttpDataSource;
        this.mLocalDataSource = mLocalDataSource;
    }

    public static ModelRepository getInstance(HttpDataSource httpDataSource,LocalDataSource localDataSource){
        if (INSTANCE == null){
            synchronized (ModelRepository.class){
                if (INSTANCE == null){
                    INSTANCE = new ModelRepository(httpDataSource,localDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Observable<WanResponse<ArticleListBean>> getHomeArticle(int page) {
        return mHttpDataSource.getHomeArticle(page);
    }

    @Override
    public Observable<WanResponse<List<ChapterBean>>> getWxChapter() {
        return mHttpDataSource.getWxChapter();
    }

    @Override
    public Observable<WanResponse<ArticleListBean>> getWxArticles(int id, int page) {
        return mHttpDataSource.getWxArticles(id,page);
    }

    @Override
    public Observable<WanResponse<ArticleListBean>> getWxArticleForAuthor(int id, int page, String message) {
        return mHttpDataSource.getWxArticleForAuthor(id,page,message);
    }

    @Override
    public Observable<WanResponse<List<ChapterBean>>> getKnowledgeChapter() {
        return mHttpDataSource.getKnowledgeChapter();
    }

    @Override
    public Observable<WanResponse<ArticleListBean>> getKnowledgeArticles(int page, int id) {
        return mHttpDataSource.getKnowledgeArticles(page,id);
    }
}
