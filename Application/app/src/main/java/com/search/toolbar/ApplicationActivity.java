package com.search.toolbar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v4.content.ContextCompat;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.speech.RecognizerIntent;
import android.os.Bundle;
import android.view.View;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.search.toolbar.youtube.fragment.YouTubeSearchFragment;
import com.search.toolbar.youtube.listeners.OnItemSelected;
import com.search.toolbar.youtube.listeners.OnFavoritesSelected;
import com.search.toolbar.youtube.data.YouTubeSqlDb;
import com.search.toolbar.youtube.utils.NetworkConf;
import com.search.engine.widget.SearchBox;
import com.search.engine.widget.SearchBox.MenuListener;
import com.search.engine.widget.SearchBox.SearchListener;
import com.search.engine.widget.SearchBox.ThumbnailListener;
import com.search.engine.widget.SearchResult;
import com.search.engine.widget.TabDrawer;
import com.search.engine.widget.model.Tab;
import com.search.engine.widget.model.TabDrawerData;
import com.search.engine.widget.model.TabListItem;

import java.util.ArrayList;
import java.util.List;
import com.search.toolbar.youtube.utils.SuggestionsLoader;
import com.search.toolbar.youtube.models.YouTubeSearch;
import com.search.toolbar.youtube.models.YouTubePlaylist;

public class ApplicationActivity extends AppCompatActivity implements OnItemSelected, OnFavoritesSelected {
    private Boolean isSearch;
	private SearchBox search;
    Context context;
    Activity activity;

    private TabDrawer tabDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
		context = this;
        activity = this;
        YouTubeSqlDb.getInstance().init(this);

		search = (SearchBox) findViewById(R.id.searchbox);
        search.setHint("Search");
        search.enableVoiceRecognition(this);
        for (int x = 0; x < 10; x++) {
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
                public void onThumbnailClick() {
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
                    SearchResult option = new SearchResult(term, R.drawable.ic_history);
                    search.addSearchable(option);
                }

                @Override
                public void onSearch(String searchTerm) {
                    // check network connection. If not available, do not query.
                    // this also disables onSuggestionClick triggering
                    YouTubeSearchFragment frag = YouTubeSearchFragment.newInstance();
                    if (frag == null) {
                        frag.searchQuery(searchTerm);
                    }
                    showFragment(frag);


                    //Toast.makeText(ApplicationActivity.this, searchTerm +" Searched", Toast.LENGTH_LONG).show();
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
        //     showFragment(new YouTubeSearchFragment());

        prepareTabDrawer();
    }

    @Override
    public void onVideoSelected(YouTubeSearch video) {


    }

    @Override
    public void onPlaylistSelected(List<YouTubeSearch> playlist, int position) {
        
    }

    @Override
    public void onFavoritesSelected(YouTubeSearch video, boolean isChecked) {
    }

    private TabDrawerData prepareTabArray() {
        return new TabDrawerData()
            // Simple
            .setTabIconColors(
            Color.parseColor("#3199ff"),
            Color.parseColor("#ffffff"),
            Color.parseColor("#CCCCCC")
        )
            .setTabTitleSize(12)
            .setTabTitleColors(
            ContextCompat.getColor(context, R.color.tabTitle),
            ContextCompat.getColor(context, R.color.tabTitle_selected),
            Color.parseColor("#CCCCCC")
        )
            .setTabBackgroundColors(
            ContextCompat.getColor(context, R.color.tabBackground),
            ContextCompat.getColor(context, R.color.tabBackground_selected),
            Color.parseColor("#2C7DDC")
        )
            .setTabListItemTitleColors(Color.parseColor("#ffffff"))
            .setTabListItemTitleSize(16)

            .addTab(new Tab()
                    .setTitle("Menu")
                    .setIconImage(R.drawable.n_activity)
                    .addTabListItem(new TabListItem("Playlist", R.drawable.ic_playlist_play))
                    .addTabListItem(new TabListItem("Populer", R.drawable.ic_trending_up))
                    .addTabListItem(new TabListItem("Search", R.drawable.ic_movie_search))
                    .addTabListItem(new TabListItem("Favorite", R.drawable.ic_favorites))
                    .addTabListItem(new TabListItem("History", R.drawable.ic_history))
                    )

            .addTab(new Tab()
                    .setTitle("Queue")
                    .setIconImage(R.drawable.n_queue)
                    .addTabListItem(new TabListItem("Add to Queue", R.drawable.ic_playlist_plus))
                    .addTabListItem(new TabListItem("Download", R.drawable.ic_download))
                    .addTabListItem(new TabListItem("Uploads", R.drawable.ic_upload)))


            .addTab(new Tab()
                    .setTitle("Chat")
                    .setIconImage(R.drawable.n_chat)
                    // .setDrawerListColumnNumber(2)
                    .addTabListItem(new TabListItem("Friends", R.drawable.ic_face_white_24dp))
                    .addTabListItem(new TabListItem("Add Friend", R.drawable.ic_person_add_white_24dp))
                    .addTabListItem(new TabListItem("Start Group Chat", R.drawable.ic_people_white_24dp))
                    .addTabListItem(new TabListItem("Funny Moments", R.drawable.ic_sentiment_very_satisfied_white_24dp)))


            .addTab(new Tab()
                    .setTitle("Reports")
                    .setIconImage(R.drawable.n_report)
                    .addTabListItem(new TabListItem("Completed Jobs", R.drawable.ic_event_available_white_24dp))
                    .addTabListItem(new TabListItem("Cancelled Jobs", R.drawable.ic_event_busy_white_24dp))
                    .addTabListItem(new TabListItem("Customer Feedbacks", R.drawable.ic_feedback_white_24dp))
                    .addTabListItem(new TabListItem("Documents", R.drawable.ic_description_white_24dp))
                    )

            .addTab(new Tab()
                    .setTitle("Settings")
                    .setIconImage(R.drawable.n_settings)
                    .addTabListItem(new TabListItem("General", R.drawable.ic_settings_white_24dp))
                    .addTabListItem(new TabListItem("My Account", R.drawable.ic_account_box))
                    .addTabListItem(new TabListItem("Accesibility", R.drawable.ic_accessibility_white_24dp))
                    .addTabListItem(new TabListItem("Notifications", R.drawable.ic_notifications_white_24dp))
                    .addTabListItem(new TabListItem("Bookmarks", R.drawable.ic_collections_bookmark_white_24dp))
                    .addTabListItem(new TabListItem("Shared Folders", R.drawable.ic_folder_shared_white_24dp))
                    .addTabListItem(new TabListItem("Cast to TV", R.drawable.ic_cast_white_24dp))
                    .addTabListItem(new TabListItem("Other Applications", R.drawable.ic_apps_white_24dp)));

    }

    public void showFragment(Fragment fragment) {
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.content_frame, fragment)
            .commitAllowingStateLoss();
    }



    public void prepareTabDrawer() { prepareTabDrawer(false); }

    public void prepareTabDrawer(boolean additional) {
        TabDrawerData tabDrawerDataTemp = prepareTabArray();

        // Clone 3 tabs to the end to fill space when it is Left or Right TabDrawer
        if (additional) {
            TabDrawerData tabDrawerDataTemp2 = prepareTabArray();
            tabDrawerDataTemp.addTab(tabDrawerDataTemp2.getTab(3).setTitle("Add 1"));
            tabDrawerDataTemp.addTab(tabDrawerDataTemp2.getTab(2).setTitle("Add 2"));
            tabDrawerDataTemp.addTab(tabDrawerDataTemp2.getTab(1).setTitle("Add 3"));
        }

        final TabDrawerData tabDrawerData = tabDrawerDataTemp;

        tabDrawer = new TabDrawer(context, activity, R.id.tabDrawer, tabDrawerData) {
            @Override
            public void onTabDrawerClicked(int tabPosition, int itemPosition) {
                super.onTabDrawerClicked(tabPosition, itemPosition);

                String text = tabDrawerData.getTab(tabPosition).getTitle();
                if (text == null) text = "... MORE ...";

                if (tabDrawerData.getTab(tabPosition).hasItems()) {
                    if (tabDrawerData.getTab(tabPosition).getTabItemList().get(itemPosition).getTitle() != null)
                        text += " -> " + tabDrawerData.getTab(tabPosition).getTabItemList().get(itemPosition).getTitle();

                    text += " - ( " + tabPosition + ", " + itemPosition + " )";
                } else
                    text += " - ( " + tabPosition + " )";


                Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                toast.show();

                if (tabPosition == 0) {

                    if (itemPosition == 0) {
                        showFragment(new YouTubeSearchFragment());     
                    }if (itemPosition == 1) {
                        showFragment(new YouTubeSearchFragment());
                    }if (itemPosition == 2) {
                        showFragment(new YouTubeSearchFragment());
                    }if (itemPosition == 3) {
                        showFragment(new YouTubeSearchFragment());
                    } if (itemPosition == 4) {
                        showFragment(new YouTubeSearchFragment());
                    }

                }

                if (tabPosition == 1) {

                    if (itemPosition == 0) {
                        showFragment(new YouTubeSearchFragment());        
                    }if (itemPosition == 1) {
                        showFragment(new YouTubeSearchFragment());     
                    }if (itemPosition == 2) {
                        showFragment(new YouTubeSearchFragment());     
                    }

                }

                if (tabPosition == 2) {

                    if (itemPosition == 0) {
                        showFragment(new YouTubeSearchFragment());       
                    }if (itemPosition == 1) {
                        showFragment(new YouTubeSearchFragment());     
                    }if (itemPosition == 2) {
                        showFragment(new YouTubeSearchFragment());     
                    }if (itemPosition == 3) {
                        showFragment(new YouTubeSearchFragment());     
                    } if (itemPosition == 4) {
                        showFragment(new YouTubeSearchFragment());     
                    }

                }

                if (tabPosition == 3) {

                    if (itemPosition == 0) {
                        showFragment(new YouTubeSearchFragment());        
                    }if (itemPosition == 1) {
                        showFragment(new YouTubeSearchFragment());     
                    }if (itemPosition == 2) {
                        showFragment(new YouTubeSearchFragment());     
                    }if (itemPosition == 3) {
                        showFragment(new YouTubeSearchFragment());     
                    } if (itemPosition == 4) {
                        showFragment(new YouTubeSearchFragment());     
                    }

                } 

                if (tabPosition == 4) {

                    if (itemPosition == 0) {
                        showFragment(new YouTubeSearchFragment());        
                    }if (itemPosition == 1) {
                        showFragment(new YouTubeSearchFragment());     
                    }if (itemPosition == 2) {                 
                        // AuthActivity.start(activity);
                    }if (itemPosition == 3) {
                        showFragment(new YouTubeSearchFragment());     
                    }if (itemPosition == 4) {
                        showFragment(new YouTubeSearchFragment());     
                    }if (itemPosition == 5) {
                        showFragment(new YouTubeSearchFragment());     
                    }
                    if (itemPosition == 6) {
                        showFragment(new YouTubeSearchFragment());     
                    }
                    if (itemPosition == 7) {
                        showFragment(new YouTubeSearchFragment());     
                    }

                } 

            }

        };
        tabDrawer.initialize();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.content_frame);
        if (fragment == null && tabDrawer.isDrawerOpen()) {
            tabDrawer.closeDrawer();
            fm.beginTransaction().remove(fragment).commitAllowingStateLoss();

        } else {
            super.onBackPressed();
        }
    }

}
