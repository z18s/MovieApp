package com.example.movieapp.navigation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.movieapp.mvp.model.base.TagConstants;
import com.example.movieapp.mvp.model.search.data.SearchResult;
import com.example.movieapp.ui.fragment.PosterFragment;
import com.example.movieapp.ui.fragment.SearchFieldFragment;
import com.example.movieapp.ui.fragment.SearchResultFragment;
import com.example.movieapp.ui.fragment.TitleFragment;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {

    public static class SearchScreen extends SupportAppScreen {

        @Override
        public Fragment getFragment() {
            return new SearchFieldFragment();
        }
    }

    public static class SearchResultScreen extends SupportAppScreen {
        private final String query;

        public SearchResultScreen(String query) {
            this.query = query;
        }

        @Override
        public Fragment getFragment() {
            SearchResultFragment titlesFragment = new SearchResultFragment();
            Bundle args = new Bundle();
            args.putString(TagConstants.QUERY_TAG, query);
            titlesFragment.setArguments(args);
            return titlesFragment;
        }
    }

    public static class TitleScreen extends SupportAppScreen {
        private final SearchResult searchResult;

        public TitleScreen(SearchResult searchResult) {
            this.searchResult = searchResult;
        }

        @Override
        public Fragment getFragment() {
            TitleFragment titleFragment = new TitleFragment();
            Bundle args = new Bundle();
            args.putParcelable(TagConstants.TITLE_TAG, searchResult);
            titleFragment.setArguments(args);
            return titleFragment;
        }
    }

    public static class PosterScreen extends SupportAppScreen {
        private final String imageUrl;

        public PosterScreen(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        @Override
        public Fragment getFragment() {
            PosterFragment posterFragment = new PosterFragment();
            Bundle args = new Bundle();
            args.putString(TagConstants.POSTER_TAG, imageUrl);
            posterFragment.setArguments(args);
            return posterFragment;
        }
    }
}