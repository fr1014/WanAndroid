package com.fr.wanandroid.main.model.http;

import com.fr.wanandroid.http.WanResponse;
import com.fr.wanandroid.main.entity.ArticleBean;
import com.fr.wanandroid.main.entity.ArticleListBean;
import com.fr.wanandroid.main.entity.ChapterBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * 创建时间：2019/10/10
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public interface HttpDataSource {
    //首页文章获取
    Observable<WanResponse<ArticleListBean>> articleGet(int page);
    //公众号
    Observable<WanResponse<List<ChapterBean>>> chapterGet();
    //知识体系
    Observable<WanResponse<List<ChapterBean>>> knowledgeGet();
    //知识体系下的文章获取
    Observable<WanResponse<ArticleListBean>> getKnowledgeArticleList(int page,int id);
}
