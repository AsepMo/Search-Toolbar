package com.search.toolbar.youtube.models;

import java.io.Serializable;

public class YouTubeSearch implements Serializable {
    private String id;
    private String title;
    private String thumbnailURL;
    private String duration;
    private String viewCount;
    private String videoId;
    private String description; 
    private String publishedAt;
    private String channelId;
    private String channeThumbnail;
    private String channelTitle;
    
    public YouTubeSearch() {
        this.id = "";
        this.title = "";
        this.thumbnailURL = "";
        this.duration = "";
        this.viewCount = "";
        this.videoId = "";
        this.publishedAt = "";
        this.channelId = "";
        this.description = "";
        this.channelTitle = "";
        this.channeThumbnail = "";
    }

    public YouTubeSearch(YouTubeSearch newVideo) {
        this.id = newVideo.id;
        this.title = newVideo.title;
        this.thumbnailURL = newVideo.thumbnailURL;
        this.duration = newVideo.duration;
        this.viewCount = newVideo.viewCount;
        this.videoId = newVideo.videoId;
        this.description = newVideo.description;
        this.channelId = newVideo.channelId;
        this.channelTitle = newVideo.channelTitle;
        this.channeThumbnail = newVideo.channeThumbnail;
    }

    public YouTubeSearch(String id, String title, String thumbnailURL, String duration, String viewCount) {
        this.id = id;
        this.title = title;
        this.thumbnailURL = thumbnailURL;
        this.duration = duration;
        this.viewCount = viewCount;
    }

    public YouTubeSearch(String id, String title, String thumbnailURL, String duration, String viewCount, String videoId, String description, String published, String channelId, String channelTitle, String channelThumbnails) {
        this.id = id;
        this.title = title;
        this.thumbnailURL = thumbnailURL;
        this.duration = duration;
        this.viewCount = viewCount;
        this.videoId = videoId;
        this.publishedAt = published;
        this.description = description;
        this.channelId = channelId;
        this.channelTitle = channelTitle;
        this.channeThumbnail = channelThumbnails;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnail) {
        this.thumbnailURL = thumbnail;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }
    
    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return channeThumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.channeThumbnail = thumbnail;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }
    @Override
    public String toString() {
        return "YouTubeSearch {" +
            "id='" + id + '\'' +
            ", title='" + title + '\'' +
            '}';
    }
}

