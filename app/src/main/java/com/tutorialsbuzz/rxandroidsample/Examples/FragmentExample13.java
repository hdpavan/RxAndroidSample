package com.tutorialsbuzz.rxandroidsample.Examples;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tutorialsbuzz.rxandroidsample.Adapter.SimpleStringAdapter;
import com.tutorialsbuzz.rxandroidsample.MainActivity;
import com.tutorialsbuzz.rxandroidsample.Network.RestClient;
import com.tutorialsbuzz.rxandroidsample.R;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by YA55 on 2/2/2017.
 */
public class FragmentExample13 extends Fragment implements SearchView.OnQueryTextListener {


    private RestClient mRestClient;
    private TextView mNoResultsIndicator;
    private RecyclerView mSearchResults;
    private SimpleStringAdapter mSearchResultsAdapter;
    private PublishSubject<String> mSearchResultsSubject;
    private Subscription mSearchSubscription;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_example_13, container, false);

        mNoResultsIndicator = (TextView) view.findViewById(R.id.no_results_indicator);
        mSearchResults = (RecyclerView) view.findViewById(R.id.search_results);
        mSearchResults.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSearchResultsAdapter = new SimpleStringAdapter(getActivity());
        mSearchResults.setAdapter(mSearchResultsAdapter);
        mRestClient = new RestClient(getActivity());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).enableNavigationIcon();
        createObservables();
    }

    private void createObservables() {

        mSearchResultsSubject = PublishSubject.create();
        mSearchSubscription = mSearchResultsSubject
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, List<String>>() {
                    @Override
                    public List<String> call(String data) {
                        return mRestClient.searchForCity(data);
                    }
                })

                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<String> cities) {
                        handleSearchResults(cities);
                    }
                });
    }

    private void handleSearchResults(List<String> cities) {
        if (cities.isEmpty()) {
            showNoSearchResults();
        } else {
            showSearchResults(cities);
        }
    }

    private void showNoSearchResults() {
        mNoResultsIndicator.setVisibility(View.VISIBLE);
        mSearchResults.setVisibility(View.GONE);
    }

    private void showSearchResults(List<String> cities) {
        mNoResultsIndicator.setVisibility(View.GONE);
        mSearchResults.setVisibility(View.VISIBLE);
        mSearchResultsAdapter.setStrings(cities);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mSearchResultsSubject.onNext(newText);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSearchSubscription != null && !mSearchSubscription.isUnsubscribed()) {
            mSearchSubscription.unsubscribe();
        }
    }
}
