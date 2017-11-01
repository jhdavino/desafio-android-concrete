package com.jhdavino.desafioandroid.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jhdavino.desafioandroid.R;
import com.jhdavino.desafioandroid.models.PullItem;
import com.jhdavino.desafioandroid.models.Repository;
import com.jhdavino.desafioandroid.network.GitService;
import com.jhdavino.desafioandroid.network.GitServiceFactory;
import com.jhdavino.desafioandroid.views.adapters.PullRequestAdapter;
import com.novoda.merlin.MerlinsBeard;
import com.paginate.Paginate;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by josehenrique on 31/10/17.
 */

public class PullRequestsActivity extends AppCompatActivity {
    private RecyclerView rvPullRequests;
    private PullRequestAdapter pullRequestAdapter;
    private GitService gitService;
    private int pageNumber = 1;
    private boolean isLoading = false;
    private boolean error = false;
    private boolean lock = false;
    private String repo_user;
    private String repo_name;
    private TextView opened;
    private TextView closed;
    private TextView pipe;
    private int open = 0;
    private int close = 0;
    private Repository currentRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_requests);

        //get data putExtra
        Bundle data = getIntent().getExtras();
        currentRepository = (Repository) data.getParcelable("repository");

        // set title with repository name
        if (currentRepository != null) {
            this.setTitle(currentRepository.getName());
        }

        opened = (TextView) findViewById(R.id.text_pull_requests_open);
        closed = (TextView) findViewById(R.id.text_pull_requests_closed);
        pipe = (TextView) findViewById(R.id.text_pipe);
        rvPullRequests = (RecyclerView) findViewById(R.id.pull_recycler);
        rvPullRequests.setHasFixedSize(true);

        //used as a divider between items
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                rvPullRequests.getContext(),
                new LinearLayoutManager(this).getOrientation());

        rvPullRequests.addItemDecoration(mDividerItemDecoration);

        //Set RecyclerView's LayoutManager to the one given.
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, RecyclerView.VERTICAL);
        rvPullRequests.setLayoutManager(layoutManager);

        //checks the existence of data
        if (savedInstanceState != null) {

            restoreData(savedInstanceState);

        } else {

            pullRequestAdapter = new PullRequestAdapter(new ArrayList<>());
            rvPullRequests.setAdapter(pullRequestAdapter);
        }

        //checks the status of the network
        if (MerlinsBeard.from(this).isConnected()) {
            loadData();
        } else {
            makeToast(getResources().getString(R.string.network_unavailable_message));
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("list_pull_requests", new ArrayList<PullItem>(pullRequestAdapter.getPulls()));
        outState.putBoolean("isloading", isLoading);
        outState.putBoolean("error", error);
        outState.putInt("page", pageNumber);
        outState.putInt("open", open);
        outState.putInt("close", close);
    }

    private void loadData() {

        gitService = new GitServiceFactory().create();

        Paginate.Callbacks callbacks = new Paginate.Callbacks() {

            @Override
            public void onLoadMore() {
                if (!lock) {
                    lock = true;
                    gitService.getPullRepository(currentRepository.getUsername(), currentRepository.getName())
                            .subscribeOn(Schedulers.io())
                            .toObservable()
                            .flatMap(r -> io.reactivex.Observable.fromIterable(r))
                            .map(r -> PullItem.create(r))
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(result -> {
                                pullRequestAdapter.add(result);
                                countStatusPullRequest(result.getState());
                                isLoading = true;
                            }, throwable -> {
                                makeToast(getResources().getString(R.string.error_message_call));
                                error = true;
                            }, () -> {
                                isLoading = false;
                                countNumberPages();
                            });
                }
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return error;
            }
        };

        Paginate.with(rvPullRequests, callbacks)
                .addLoadingListItem(true)
                .build();
    }

    private void restoreData(Bundle savedInstanceState) {

        pageNumber = savedInstanceState.getInt("page");
        isLoading = savedInstanceState.getBoolean("isloading");
        error = savedInstanceState.getBoolean("error");
        open = savedInstanceState.getInt("open");
        close = savedInstanceState.getInt("close");

        pullRequestAdapter = new PullRequestAdapter(savedInstanceState.getParcelableArrayList("list_pull_requests"));
        rvPullRequests.setAdapter(pullRequestAdapter);
        pullRequestAdapter.notifyDataSetChanged();

        writeValuesPullRequests(open, close);
    }

    private void writeValuesPullRequests(int open, int close) {

        opened.setText(String.format(getString(R.string.label_pull_request_open), String.valueOf(open)));
        opened.setVisibility(View.VISIBLE);
        pipe.setVisibility(View.VISIBLE);
        closed.setText(String.format(getString(R.string.label_pull_request_closed), String.valueOf(close)) + " (in context)");
        closed.setVisibility(View.VISIBLE);
    }

    private void countStatusPullRequest(String state) {
        if (state.equals("open")) {
            open++;
        } else if (state.equals("closed")) {
            close++;
        }

        writeValuesPullRequests(open, close);

    }

    private void countNumberPages() {
        this.pageNumber += 1;
        this.lock = false;
    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}