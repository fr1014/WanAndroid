package com.fr.wanandroid.model.http;

import com.fr.wanandroid.http.WanResponse;
import com.fr.wanandroid.http.api.WanApiService;
import com.fr.wanandroid.entity.ArticleListBean;
import com.fr.wanandroid.entity.ChapterBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * 创建时间：2019/10/10
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class HttpDataSourceImpl implements HttpDataSource {
    private WanApiService apiService;
    private volatile static HttpDataSourceImpl INSTANCE = null;

    private HttpDataSourceImpl(WanApiService apiService) {
        this.apiService = apiService;
    }

    public static HttpDataSourceImpl getInstance(WanApiService apiService) {
        if (INSTANCE == null) {
            synchronized (HttpDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpDataSourceImpl(apiService);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Observable<WanResponse<ArticleListBean>> getHomeArticle(int page) {
        return apiService.getHomeArticles(page);
    }

    @Override
    public Observable<WanResponse<List<ChapterBean>>> getWxChapter() {
        return apiService.getWxChapters();
    }

    @Override
    public Observable<WanResponse<ArticleListBean>> getWxArticles(int id, int page) {
        return apiService.getWxArticles(id,page);
    }

    @Override
    public Observable<WanResponse<ArticleListBean>> getWxArticleForAuthor(int id, int page, String message) {
        return apiService.getWxArticleForAuthor(id,page,message);
    }

    @Override
    public Observable<WanResponse<List<ChapterBean>>> getKnowledgeChapter() {
        return apiService.getKnowledgeChapter();
    }

    @Override
    public Observable<WanResponse<ArticleListBean>> getKnowledgeArticles(int page, int id) {
        return apiService.getKnowledgeArticles(page,id);
    }

    @Override
    public Observable<WanResponse<List<ChapterBean>>> getProjectChapters() {
        return apiService.getProjectChapters();
    }

    @Override
    public Observable<WanResponse<ArticleListBean>> getProjectArticles(int page, int id) {
        return apiService.getProjectArticles(page,id);
    }

}
