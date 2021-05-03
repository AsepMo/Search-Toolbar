package com.search.toolbar.youtube.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by smedic on 9.2.17..
 */

public abstract class BaseFragment extends Fragment {

    protected void share(String url) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, url);
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this song!");
        startActivity(Intent.createChooser(intent, "Share"));
    }

    protected abstract void updateList();

}

