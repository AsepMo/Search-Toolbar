package com.search.toolbar.youtube.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.search.toolbar.R;
import com.search.toolbar.youtube.listeners.YouTubeEventsListener;
import com.search.toolbar.youtube.models.YouTubePlaylist;

import java.util.List;

public class YouTubePlaylistAdapters extends RecyclerView.Adapter<YouTubePlaylistAdapters.ViewHolder> implements View.OnClickListener {

    public static String TAG = YouTubePlaylistAdapters.class.getSimpleName();
    
    private Context context;
    private List<YouTubePlaylist> playlists;
    private YouTubeEventsListener<YouTubePlaylist> itemEventsListener;

    public YouTubePlaylistAdapters(Context context, List<YouTubePlaylist> playlists) {
        super();
        this.context = context;
        this.playlists = playlists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_youtube_playlist_item, null);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final YouTubePlaylist playlist = playlists.get(position);
        Glide.with(context).load(playlist.getThumbnailURL()).into(holder.thumbnail);
        holder.title.setText(playlist.getTitle());
        String videosNumberText = context.getString(R.string.number_of_videos) + String.valueOf(playlist.getNumberOfVideos());
        holder.videosNumber.setText(videosNumberText);
        String status = context.getString(R.string.status) + playlist.getStatus();
        holder.privacy.setText(status);

        if (playlist.getStatus().equals("private")) {
            holder.shareButton.setEnabled(false);
        } else {
            holder.shareButton.setVisibility(View.VISIBLE);
        }

        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemEventsListener != null) {
                    itemEventsListener.onShareClicked(playlist.getId());
                }
            }
        });
        holder.itemView.setTag(playlist);
    }

    @Override
    public int getItemCount() {
        return (null != playlists ? playlists.size() : 0);
    }

    @Override
    public void onClick(View v) {
        if (itemEventsListener != null) {
            YouTubePlaylist item = (YouTubePlaylist) v.getTag();
            itemEventsListener.onItemClick(item);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title;
        TextView videosNumber;
        TextView privacy;
        ImageView shareButton;

        public ViewHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.video_thumbnail);
            title = (TextView) itemView.findViewById(R.id.playlist_title);
            videosNumber = (TextView) itemView.findViewById(R.id.videos_number);
            privacy = (TextView) itemView.findViewById(R.id.privacy);
            shareButton = (ImageView) itemView.findViewById(R.id.share_button);
        }
    }

    public void setOnYouTubeEventsListener(YouTubeEventsListener<YouTubePlaylist> listener) {
        itemEventsListener = listener;
    }
    
    
}
