package com.example.movieapp.mvp.presenter.title;

import com.example.movieapp.application.MovieApp;
import com.example.movieapp.logger.ILogger;
import com.example.movieapp.mvp.model.title.cache.ITitleCache;
import com.example.movieapp.mvp.model.title.data.Title;
import com.example.movieapp.mvp.model.title.repo.ITitleRepo;
import com.example.movieapp.mvp.presenter.title.list.IUserRatingsListPresenter;
import com.example.movieapp.mvp.view.title.IUserRatingsView;
import com.example.movieapp.mvp.view.title.list.IUserRatingsItemView;
import com.example.movieapp.navigation.Screens;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Scheduler;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class UserRatingsPresenter extends MvpPresenter<IUserRatingsView> implements ILogger {

    @Inject
    Scheduler scheduler;
    @Inject
    Router router;
    @Inject
    ITitleRepo titleRepo;
    @Inject
    ITitleCache titleCache;

    public UserRatingsPresenter() {
        MovieApp.instance.getTitleSubcomponent().inject(this);
    }

    private final UserRatingsListPresenter userRatingsListPresenter = new UserRatingsListPresenter();

    private class UserRatingsListPresenter implements IUserRatingsListPresenter {
        private final List<Title> userRatings = new ArrayList<>();

        @Override
        public void onItemClick(IUserRatingsItemView view) {
            int index = view.getPos();
            showVerboseLog(this, "onItemClick - " + index);
            Title userRating = userRatings.get(index);
            if (!userRating.equals(new Title.EmptyTitle())) {
                router.navigateTo(new Screens.TitleScreen(userRating.getId()));
            }
        }

        @Override
        public void bindView(IUserRatingsItemView view) {
            int index = view.getPos();
            Title userRating = userRatings.get(index);
            setRecyclerData(view, userRating);
        }

        @Override
        public int getCount() {
            return userRatings.size();
        }
    }

    public IUserRatingsListPresenter getUserRatingsListPresenter() {
        return userRatingsListPresenter;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
        setData();
    }

    private void setRecyclerData(IUserRatingsItemView view, Title userRating) {
        showVerboseLog(this, "setRecyclerData");
        view.setPoster(userRating.getImageUrl());
        view.setName(userRating.getName());
        view.setYear(userRating.getYear());
        view.setCountry(userRating.getCountry());
        view.setUserRating(userRating.getUserRating());
    }

    private void setData() {
        showVerboseLog(this, "setData");
        userRatingsListPresenter.userRatings.clear();
        titleRepo.getUserRatings().observeOn(scheduler).subscribe(
                (userRatings) -> {
                    if (userRatings != null && userRatings.size() != 0) {
                        userRatingsListPresenter.userRatings.addAll(userRatings);
                    } else {
                        userRatingsListPresenter.userRatings.add(new Title.EmptyTitle());
                    }
                    getViewState().updateUserRatingsData();
                },
                (e) -> {
                    showVerboseLog(this, "setData.onError - " + e.getMessage());
                }
        );
        getViewState().updateUserRatingsData();
    }

    public void onClearRatingsDataButtonClick() {
        titleCache.clearUserRatings().observeOn(scheduler).subscribe(this::updateData);
    }

    public void updateData() {
        setData();
    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}