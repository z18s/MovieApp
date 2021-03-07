package com.example.movieapp.mvp.model.search.data;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.reactivex.rxjava3.core.Observable;

import static com.example.movieapp.mvp.model.base.SearchConstants.EMPTY_STRING;
import static com.example.movieapp.mvp.model.base.SearchConstants.NO_RESULT;

public class SearchResult {

    @SerializedName("imdbID")
    @Expose
    String id;

    @SerializedName("Title")
    @Expose
    String name;

    @SerializedName("Type")
    @Expose
    String type;

    @SerializedName("Year")
    @Expose
    String year;

    @SerializedName("Poster")
    @Expose
    String imageUrl;

    public SearchResult(String id, String name, String type, String year, String imageUrl) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.year = year;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public Observable<String> getName() {
        return Observable.just(name);
    }

    public String getNameString() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getYear() {
        return year;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public static class EmptySearchResult extends SearchResult {

        public EmptySearchResult() {
            super(EMPTY_STRING, NO_RESULT, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            EmptySearchResult that = (EmptySearchResult) obj;
            return (that.getId().equals(EMPTY_STRING) && that.getNameString().equals(NO_RESULT));
        }
    }
}