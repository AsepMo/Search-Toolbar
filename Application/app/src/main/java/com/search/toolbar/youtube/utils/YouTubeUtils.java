package com.search.toolbar.youtube.utils;

import android.util.Log;

import com.search.toolbar.youtube.models.YouTubeSearch;

import java.util.Iterator;
import java.util.List;
public class YouTubeUtils {
    
    public static String TAG = YouTubeUtils.class.getSimpleName();
    /**
     * Converting ISO8601 formatted duration to normal readable time
     */
    public static String convertISO8601DurationToNormalTime(String isoTime) {
        String formattedTime = new String();

        if (isoTime.contains("H") && isoTime.contains("M") && isoTime.contains("S")) {
            String hours = isoTime.substring(isoTime.indexOf('T') + 1, isoTime.indexOf('H'));
            String minutes = isoTime.substring(isoTime.indexOf('H') + 1, isoTime.indexOf('M'));
            String seconds = isoTime.substring(isoTime.indexOf('M') + 1, isoTime.indexOf('S'));
            formattedTime = hours + ":" + formatTo2Digits(minutes) + ":" + formatTo2Digits(seconds);
        } else if (!isoTime.contains("H") && isoTime.contains("M") && isoTime.contains("S")) {
            String minutes = isoTime.substring(isoTime.indexOf('T') + 1, isoTime.indexOf('M'));
            String seconds = isoTime.substring(isoTime.indexOf('M') + 1, isoTime.indexOf('S'));
            formattedTime = minutes + ":" + formatTo2Digits(seconds);
        } else if (isoTime.contains("H") && !isoTime.contains("M") && isoTime.contains("S")) {
            String hours = isoTime.substring(isoTime.indexOf('T') + 1, isoTime.indexOf('H'));
            String seconds = isoTime.substring(isoTime.indexOf('H') + 1, isoTime.indexOf('S'));
            formattedTime = hours + ":00:" + formatTo2Digits(seconds);
        } else if (isoTime.contains("H") && isoTime.contains("M") && !isoTime.contains("S")) {
            String hours = isoTime.substring(isoTime.indexOf('T') + 1, isoTime.indexOf('H'));
            String minutes = isoTime.substring(isoTime.indexOf('H') + 1, isoTime.indexOf('M'));
            formattedTime = hours + ":" + formatTo2Digits(minutes) + ":00";
        } else if (!isoTime.contains("H") && !isoTime.contains("M") && isoTime.contains("S")) {
            String seconds = isoTime.substring(isoTime.indexOf('T') + 1, isoTime.indexOf('S'));
            formattedTime = "0:" + formatTo2Digits(seconds);
        } else if (!isoTime.contains("H") && isoTime.contains("M") && !isoTime.contains("S")) {
            String minutes = isoTime.substring(isoTime.indexOf('T') + 1, isoTime.indexOf('M'));
            formattedTime = minutes + ":00";
        } else if (isoTime.contains("H") && !isoTime.contains("M") && !isoTime.contains("S")) {
            String hours = isoTime.substring(isoTime.indexOf('T') + 1, isoTime.indexOf('H'));
            formattedTime = hours + ":00:00";
        }

        return formattedTime;
    }

    /**
     * Makes values consist of 2 letters "01"
     */
    private static String formatTo2Digits(String str) {
        if (str.length() < 2) {
            str = "0" + str;
        }
        return str;
    }

    /**
     * Prints videos nicely formatted
     *
     * @param videos
     */
    public static void prettyPrintVideos(List<YouTubeSearch> videos) {
        Log.d(TAG, "=============================================================");
        Log.d(TAG, "\t\tTotal Videos: " + videos.size());
        Log.d(TAG, "=============================================================\n");

        Iterator<YouTubeSearch> playlistEntries = videos.iterator();

        while (playlistEntries.hasNext()) {
            YouTubeSearch playlistItem = playlistEntries.next();
            Log.d(TAG, " video name  = " + playlistItem.getTitle());
            Log.d(TAG, " video id    = " + playlistItem.getId());
            Log.d(TAG, " duration    = " + playlistItem.getDuration());
            Log.d(TAG, " thumbnail   = " + playlistItem.getThumbnailURL());
            Log.d(TAG, "\n-------------------------------------------------------------\n");
        }
    }

    /**
     * Prints video nicely formatted
     *
     * @param videoEntry
     */
    public static void prettyPrintVideoItem(YouTubeSearch videoEntry) {
        Log.d(TAG, "*************************************************************");
        Log.d(TAG, "\t\tItem:");
        Log.d(TAG, "*************************************************************");

        Log.d(TAG, " video name  = " + videoEntry.getTitle());
        Log.d(TAG, " video id    = " + videoEntry.getId());
        Log.d(TAG, " duration    = " + videoEntry.getDuration());
        Log.d(TAG, " thumbnail   = " + videoEntry.getThumbnailURL());
        Log.d(TAG, "\n*************************************************************\n");
    }
    
}
