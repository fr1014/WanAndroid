package com.fr.wanandroid.model.http;

import com.fr.wanandroid.http.WanResponse;
import com.fr.wanandroid.entity.ArticleListBean;
import com.fr.wanandroid.entity.ChapterBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * 创建时间：2019/10/10
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public interface HttpDataSource {
    //首页文章获取
    Observable<WanResponse<ArticleListBean>> getHomeArticle(int page);
    //公众号
    Observable<WanResponse<List<ChapterBean>>> getWxChapter();
    //查看某个公众号历史数据
    Observable<WanResponse<ArticleListBean>> getWxArticles(int id,int page);
    //在某个公众号中搜索历史文章
    Observable<WanResponse<ArticleListBean>> getWxArticleForAuthor(int id,int page,String message);
    //知识体系
    Observable<WanResponse<List<ChapterBean>>> getKnowledgeChapter();
    //知识体系下的文章获取
    Observable<WanResponse<ArticleListBean>> getKnowledgeArticles(int page,int id);
}
