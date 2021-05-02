package com.search.engine.widget;

import android.graphics.drawable.Drawable;

public class SearchResult {
    public String title;
    public int icon;

    /**
     * Create a search result with text and an icon
     * @param title
     * @param icon
     */
    public SearchResult(String title, int icon) {
       this.title = title;
       this.icon = icon;
    }

    public int viewType = 0;

    public SearchResult(String title){
        this.title = title;
    }

    public SearchResult(int viewType, String title){
        this.viewType = viewType;
        this.title = title;
    }
    
    public String getTitle(){
        return title;
    }
    
    public int getIcon(){
        return icon;
    }
    /**
     * Return the title of the result
     */
    @Override
    public String toString() {
        return title;
    }
    
}
