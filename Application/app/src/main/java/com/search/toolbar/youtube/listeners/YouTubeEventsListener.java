package com.search.toolbar.youtube.listeners;

import com.search.toolbar.youtube.models.YouTubeSearch;

public interface YouTubeEventsListener<Model> {
    void onShareClicked(String videoId);

    void onFavoriteClicked(YouTubeSearch video, boolean isChecked);

    void onItemClick(Model model); //handle click on a row (video or playlist)
    
}
