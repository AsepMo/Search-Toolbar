package com.search.toolbar.youtube.listeners;

import com.search.toolbar.youtube.models.YouTubeSearch;

public interface OnFavoritesSelected {
    
    void onFavoritesSelected(YouTubeSearch video, boolean isChecked);
}
