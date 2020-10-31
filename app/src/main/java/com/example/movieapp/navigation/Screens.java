package com.example.movieapp.navigation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.movieapp.Logger;
import com.example.movieapp.mvp.model.Tags;
import com.example.movieapp.mvp.model.entity.Title;
import com.example.movieapp.ui.fragment.SearchFragment;
import com.example.movieapp.ui.fragment.TitleFragment;
import com.example.movieapp.ui.fragment.TitlesFragment;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {

    private static final String TAG = Screens.class.getSimpleName();

    public static class SearchScreen extends SupportAppScreen {

        @Override
        public Fragment getFragment() {
            Logger.showLog(Logger.VERBOSE, TAG, "new SearchFragment");
            return new SearchFragment();
        }
    }

    public static class TitlesScreen extends SupportAppScreen {

        private final String query;

        public TitlesScreen(String query) {
            this.query = query;
        }

        @Override
        public Fragment getFragment() {
            TitlesFragment titlesFragment = new TitlesFragment();
            Bundle args = new Bundle();
            args.putString(Tags.QUERY_TAG, query);
            titlesFragment.setArguments(args);
            Logger.showLog(Logger.VERBOSE, TAG, "new TitlesScreen + args");
            return titlesFragment;
        }
    }

    public static class TitleScreen extends SupportAppScreen {
        private final Title title;

        public TitleScreen(Title title) {
            this.title = title;
        }

        @Override
        public Fragment getFragment() {
            TitleFragment titleFragment = new TitleFragment();
            Bundle args = new Bundle();
            args.putParcelable(Tags.TITLE_TAG, title);
            titleFragment.setArguments(args);
            Logger.showLog(Logger.VERBOSE, TAG, "new TitleScreen + args");
            return titleFragment;
        }
    }
}