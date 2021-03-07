package com.example.movieapp.mvp.presenter.title;

import com.example.movieapp.application.MovieApp;
import com.example.movieapp.logger.ILogger;
import com.example.movieapp.mvp.model.title.data.Title;
import com.example.movieapp.mvp.model.title.repo.ITitleRepo;
import com.example.movieapp.mvp.presenter.title.list.IFavoritesListPresenter;
import com.example.movieapp.mvp.view.title.IFavoritesView;
import com.example.movieapp.mvp.view.title.list.IFavoritesItemView;
import com.example.movieapp.navigation.Screens;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Scheduler;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class FavoritesPresenter extends MvpPresenter<IFavoritesView> implements ILogger {

    @Inject
    Scheduler scheduler;
    @Inject
    Router router;
    @Inject
    ITitleRepo titleRepo;

    public FavoritesPresenter() {

        MovieApp.instance.getTitleSubcomponent().inject(this);
    }

    private final FavoritesListPresenter favoritesListPresenter = new FavoritesListPresenter();

    private class FavoritesListPresenter implements IFavoritesListPresenter {
        private final List<Title> favorites = new ArrayList<>();

        @Override
        public void onItemClick(IFavoritesItemView view) {
            int index = view.getPos();
            showVerboseLog(this, "onItemClick - " + index);
            Title favorite = favorites.get(index);
            if (!favorite.equals(new Title.EmptyTitle())) {
                router.navigateTo(new Screens.TitleScreen(favorite.getId()));
            }
        }

        @Override
        public void bindView(IFavoritesItemView view) {
            int index = view.getPos();
            Title favorite = favorites.get(index);
            setRecyclerData(view, favorite);
        }

        @Override
        public int getCount() {
            return favorites.size();
        }
    }

    public IFavoritesListPresenter getFavoritesListPresenter() {
        return favoritesListPresenter;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
        setData();
    }

    private void setRecyclerData(IFavoritesItemView view, Title favorite) {
        showVerboseLog(this, "setRecyclerData");
        view.setName(favorite.getName());
        view.setYear(favorite.getYear());
        view.setCountry(favorite.getCountry());
    }

    private void setData() {
        showVerboseLog(this, "setData");
        favoritesListPresenter.favorites.clear();
        titleRepo.getFavorites().observeOn(scheduler).subscribe(
                (favorites) -> {
                    if (favorites != null && favorites.size() != 0) {
                        favoritesListPresenter.favorites.addAll(favorites);
                    } else {
                        favoritesListPresenter.favorites.add(new Title.EmptyTitle());
                    }
                    getViewState().updateFavoritesData();
                },
                (e) -> {
                    showVerboseLog(this, "setData.onError - " + e.getMessage());
                }
        );
        getViewState().updateFavoritesData();
    }

    public void updateData() {
        setData();
    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}