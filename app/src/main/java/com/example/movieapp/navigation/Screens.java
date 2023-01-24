package com.example.movieapp.navigation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.movieapp.mvp.model.base.TagConstants;
import com.example.movieapp.mvp.model.search.data.SearchResult;
import com.example.movieapp.ui.fragment.FavoritesFragment;
import com.example.movieapp.ui.fragment.PosterFragment;
import com.example.movieapp.ui.fragment.SearchFieldFragment;
import com.example.movieapp.ui.fragment.SearchResultFragment;
import com.example.movieapp.ui.fragment.TitleFragment;
import com.example.movieapp.ui.fragment.UserRatingsFragment;

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

    public static class FavoritesScreen extends SupportAppScreen {

        @Override
        public Fragment getFragment() {
            return new FavoritesFragment();
        }
    }

    public static class UserRatingsScreen extends SupportAppScreen {

        @Override
        public Fragment getFragment() {
            return new UserRatingsFragment();
        }
    }

    public static class TitleScreen extends SupportAppScreen {
        private final String titleId;

        public TitleScreen(String titleId) {
            this.titleId = titleId;
        }

        @Override
        public Fragment getFragment() {
            TitleFragment titleFragment = new TitleFragment();
            Bundle args = new Bundle();
            args.putString(TagConstants.TITLE_TAG, titleId);
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