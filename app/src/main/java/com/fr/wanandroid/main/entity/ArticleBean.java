package com.fr.wanandroid.main.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.fr.mvvm.http.base.BaseBean;

import java.util.List;

/**
 * 创建时间：2019/10/9
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class ArticleBean implements Parcelable {

    /**
     * apkLink :
     * audit : 1
     * author :
     * chapterId : 423
     * chapterName : Architecture
     * collect : false
     * courseId : 13
     * desc :
     * envelopePic :
     * fresh : true
     * id : 9533
     * link : https://juejin.im/post/5d761e2c6fb9a06b1e7f62a3
     * niceDate : 1天前
     * niceShareDate : 1天前
     * origin :
     * prefix :
     * projectLink :
     * publishTime : 1570544215000
     * selfVisible : 0
     * shareDate : 1570515053000
     * shareUser : Hankkin
     * superChapterId : 423
     * superChapterName : Jetpack
     * tags : []
     * title : 7. JetpackNote---基于Jetpack的学习笔记APP
     * type : 0
     * userId : 7490
     * visible : 1
     * zan : 0
     */
    private String apkLink;
    private int audit;
    private String author;
    private int chapterId;
    private String chapterName;
    private boolean collect;
    private int courseId;
    private String desc;
    private String envelopePic;
    private boolean fresh;
    private int id;
    private String link;
    private String niceDate;
    private String niceShareDate;
    private String origin;
    private String prefix;
    private String projectLink;
    private long publishTime;
    private int selfVisible;
    private long shareDate;
    private String shareUser;
    private int superChapterId;
    private String superChapterName;
    private String title;
    private int type;
    private int userId;
    private int visible;
    private int zan;
    private List<TagsBean> tags;

    public String getApkLink() {
        return apkLink;
    }

    public void setApkLink(String apkLink) {
        this.apkLink = apkLink;
    }

    public int getAudit() {
        return audit;
    }

    public void setAudit(int audit) {
        this.audit = audit;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEnvelopePic() {
        return envelopePic;
    }

    public void setEnvelopePic(String envelopePic) {
        this.envelopePic = envelopePic;
    }

    public boolean isFresh() {
        return fresh;
    }

    public void setFresh(boolean fresh) {
        this.fresh = fresh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNiceDate() {
        return niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }

    public String getNiceShareDate() {
        return niceShareDate;
    }

    public void setNiceShareDate(String niceShareDate) {
        this.niceShareDate = niceShareDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getProjectLink() {
        return projectLink;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public int getSelfVisible() {
        return selfVisible;
    }

    public void setSelfVisible(int selfVisible) {
        this.selfVisible = selfVisible;
    }

    public long getShareDate() {
        return shareDate;
    }

    public void setShareDate(long shareDate) {
        this.shareDate = shareDate;
    }

    public String getShareUser() {
        return shareUser;
    }

    public void setShareUser(String shareUser) {
        this.shareUser = shareUser;
    }

    public int getSuperChapterId() {
        return superChapterId;
    }

    public void setSuperChapterId(int superChapterId) {
        this.superChapterId = superChapterId;
    }

    public String getSuperChapterName() {
        return superChapterName;
    }

    public void setSuperChapterName(String superChapterName) {
        this.superChapterName = superChapterName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public static class TagsBean implements Parcelable{
        /**
         * name : 公众号
         * url : /wxarticle/list/410/1
         */
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeString(this.url);
        }

        public TagsBean() {
        }

        protected TagsBean(Parcel in) {
            this.name = in.readString();
            this.url = in.readString();
        }

        public static final Creator<TagsBean> CREATOR = new Creator<TagsBean>() {
            @Override
            public TagsBean createFromParcel(Parcel source) {
                return new TagsBean(source);
            }

            @Override
            public TagsBean[] newArray(int size) {
                return new TagsBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.apkLink);
        dest.writeInt(this.audit);
        dest.writeString(this.author);
        dest.writeInt(this.chapterId);
        dest.writeString(this.chapterName);
        dest.writeByte(this.collect ? (byte) 1 : (byte) 0);
        dest.writeInt(this.courseId);
        dest.writeString(this.desc);
        dest.writeString(this.envelopePic);
        dest.writeByte(this.fresh ? (byte) 1 : (byte) 0);
        dest.writeInt(this.id);
        dest.writeString(this.link);
        dest.writeString(this.niceDate);
        dest.writeString(this.niceShareDate);
        dest.writeString(this.origin);
        dest.writeString(this.prefix);
        dest.writeString(this.projectLink);
        dest.writeLong(this.publishTime);
        dest.writeInt(this.selfVisible);
        dest.writeLong(this.shareDate);
        dest.writeString(this.shareUser);
        dest.writeInt(this.superChapterId);
        dest.writeString(this.superChapterName);
        dest.writeString(this.title);
        dest.writeInt(this.type);
        dest.writeInt(this.userId);
        dest.writeInt(this.visible);
        dest.writeInt(this.zan);
        dest.writeTypedList(this.tags);
    }

    public ArticleBean() {
    }

    protected ArticleBean(Parcel in) {
        this.apkLink = in.readString();
        this.audit = in.readInt();
        this.author = in.readString();
        this.chapterId = in.readInt();
        this.chapterName = in.readString();
        this.collect = in.readByte() != 0;
        this.courseId = in.readInt();
        this.desc = in.readString();
        this.envelopePic = in.readString();
        this.fresh = in.readByte() != 0;
        this.id = in.readInt();
        this.link = in.readString();
        this.niceDate = in.readString();
        this.niceShareDate = in.readString();
        this.origin = in.readString();
        this.prefix = in.readString();
        this.projectLink = in.readString();
        this.publishTime = in.readLong();
        this.selfVisible = in.readInt();
        this.shareDate = in.readLong();
        this.shareUser = in.readString();
        this.superChapterId = in.readInt();
        this.superChapterName = in.readString();
        this.title = in.readString();
        this.type = in.readInt();
        this.userId = in.readInt();
        this.visible = in.readInt();
        this.zan = in.readInt();
        this.tags = in.createTypedArrayList(TagsBean.CREATOR);
    }

    public static final Creator<ArticleBean> CREATOR = new Creator<ArticleBean>() {
        @Override
        public ArticleBean createFromParcel(Parcel source) {
            return new ArticleBean(source);
        }

        @Override
        public ArticleBean[] newArray(int size) {
            return new ArticleBean[size];
        }
    };
}
