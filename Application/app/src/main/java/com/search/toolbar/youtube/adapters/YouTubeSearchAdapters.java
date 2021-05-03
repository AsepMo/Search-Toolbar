package com.search.toolbar.youtube.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.search.toolbar.R;
import com.search.toolbar.youtube.data.YouTubeSqlDb;
import com.search.toolbar.youtube.listeners.YouTubeEventsListener;
import com.search.toolbar.youtube.models.YouTubeSearch;
import com.search.toolbar.youtube.config.YouTubeConfig;
import com.search.toolbar.youtube.utils.YouTubeUtils;

import java.util.List;

/**
 * Custom ArrayAdapter which enables setup of a list view row views
 * Created by smedic on 8.2.16..
 */
public class YouTubeSearchAdapters extends RecyclerView.Adapter<YouTubeSearchAdapters.ViewHolder> implements View.OnClickListener {

    public static String TAG = YouTubeSearchAdapters.class.getSimpleName();
    
    private Context context;
    private final List<YouTubeSearch> list;
    private boolean[] itemChecked;
    private YouTubeEventsListener<YouTubeSearch> itemEventsListener;

    public YouTubeSearchAdapters(Context context, List<YouTubeSearch> list) {
        super();
        this.list = list;
        this.context = context;
        this.itemChecked = new boolean[(int) YouTubeConfig.NUMBER_OF_VIDEOS_RETURNED];
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_youtube_search_item, null);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final YouTubeSearch video = list.get(position);
        if (YouTubeSqlDb.getInstance().videos(YouTubeSqlDb.VIDEOS_TYPE.FAVORITE).checkIfExists(video.getId())) {
            itemChecked[position] = true;
        } else {
            itemChecked[position] = false;
        }

        Glide.with(context).load(video.getThumbnailURL()).into(holder.thumbnail);

        holder.title.setText(video.getTitle());
        holder.duration.setText(video.getDuration());
        holder.viewCount.setText(video.getViewCount());
        holder.favoriteCheckBox.setOnCheckedChangeListener(null);
        holder.favoriteCheckBox.setChecked(itemChecked[position]);

        holder.favoriteCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton btn, boolean isChecked) {
                    itemChecked[position] = isChecked;
                    if (itemEventsListener != null) {
                        itemEventsListener.onFavoriteClicked(video, isChecked);
                    }
                }
            });

        holder.shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemEventsListener != null) {
                        itemEventsListener.onShareClicked(video.getId());
                    }
                }
            });

        holder.itemView.setTag(video);
    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }

    @Override
    public void onClick(View v) {
        if (itemEventsListener != null) {
            YouTubeSearch item = (YouTubeSearch) v.getTag();
            itemEventsListener.onItemClick(item);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title;
        TextView duration;
        TextView viewCount;
        CheckBox favoriteCheckBox;
        ImageView shareButton;

        public ViewHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.video_thumbnail);
            title = (TextView) itemView.findViewById(R.id.video_title);
            duration = (TextView) itemView.findViewById(R.id.video_duration);
            viewCount = (TextView) itemView.findViewById(R.id.views_number);
            favoriteCheckBox = (CheckBox) itemView.findViewById(R.id.favoriteButton);
            shareButton = (ImageView) itemView.findViewById(R.id.shareButton);
        }
    }

    public void setOnYouTubeEventsListener(YouTubeEventsListener<YouTubeSearch> listener) {
        itemEventsListener = listener;
    }
}

