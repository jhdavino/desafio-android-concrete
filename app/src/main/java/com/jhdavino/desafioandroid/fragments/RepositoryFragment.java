package com.jhdavino.desafioandroid.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jhdavino.desafioandroid.R;
import com.jhdavino.desafioandroid.models.Repository;
import com.jhdavino.desafioandroid.network.GitService;
import com.jhdavino.desafioandroid.network.GitServiceFactory;
import com.jhdavino.desafioandroid.views.adapters.RepositoryAdapter;
import com.novoda.merlin.MerlinsBeard;
import com.paginate.Paginate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by josehenrique on 30/10/17.
 */

public class RepositoryFragment extends Fragment {

    RecyclerView rvRepository;
    private List<Repository> listRepositories;
    private View view;
    private RepositoryAdapter repositoryAdapter;
    private GitService gitService;
    private int currentPage = 1;
    private boolean isLoading = false;
    private boolean miss = false;
    private boolean lock = false;

    public RepositoryFragment() {
        // Required empty public constructor
    }

    public static RepositoryFragment newInstance() {
        return new RepositoryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_repository, container, false);

        } catch (InflateException e) {
            Log.e("Exception: %s", e.getMessage());
        }

        rvRepository = (RecyclerView) view.findViewById(R.id.rv_repositories);
        rvRepository.setHasFixedSize(true);

        //used as a divider between items
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                rvRepository.getContext(),
                new LinearLayoutManager(getActivity()).getOrientation());

        rvRepository.addItemDecoration(mDividerItemDecoration);

        //Set RecyclerView's LayoutManager to the one given.
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, RecyclerView.VERTICAL);
        rvRepository.setLayoutManager(layoutManager);

        //checks the existence of data
        if (savedInstanceState != null) {

            restoreData(savedInstanceState);
        } else {

            repositoryAdapter = new RepositoryAdapter(new ArrayList<>());
            rvRepository.setAdapter(repositoryAdapter);
        }


        //checks the status of the network
        if (MerlinsBeard.from(getContext()).isConnected()) {
            loadData();
        } else {
            makeToast(getResources().getString(R.string.network_unavailable_message));
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list_repositories", new ArrayList<>(repositoryAdapter.getListRepository()));
        outState.putBoolean("isloading", isLoading);
        outState.putBoolean("erro_message", miss);
        outState.putInt("current_page", currentPage);
    }

    private void loadData() {

        gitService = new GitServiceFactory().create();

        Paginate.Callbacks callbacks = new Paginate.Callbacks() {
            @Override
            public void onLoadMore() {
                if (!lock) {
                    lock = true;
                    gitService.getRemotesRepository(currentPage)
                            .subscribeOn(Schedulers.io())
                            .toObservable()
                            .map(r -> r.getItems())
                            .flatMap(r -> io.reactivex.Observable.fromIterable(r))
                            .map(r -> Repository.create(r))
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(result -> {
                                repositoryAdapter.add(result);
                                isLoading = true;
                            }, throwable -> {
                                makeToast(getResources().getString(R.string.error_message_call));
                                miss = true;
                            }, () -> {
                                isLoading = false;
                                setCurrentPage();
                            });
                }
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return miss;
            }
        };

        Paginate.with(rvRepository, callbacks)
                .addLoadingListItem(true)
                .build();
    }

    private void restoreData(Bundle savedInstanceState) {
        currentPage = savedInstanceState.getInt("current_page");
        isLoading = savedInstanceState.getBoolean("isloading");
        miss = savedInstanceState.getBoolean("erro_message");

        repositoryAdapter = new RepositoryAdapter(savedInstanceState.getParcelableArrayList("list_repositories"));
        rvRepository.setAdapter(repositoryAdapter);
        repositoryAdapter.notifyDataSetChanged();
    }

    private void setCurrentPage() {
        this.currentPage += 1;
        this.lock = false;
    }

    private void makeToast(String text) {
        if (getActivity() != null && isAdded())
            Toast.makeText(getActivity(),
                    text,
                    Toast.LENGTH_LONG).show();
    }
}