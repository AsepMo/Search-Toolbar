package com.search.toolbar.youtube.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.search.toolbar.youtube.models.YouTubeSearch;
import com.search.toolbar.youtube.config.YouTubeConfig;
import com.search.toolbar.youtube.utils.YouTubeUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class YouTubeVideosLoader extends AsyncTaskLoader<List<YouTubeSearch>> {

    private String keywords;

    public YouTubeVideosLoader(Context context, String keywords) {
        super(context);
        this.keywords = keywords;
    }

    @Override
    public List<YouTubeSearch> loadInBackground() {

        ArrayList<YouTubeSearch> items = new ArrayList<>();
        return items;
    }
    
    @Override
    public void deliverResult(List<YouTubeSearch> data) {
        if (isReset()) {
            // The Loader has been reset; ignore the result and invalidate the data.
            return;
        }
        super.deliverResult(data);
    }
}
