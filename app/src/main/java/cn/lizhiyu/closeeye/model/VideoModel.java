package cn.lizhiyu.closeeye.model;

import java.io.Serializable;

/**
 * Created by king on 2018/2/13.
 */

public class VideoModel implements Serializable
{
    private String videoUrls;

    private String posterId;

    private String publishDateStr;

    private String mediaType;

    private String favoriteCount;

    private String memberOnly;

    private String danmakuCount;

    private String likeCount;

    private String durationMin;

    private String ifFree;

    private String coverUrl;

    private String description;

    private String url;

    private String publishDate;

    private String commentCount;

    private String catPathKey;

    private String posterScreenName;

    public String dislikeCount;

    private String title;

    private String fileOptions;

    private String viewCount;

    public String getVideoUrls() {
        return videoUrls;
    }

    public void setVideoUrls(String videoUrls) {
        this.videoUrls = videoUrls;
    }

    public String getPosterId() {
        return posterId;
    }

    public void setPosterId(String posterId) {
        this.posterId = posterId;
    }

    public String getPublishDateStr() {
        return publishDateStr;
    }

    public void setPublishDateStr(String publishDateStr) {
        this.publishDateStr = publishDateStr;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(String favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public String getMemberOnly() {
        return memberOnly;
    }

    public void setMemberOnly(String memberOnly) {
        this.memberOnly = memberOnly;
    }

    public String getDanmakuCount() {
        return danmakuCount;
    }

    public void setDanmakuCount(String danmakuCount) {
        this.danmakuCount = danmakuCount;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(String durationMin) {
        this.durationMin = durationMin;
    }

    public String getIfFree() {
        return ifFree;
    }

    public void setIfFree(String ifFree) {
        this.ifFree = ifFree;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getCatPathKey() {
        return catPathKey;
    }

    public void setCatPathKey(String catPathKey) {
        this.catPathKey = catPathKey;
    }

    public String getPosterScreenName() {
        return posterScreenName;
    }

    public void setPosterScreenName(String posterScreenName) {
        this.posterScreenName = posterScreenName;
    }

    public String getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(String dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileOptions() {
        return fileOptions;
    }

    public void setFileOptions(String fileOptions) {
        this.fileOptions = fileOptions;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }
}
