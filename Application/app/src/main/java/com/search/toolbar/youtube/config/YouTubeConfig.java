package com.search.toolbar.youtube.config;

public class YouTubeConfig {
    
    public static String TAG = YouTubeConfig.class.getSimpleName();
    public static final boolean DEBUG = false;

    public static final String SUGGESTIONS_URL = "http://suggestqueries.google.com/complete/search?client=youtube&ds=yt&q=";
    public static final String YOUTUBE_BASE_URL = "http://youtube.com/watch?v=";
    public static final String SHARE_VIDEO_URL = "http://youtube.com/watch?v=";
    public static final String SHARE_PLAYLIST_URL = "https://www.youtube.com/playlist?list=";
    public static final String YOUTUBE_TYPE = "YT_MEDIA_TYPE";
    public static final String YOUTUBE_TYPE_VIDEO = "YT_VIDEO";
    public static final String YOUTUBE_TYPE_PLAYLIST= "YT_PLAYLIST";
    public static final String YOUTUBE_TYPE_PLAYLIST_VIDEO_POS = "YT_PLAYLIST_VIDEO_POS";

    public static final String YOUTUBE_DEVELOPER_API_KEY = "AIzaSyAYgHbHDXV1x-wSdJPqdPiwq-2GgdWEqWk";
    public static final String API_KEY = "AIzaSyDH3naOGPlOL175VfhVaRrzr0438MymNxM";
    public static final String POPULARVIDEO_URL = "https://www.googleapis.com/youtube/v3/videos?part=snippet%2CcontentDetails%2Cstatistics&chart=mostPopular&regionCode=ID&maxResults=150&key=" + YouTubeConfig.API_KEY;
    public static final String SEARCH_VIDEO_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=150&key=" + YouTubeConfig.API_KEY;
    public static final String VIDEO_DETAILS_URL = "https://www.googleapis.com/youtube/v3/videos?part=snippet%2CcontentDetails%2Cstatistics&key=" + YouTubeConfig.API_KEY;
    public static final String YOUTUBE_URL_VIDEO = "http://www.youtube.com/watch?v=";
    public static final long NUMBER_OF_VIDEOS_RETURNED = 30; //due to YouTube API rules - MAX 50
}
