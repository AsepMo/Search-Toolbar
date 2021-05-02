package com.search.toolbar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.graphics.Color;
import android.speech.RecognizerIntent;
import android.os.Bundle;
import android.view.View;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.ImageView;
import com.search.engine.widget.SearchBox;
import com.search.engine.widget.SearchBox.MenuListener;
import com.search.engine.widget.SearchBox.SearchListener;
import com.search.engine.widget.SearchBox.ThumbnailListener;
import com.search.engine.widget.SearchResult;

import java.util.ArrayList;
import android.widget.Toast;
public class ApplicationActivity extends AppCompatActivity {
    private Boolean isSearch;
	private SearchBox search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		search = (SearchBox) findViewById(R.id.searchbox);
        search.enableVoiceRecognition(this);
        for(int x = 0; x < 10; x++){
            SearchResult option = new SearchResult("Result " + Integer.toString(x), R.drawable.ic_history);
            search.addSearchable(option);
        }
        search.setMenuListener(new MenuListener(){

                @Override
                public void onMenuClick() {
                    //Hamburger has been clicked
                    //Toast.makeText(MainActivity.this, "Menu click", Toast.LENGTH_LONG).show();              
                }

            });
        search.setThumbnails("https://yt3.ggpht.com/ytc/AAUvwniXpFZbF9hWgU-51Xn6fLt1utL1h20Gt-MhNZuj=s800-c-k-c0x00ffffff-no-rj");
        search.setBackground(R.color.colorPrimary);
        search.setThumbnailsListener(new SearchBox.ThumbnailListener(){
            @Override
            public void onThumbnailClick(){
                Toast.makeText(ApplicationActivity.this, "Thumbnails", Toast.LENGTH_SHORT).show();
            }
        });
        search.setSearchListener(new SearchListener(){

                @Override
                public void onSearchOpened() {
                    //Use this to tint the screen
                }

                @Override
                public void onSearchClosed() {
                    //Use this to un-tint the screen
                }

                @Override
                public void onSearchTermChanged(String term) {
                    //React to the search term changing
                    //Called after it has updated results
                }

                @Override
                public void onSearch(String searchTerm) {
                    //Toast.makeText(MainActivity.this, searchTerm +" Searched", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onResultClick(SearchResult result) {
                    //React to a result being clicked
                }

                @Override
                public void onSearchCleared() {
                    //Called when the clear button is clicked
                }

            });
        search.setOverflowMenu(R.menu.menu_application);
        search.setOverflowMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_about:
                            //Toast.makeText(MainActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
                            return true;
                    }
                    return false;
                }
            });
        
    }


}
