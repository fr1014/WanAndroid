package com.fr.wanandroid.main.model.http;

import com.fr.wanandroid.http.WanResponse;
import com.fr.wanandroid.http.api.WanApiService;
import com.fr.wanandroid.main.entity.ArticleListBean;
import com.fr.wanandroid.main.entity.ChapterBean;

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
    public Observable<WanResponse<ArticleListBean>> articleGet(int page) {
        return apiService.getHomeArticle(page);
    }

    @Override
    public Observable<WanResponse<List<ChapterBean>>> chapterGet() {
        return apiService.getChapters();
    }

    @Override
    public Observable<WanResponse<List<ChapterBean>>> knowledgeGet() {
        return apiService.getKnowledgeList();
    }

    @Override
    public Observable<WanResponse<ArticleListBean>> getKnowledgeArticleList(int page, int id) {
        return apiService.getKnowledgeArticleList(page,id);
    }
}
