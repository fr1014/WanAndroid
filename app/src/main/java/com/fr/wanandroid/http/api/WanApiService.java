package com.fr.wanandroid.http.api;

import com.fr.wanandroid.entity.ArticleListBean;
import com.fr.wanandroid.entity.ChapterBean;
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
     * 参数：无
     */
    @GET("wxarticle/chapters/json")
    Observable<WanResponse<List<ChapterBean>>> getWxChapters();

    /**
     * 查看某个公众号历史数据
     * 方法：GET
     * 参数：
     * 公众号 ID：拼接在 url 中，eg:405
     * 公众号页码：拼接在url 中，eg:1
     */
    @GET("wxarticle/list/{id}/{page}/json")
    Observable<WanResponse<ArticleListBean>> getWxArticles(@Path("id") int id, @Path("page") int page);

    /**
     * 在某个公众号中搜索历史文章
     * 方法：GET
     * 参数：
     * k : 字符串，eg:Java
     * 公众号 ID：拼接在 url 中，eg:405
     * 公众号页码：拼接在url 中，eg:1
     */
    @GET("wxarticle/list/{id}/{page}/json")
    Observable<WanResponse<ArticleListBean>> getWxArticleForAuthor(@Path("id") int id, @Path("page") int page, @Query("k") String message);

    /**
     * 首页文章列表
     * 方法：GET
     * 参数：页码，拼接在连接中，从0开始
     */
    @GET("article/list/{page}/json")
    Observable<WanResponse<ArticleListBean>> getHomeArticles(@Path("page") int page);

    /**
     * 知识体系数据（分类）
     * 方法：GET
     * 参数：无
     */
    @GET("tree/json")
    Observable<WanResponse<List<ChapterBean>>> getKnowledgeChapter();

    /**
     * 知识体系下的文章
     * 方法：GET
     * 参数：
     * cid 分类的id，上述二级目录的id
     * 页码：拼接在链接上，从0开始。
     */
    @GET("article/list/{page}/json")
    Observable<WanResponse<ArticleListBean>> getKnowledgeArticles(@Path("page") int page, @Query("cid") int id);

    /**
     * 项目分类
     * 方法：GET
     * 参数：无
     */
    @GET("project/tree/json")
    Observable<WanResponse<ChapterBean>> getProjectChapters();

    /**
     * 项目列表数据
     * 方法：GET
     * 参数：
     * cid 分类的id，上面项目分类接口
     * 页码：拼接在链接中，从1开始。
     */
    @GET("project/list/{page}/json")
    Observable<WanResponse<ArticleListBean>> getProjectArticles(@Path("page") int page, @Query("cid") int id);
}
