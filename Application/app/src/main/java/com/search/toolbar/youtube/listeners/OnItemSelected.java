package com.search.toolbar.youtube.listeners;

import com.search.toolbar.youtube.models.YouTubeSearch;

import java.util.List;

public interface OnItemSelected {
    void onVideoSelected(YouTubeSearch video);

    void onPlaylistSelected(List<YouTubeSearch> playlist, int position);
}
