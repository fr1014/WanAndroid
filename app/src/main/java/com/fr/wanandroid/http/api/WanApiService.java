package com.fr.wanandroid.http.api;

import com.fr.wanandroid.main.entity.ArticleListBean;
import com.fr.wanandroid.main.entity.ChapterBean;
import com.fr.wanandroid.http.WanResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 创建时间：2019/10/6
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public interface WanApiService {
    /**
     * 获取公众号列表
     * 方法：GET
     */
    @GET("wxarticle/chapters/json")
    Observable<WanResponse<List<ChapterBean>>> getChapters();

    /**
     *首页文章列表
     *方法：GET
     *参数：页码，拼接在连接中，从0开始
     */
    @GET("article/list/{page}/json")
    Observable<WanResponse<ArticleListBean>> getHomeArticle(@Path("page") int page);

    /**
     * 体系数据
     */
    @GET("tree/json")
    Observable<WanResponse<List<ChapterBean>>> getKnowledgeList();

    /**
     * 知识体系下的文章
     * 方法：GET
     * 参数：
     * cid 分类的id，上述二级目录的id
     * 页码：拼接在链接上，从0开始。
     */
    @GET("article/list/{page}/json")
    Observable<WanResponse<ArticleListBean>> getKnowledgeArticleList(@Path("page") int page,@Query("cid") int id);

}
