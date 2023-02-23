package com.example.movieapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.movieapp.R;
import com.example.movieapp.logger.ILogger;
import com.example.movieapp.mvp.presenter.title.UserRatingsPresenter;
import com.example.movieapp.mvp.view.title.IUserRatingsView;
import com.example.movieapp.ui.BackButtonListener;
import com.example.movieapp.ui.adapter.UserRatingsAdapter;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class UserRatingsFragment extends MvpAppCompatFragment implements IUserRatingsView, SwipeRefreshLayout.OnRefreshListener, ILogger, BackButtonListener {

    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView userRatingsRecyclerView;
    private UserRatingsAdapter userRatingsAdapter;
    private Button clearRatingsDataButton;

    @InjectPresenter
    UserRatingsPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ratings, container, false);
        userRatingsRecyclerView = view.findViewById(R.id.rv_user_ratings);
        showVerboseLog(this, "onCreateView");
        return view;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        presenter.updateData();
    }

    @Override
    public void init() {
        initViews();
        initListeners();
        showVerboseLog(this, "init");
    }

    private void initViews() {
        swipeRefreshLayout = view.findViewById(R.id.srl_user_ratings);
        clearRatingsDataButton = view.findViewById(R.id.btn_clear_ratings);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        userRatingsAdapter = new UserRatingsAdapter(presenter.getUserRatingsListPresenter());
        userRatingsRecyclerView.setLayoutManager(layoutManager);
        userRatingsRecyclerView.setAdapter(userRatingsAdapter);
    }

    private void initListeners() {
        swipeRefreshLayout.setOnRefreshListener(this);
        clearRatingsDataButton.setOnClickListener((view) -> {
            presenter.onClearRatingsDataButtonClick();
        });
    }

    @Override
    public void updateUserRatingsData() {
        userRatingsAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean backPressed() {
        return presenter.backPressed();
    }
}