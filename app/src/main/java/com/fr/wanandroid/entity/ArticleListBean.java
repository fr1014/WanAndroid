package com.fr.wanandroid.entity;

import com.fr.mvvm.http.base.BaseBean;

import java.util.List;

/**
 * 创建时间：2019/10/9
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class ArticleListBean extends BaseBean {

        /**
         * curPage : 1
         * datas : [{"apkLink":"","audit":1,"author":"","chapterId":423,"chapterName":"Architecture","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":true,"id":9533,"link":"https://juejin.im/post/5d761e2c6fb9a06b1e7f62a3","niceDate":"1天前","niceShareDate":"1天前","origin":"","prefix":"","projectLink":"","publishTime":1570544215000,"selfVisible":0,"shareDate":1570515053000,"shareUser":"Hankkin","superChapterId":423,"superChapterName":"Jetpack","tags":[],"title":"7. JetpackNote---基于Jetpack的学习笔记APP","type":0,"userId":7490,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":99,"chapterName":"具体案例","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":true,"id":9545,"link":"https://juejin.im/post/5d9bf8dce51d45781d5e4bb9","niceDate":"1天前","niceShareDate":"1天前","origin":"","prefix":"","projectLink":"","publishTime":1570544197000,"selfVisible":0,"shareDate":1570543942000,"shareUser":"鸿洋","superChapterId":126,"superChapterName":"自定义控件","tags":[],"title":"花式实现时间轴，样式由你来定！","type":0,"userId":2,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":229,"chapterName":"AOP","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":true,"id":9546,"link":"https://www.jianshu.com/p/fb8d832e0dfd?utm_source=desktop&utm_medium=timeline","niceDate":"1天前","niceShareDate":"1天前","origin":"","prefix":"","projectLink":"","publishTime":1570544189000,"selfVisible":0,"shareDate":1570543989000,"shareUser":"鸿洋","superChapterId":227,"superChapterName":"注解 & 反射 & AOP","tags":[],"title":"AspectJ AOP教程：实现Android基于注解无侵入埋点、性能监控","type":0,"userId":2,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":444,"chapterName":"androidx","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":true,"id":9547,"link":"https://www.jianshu.com/p/25aa5cacbfb9?utm_source=desktop&utm_medium=timeline","niceDate":"1天前","niceShareDate":"1天前","origin":"","prefix":"","projectLink":"","publishTime":1570544158000,"selfVisible":0,"shareDate":1570544019000,"shareUser":"鸿洋","superChapterId":192,"superChapterName":"5.+高新技术","tags":[],"title":"ViewPager2的使用方式","type":0,"userId":2,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"aijason","chapterId":402,"chapterName":"跨平台应用","collect":false,"courseId":13,"desc":"RN_WanAndroid 采用 React Native 框架编写，基于0.61.1^版本。结合 Axios + React-Navigation + Redux 等开源框架设计的项目，项目代码结构清晰并且有详细注释。","envelopePic":"https://www.wanandroid.com/blogimgs/edb07a7c-3956-4b56-9a97-ac575d8fe22b.png","fresh":false,"id":9517,"link":"https://www.wanandroid.com/blog/show/2681","niceDate":"2天前","niceShareDate":"2天前","origin":"","prefix":"","projectLink":"https://github.com/aijason/RN_WanAndroid","publishTime":1570452331000,"selfVisible":0,"shareDate":1570452331000,"shareUser":"","superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=402"}],"title":"React Native版玩Android项目","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":182,"chapterName":"JNI编程","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9498,"link":"https://juejin.im/post/5d984028518825095879e45d#heading-10","niceDate":"2天前","niceShareDate":"2019-10-05","origin":"","prefix":"","projectLink":"","publishTime":1570452047000,"selfVisible":0,"shareDate":1570261313000,"shareUser":"鸿洋","superChapterId":149,"superChapterName":"JNI","tags":[],"title":"[-NDK 导引篇 -] 在NDK开发之前你应知道的东西","type":0,"userId":2,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":182,"chapterName":"JNI编程","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9515,"link":"https://juejin.im/post/5d9ae710e51d45782c23faaf","niceDate":"2天前","niceShareDate":"2天前","origin":"","prefix":"","projectLink":"","publishTime":1570452040000,"selfVisible":0,"shareDate":1570433906000,"shareUser":"叫我小明同学","superChapterId":149,"superChapterName":"JNI","tags":[],"title":"NDK 开发之 OpenCV 使用实践","type":0,"userId":26222,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":238,"chapterName":"其他动画","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9508,"link":"https://juejin.im/post/5d95b4e86fb9a04dff4de98c","niceDate":"2天前","niceShareDate":"2019-10-06","origin":"","prefix":"","projectLink":"","publishTime":1570452027000,"selfVisible":0,"shareDate":1570351201000,"shareUser":"鸿洋","superChapterId":92,"superChapterName":"动画效果","tags":[],"title":"谷歌VR展示360度全景图","type":0,"userId":2,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":78,"chapterName":"性能优化","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9514,"link":"https://juejin.im/post/5d95f4a4f265da5b8f10714b","niceDate":"2天前","niceShareDate":"2天前","origin":"","prefix":"","projectLink":"","publishTime":1570452014000,"selfVisible":0,"shareDate":1570430230000,"shareUser":"蓝师傅","superChapterId":74,"superChapterName":"热门专题","tags":[],"title":"面试官：今日头条启动很快，你觉得可能是做了哪些优化？","type":0,"userId":1871,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"xuexiangjys","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"一个简洁而又优雅的Android原生UI框架，解放你的双手！还不赶紧点击使用说明文档，体验一下吧！","envelopePic":"https://wanandroid.com/blogimgs/df78f4d6-94c6-4ed8-8b96-999f57a84262.png","fresh":false,"id":9494,"link":"https://www.wanandroid.com/blog/show/2680","niceDate":"2019-10-05","niceShareDate":"2019-10-05","origin":"","prefix":"","projectLink":"https://github.com/xuexiangjys/XUI","publishTime":1570252145000,"selfVisible":0,"shareDate":1570252145000,"shareUser":"","superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"XUI 一个简洁而优雅的Android原生UI框架，解放你的双手！","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"soulqw","chapterId":358,"chapterName":"项目基础功能","collect":false,"courseId":13,"desc":"1.一行代码完成某个View,或者多个View的高亮展示\r\n2.高亮区域支持自定义大小、操作灵活\r\n3.顺应变化,基于Android X","envelopePic":"https://www.wanandroid.com/blogimgs/555764b3-2bc9-481b-b08d-9039e275c4e7.png","fresh":false,"id":9452,"link":"https://www.wanandroid.com/blog/show/2679","niceDate":"2019-09-29","niceShareDate":"2019-09-29","origin":"","prefix":"","projectLink":"https://github.com/soulqw/Curtain","publishTime":1569765101000,"selfVisible":0,"shareDate":1569765101000,"shareUser":"","superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=358"}],"title":"Curtain-一个更简单更方便的View 高亮库","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":296,"chapterName":"阅读","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9438,"link":"https://mp.weixin.qq.com/s?__biz=MzI3OTcyNjQ5MQ==&mid=2247483781&idx=1&sn=c423dfc1737b54de893133f7df5afcdc&chksm=eb421171dc3598678e8a5da7d629c4ecc172d7c06602e30be41385dadc6b5f907bd773c2e663&token=579360501&lang=zh_CN#rd","niceDate":"2019-09-29","niceShareDate":"2019-09-29","origin":"","prefix":"","projectLink":"","publishTime":1569764960000,"selfVisible":0,"shareDate":1569717139000,"shareUser":"ZYLAB","superChapterId":202,"superChapterName":"延伸技术","tags":[],"title":"软件开发过程中的思维方式 -- 如何分析问题","type":0,"userId":10577,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":78,"chapterName":"性能优化","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9439,"link":"https://blog.csdn.net/csdn_aiyang/article/details/74989318","niceDate":"2019-09-29","niceShareDate":"2019-09-29","origin":"","prefix":"","projectLink":"","publishTime":1569764913000,"selfVisible":0,"shareDate":1569717399000,"shareUser":"zhanlv","superChapterId":74,"superChapterName":"热门专题","tags":[],"title":"Android 性能优化之内存检测、卡顿优化、耗电优化、APK瘦身","type":0,"userId":22666,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":424,"chapterName":"协程","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9447,"link":"https://mp.weixin.qq.com/s/0qMWYrHDSRATw3Qj8vrTGQ","niceDate":"2019-09-29","niceShareDate":"2019-09-29","origin":"","prefix":"","projectLink":"","publishTime":1569764879000,"selfVisible":0,"shareDate":1569737781000,"shareUser":"MNY459","superChapterId":232,"superChapterName":"Kotlin","tags":[],"title":"到底什么是「非阻塞式」挂起？协程真的比线程更轻量级吗？","type":0,"userId":2199,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":182,"chapterName":"JNI编程","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9449,"link":"https://juejin.im/post/5d8a44096fb9a04ded310586","niceDate":"2019-09-29","niceShareDate":"2019-09-29","origin":"","prefix":"","projectLink":"","publishTime":1569764860000,"selfVisible":0,"shareDate":1569746265000,"shareUser":"叫我小明同学","superChapterId":149,"superChapterName":"JNI","tags":[],"title":"NDK 开发之使用 OpenCV 实现人脸识别","type":0,"userId":26222,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"玉刚说","chapterId":410,"chapterName":"玉刚说","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9419,"link":"https://mp.weixin.qq.com/s/wazr-s0jl83mbUGeAviTbA","niceDate":"2019-09-28","niceShareDate":"2019-09-28","origin":"","prefix":"","projectLink":"","publishTime":1569600000000,"selfVisible":0,"shareDate":1569677794000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/410/1"}],"title":"第一站仿小红书图片裁剪控件，深度解析大厂炫酷控件","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"code小生","chapterId":414,"chapterName":"code小生","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9428,"link":"https://mp.weixin.qq.com/s/u7s2n9ksyWSniurVqv5Uyw","niceDate":"2019-09-28","niceShareDate":"2019-09-28","origin":"","prefix":"","projectLink":"","publishTime":1569600000000,"selfVisible":0,"shareDate":1569679169000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/414/1"}],"title":"为什么还要在Activity中写业务代码？","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"程序亦非猿","chapterId":428,"chapterName":"程序亦非猿","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9437,"link":"https://mp.weixin.qq.com/s/Q7GfRwx66OF3DmHdhx7PJw","niceDate":"2019-09-28","niceShareDate":"2019-09-28","origin":"","prefix":"","projectLink":"","publishTime":1569600000000,"selfVisible":0,"shareDate":1569679988000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/428/1"}],"title":"接口幂等性这么重要，它是什么？怎么实现？","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":168,"chapterName":"Drawable","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9359,"link":"https://juejin.im/post/5d822fbf518825171501549f","niceDate":"2019-09-27","niceShareDate":"2019-09-26","origin":"","prefix":"","projectLink":"","publishTime":1569514868000,"selfVisible":0,"shareDate":1569479062000,"shareUser":"徐公码字","superChapterId":168,"superChapterName":"基础知识","tags":[],"title":"Android 点九图机制讲解及在聊天气泡中的应用","type":0,"userId":5339,"visible":1,"zan":0},{"apkLink":"","audit":1,"author":"","chapterId":494,"chapterName":"广场","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":9362,"link":"https://juejin.im/post/5d8c593ee51d45783544b9ac","niceDate":"2019-09-27","niceShareDate":"2019-09-26","origin":"","prefix":"","projectLink":"","publishTime":1569514835000,"selfVisible":0,"shareDate":1569484206000,"shareUser":"祝起光","superChapterId":494,"superChapterName":"广场Tab","tags":[],"title":"HTTPS工作原理以及Android中如何防止抓包","type":0,"userId":27937,"visible":1,"zan":0}]
         * offset : 0
         * over : false
         * pageCount : 362
         * size : 20
         * total : 7225
         */
        private int curPage;
        private int offset;
        private boolean over;
        private int pageCount;
        private int size;
        private int total;
        private List<ArticleBean> datas;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public boolean isOver() {
            return over;
        }

        public void setOver(boolean over) {
            this.over = over;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ArticleBean> getDatas() {
            return datas;
        }

        public void setDatas(List<ArticleBean> datas) {
            this.datas = datas;
        }

}
