package cn.lizhiyu.closeeye.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by king on 2018/2/24.
 */

public class DiscItemModel implements Serializable
{
    private boolean hasNext;

    private int viewCount;

    private String title;

    private String url;

    private List imageUrls;

    private int likeCount;

    private int commentCount;

    private String id;

    private String posterId;

    private String posterScreenName;

    private String content;

    private double publishDate;

    private int shareCount;

    private String publishDateStr;

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List imagesUrls) {
        this.imageUrls = imagesUrls;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosterId() {
        return posterId;
    }

    public void setPosterId(String posterId) {
        this.posterId = posterId;
    }

    public String getPosterScreenName() {
        return posterScreenName;
    }

    public void setPosterScreenName(String posterScreenName) {
        this.posterScreenName = posterScreenName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(double publishDate) {
        this.publishDate = publishDate;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public String getPublishDateStr() {
        return publishDateStr;
    }

    public void setPublishDateStr(String publishDateStr) {
        this.publishDateStr = publishDateStr;
    }
}
