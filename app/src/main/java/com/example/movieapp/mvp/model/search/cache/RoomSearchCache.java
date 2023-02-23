package com.example.movieapp.mvp.model.search.cache;

import com.example.movieapp.logger.ILogger;
import com.example.movieapp.mvp.model.search.data.SearchResult;
import com.example.movieapp.mvp.model.search.data.Search;
import com.example.movieapp.mvp.model.base.database.Database;
import com.example.movieapp.mvp.model.search.database.RoomSearchResult;
import com.example.movieapp.mvp.model.search.database.RoomSearch;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RoomSearchCache implements ISearchCache, ILogger {

    private final Database db;

    public RoomSearchCache(Database db) {
        this.db = db;
    }

    @Override
    public Single<Search> getSearch(String query) {
        showVerboseLog(this, "getSearch");
        return Single.fromCallable(() -> {

            RoomSearch roomSearch = db.searchDao().findByQuery(query);
            List<String> ids = roomSearch.getIdList();

            List<SearchResult> searchResults = new ArrayList<>();

            for (String id : ids) {
                SearchResult searchResult = getTitle(id);
                searchResults.add(searchResult);
            }

            return new Search(searchResults);
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable putSearch(String query, Search search) {
        showVerboseLog(this, "putSearch");
        return Completable.fromAction(() -> {

            List<String> ids = new ArrayList<>();

            List<SearchResult> searchResults = search.getList();

            for (SearchResult searchResult : searchResults) {
                String id = searchResult.getId();
                ids.add(id);

                putTitle(searchResult);
            }

            db.searchDao().insert(new RoomSearch(query, ids));
        }).subscribeOn(Schedulers.io());
    }

    private SearchResult getTitle(String id) {
        showVerboseLog(this, "getTitle");

        RoomSearchResult roomSearchResult = db.searchResultDao().findById(id);

        return new SearchResult(
                roomSearchResult.getId(),
                roomSearchResult.getName(),
                roomSearchResult.getType(),
                roomSearchResult.getYear(),
                roomSearchResult.getImageUrl()
        );
    }

    private void putTitle(SearchResult searchResult) {
        showVerboseLog(this, "putTitle");

        db.searchResultDao().insert(new RoomSearchResult(
                searchResult.getId(),
                searchResult.getNameString(),
                searchResult.getType(),
                searchResult.getYear(),
                searchResult.getImageUrl()
        ));
    }

    @Override
    public Completable clear() {
        showVerboseLog(this, "clear");
        return Completable.fromAction(() -> {
            db.searchResultDao().deleteAll();
        }).subscribeOn(Schedulers.io());
    }
}