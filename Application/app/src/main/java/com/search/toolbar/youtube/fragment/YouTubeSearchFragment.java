package com.search.toolbar.youtube.fragment;

import com.search.toolbar.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.content.Intent;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import static java.lang.Thread.sleep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.List;


import com.search.toolbar.youtube.models.YouTubeSearch;
import com.search.toolbar.youtube.adapters.YouTubeSearchAdapters;
import com.search.toolbar.youtube.utils.NetworkConf;
import com.search.toolbar.youtube.listeners.OnItemSelected;
import com.search.toolbar.youtube.listeners.OnFavoritesSelected;
import com.search.toolbar.youtube.listeners.YouTubeEventsListener;
import com.search.toolbar.youtube.data.YouTubeSqlDb;
import com.search.toolbar.youtube.config.YouTubeConfig;
import com.search.toolbar.youtube.loaders.YouTubeVideosLoader;

import com.search.toolbar.ApplicationActivity;
import com.search.toolbar.youtube.listeners.VideoJsonResponseParser;

public class YouTubeSearchFragment extends BaseFragment implements YouTubeEventsListener<YouTubeSearch> {

    public static String TAG = YouTubeSearchFragment.class.getSimpleName();

    private RecyclerView videosFoundListView;
    private List<YouTubeSearch> searchResultsList;
    private YouTubeSearchAdapters videoListAdapter;
    private ProgressBar loadingProgressBar;
    private String keyword = "";
    private Context context;
    private OnItemSelected itemSelected;
    private OnFavoritesSelected onFavoritesSelected;
    private RelativeLayout nothingFoundMessageHolder;
    String nextPageToken = "";
    public YouTubeSearchFragment() {
        // Required empty public constructor
    }

    public static YouTubeSearchFragment newInstance() {
        return new YouTubeSearchFragment();
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchResultsList = new ArrayList<>();
       // networkConf = new NetworkConf(getActivity());
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_youtube_search, container, false);
        videosFoundListView = (RecyclerView) v.findViewById(R.id.fragment_list_items);
        videosFoundListView.setLayoutManager(new LinearLayoutManager(context));
        videosFoundListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (!recyclerView.canScrollVertically(1)) {
                        new FetchVideos(keyword).execute();

                    }
                }

            });
        
        videosFoundListView.invalidate();
        
        loadingProgressBar = (ProgressBar) v.findViewById(R.id.fragment_progress_bar);
        videoListAdapter = new YouTubeSearchAdapters(context, searchResultsList);
        videoListAdapter.notifyDataSetChanged();
        videoListAdapter.setOnYouTubeEventsListener(this);
        videosFoundListView.setAdapter(videoListAdapter);
        nothingFoundMessageHolder = (RelativeLayout) v.findViewById(R.id.nothing_found_holder);
        //disable swipe to refresh for this tab
        return v;
    }
    
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof ApplicationActivity) {
            this.context = context;
            itemSelected = (OnItemSelected) context;
            onFavoritesSelected = (OnFavoritesSelected) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
        this.itemSelected = null;
        this.onFavoritesSelected = null;
    }

    /**
     * Search for query on youTube by using YouTube Data API V3
     *
     * @param query
     */
    public void searchQuery(final String query) {
        //check network connectivity
        /*if (!networkConf.isNetworkAvailable()) {
            networkConf.createNetErrorDialog();
            return;
        }*/

        new FetchVideos(query).execute();
    }
    
    private Response.Listener<String> respone =  new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            //If we are getting success from server
            try {
                JSONObject object = new JSONObject(response);
                JSONArray array = object.getJSONArray("items");
                nextPageToken = object.getString("nextPageToken");
                for (int i = 0; i < array.length(); i++) {
                    searchResultsList.add(VideoJsonResponseParser.getParsedDataOf(array.getJSONObject(i)));
                    videoListAdapter.notifyDataSetChanged();
                    videosFoundListView.invalidate();
                    
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            loadingProgressBar.setVisibility(View.GONE);
        }
    };

    private Response.ErrorListener error = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            //You can handle error here if you want
            loadingProgressBar.setVisibility(View.GONE);
            NetworkResponse networkResponse = error.networkResponse;
            if (networkResponse != null && networkResponse.data != null) {

            }
           // Toast.makeText(getBaseContext(), "Error in loading video", Toast.LENGTH_SHORT).show();
        }
    };


    private class FetchVideos extends AsyncTask<String,String,String> {
        
        
        public FetchVideos(String key){
            keyword = key;
        }
        
        @Override
        protected String doInBackground(String... strings) {

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String URL = YouTubeConfig.SEARCH_VIDEO_URL+ "&q=" + keyword + "&pageToken=" + nextPageToken;
            
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, respone, error) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request

                    return params;
                }
            };

            //Adding the string request to the queue
            stringRequest.setShouldCache(false);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                             0,
                                             DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                             DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                                         ));
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.getCache().clear();
            requestQueue.add(stringRequest);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            
            videosFoundListView.invalidate();
            updateList();
//            progressBar.setVisibility(View.GONE);
        }
    }
    
    @Override
    public void onShareClicked(String itemId) {
        share(YouTubeConfig.SHARE_VIDEO_URL + itemId);
    }

    @Override
    public void onFavoriteClicked(YouTubeSearch video, boolean isChecked) {
        onFavoritesSelected.onFavoritesSelected(video, isChecked); // pass event to MainActivity
    }

    @Override
    public void onItemClick(YouTubeSearch video) {
        YouTubeSqlDb.getInstance().videos(YouTubeSqlDb.VIDEOS_TYPE.RECENTLY_WATCHED).create(video);
        //itemSelected.onVideoSelected(video);
        itemSelected.onPlaylistSelected(searchResultsList, searchResultsList.indexOf(video));
    }

    @Override
    public void updateList() {
        videoListAdapter.notifyDataSetChanged();
        if (videoListAdapter.getItemCount() > 0) {
            nothingFoundMessageHolder.setVisibility(View.GONE);
            videosFoundListView.setVisibility(View.VISIBLE);
        } else {
            nothingFoundMessageHolder.setVisibility(View.VISIBLE);
            videosFoundListView.setVisibility(View.GONE);
        }
    }
}

