package cn.lizhiyu.closeeye.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class MovieItemModel implements Serializable
{
    public int ratingCount;

    public int commentCount;

    public ArrayList videoUrls;

    public String title;

    public ArrayList directors;

    public ArrayList actors;

    public ArrayList languages;

    public String id;

    public ArrayList countries;

    public float durationMin;

    public String catName1;

    public Date publishDate;

    public int rating;

    public int favoriteCount;

    public int releaseStatues;

    public ArrayList writers;

    public String description;

    public int wishCount;

    public String pressDate;

    public ArrayList titleAliases;

    public ArrayList imageUrls;

    public String coverUrl;

    public String url;

    public int reviewCount;

    public ArrayList tags;

    public ArrayList releaseDates;

    public ArrayList genres;

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public ArrayList getVideoUrls() {
        return videoUrls;
    }

    public void setVideoUrls(ArrayList videoUrls) {
        this.videoUrls = videoUrls;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList getDirectors() {
        return directors;
    }

    public void setDirectors(ArrayList directors) {
        this.directors = directors;
    }

    public ArrayList getActors() {
        return actors;
    }

    public void setActors(ArrayList actors) {
        this.actors = actors;
    }

    public ArrayList getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList languages) {
        this.languages = languages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList getCountries() {
        return countries;
    }

    public void setCountries(ArrayList countries) {
        this.countries = countries;
    }

    public float getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(float durationMin) {
        this.durationMin = durationMin;
    }

    public String getCatName1() {
        return catName1;
    }

    public void setCatName1(String catName1) {
        this.catName1 = catName1;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public int getReleaseStatues() {
        return releaseStatues;
    }

    public void setReleaseStatues(int releaseStatues) {
        this.releaseStatues = releaseStatues;
    }

    public ArrayList getWriters() {
        return writers;
    }

    public void setWriters(ArrayList writers) {
        this.writers = writers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWishCount() {
        return wishCount;
    }

    public void setWishCount(int wishCount) {
        this.wishCount = wishCount;
    }

    public String getPressDate() {
        return pressDate;
    }

    public void setPressDate(String pressDate) {
        this.pressDate = pressDate;
    }

    public ArrayList getTitleAliases() {
        return titleAliases;
    }

    public void setTitleAliases(ArrayList titleAliases) {
        this.titleAliases = titleAliases;
    }

    public ArrayList getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ArrayList imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public ArrayList getTags() {
        return tags;
    }

    public void setTags(ArrayList tags) {
        this.tags = tags;
    }

    public ArrayList getReleaseDates() {
        return releaseDates;
    }

    public void setReleaseDates(ArrayList releaseDates) {
        this.releaseDates = releaseDates;
    }

    public ArrayList getGenres() {
        return genres;
    }

    public void setGenres(ArrayList genres) {
        this.genres = genres;
    }
}
