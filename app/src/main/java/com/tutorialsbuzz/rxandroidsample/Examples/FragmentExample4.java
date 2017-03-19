package com.tutorialsbuzz.rxandroidsample.Examples;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.tutorialsbuzz.rxandroidsample.Adapter.SimpleStringAdapter;
import com.tutorialsbuzz.rxandroidsample.MainActivity;
import com.tutorialsbuzz.rxandroidsample.Network.RestClient;
import com.tutorialsbuzz.rxandroidsample.R;

import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YA55 on 2/2/2017.
 */
public class FragmentExample4 extends Fragment {

    private Subscription mSubscription;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private SimpleStringAdapter mSimpleStringAdapter;
    private RestClient mRestClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_example_4, container, false);

        mProgressBar = (ProgressBar) view.findViewById(R.id.loader);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recylerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSimpleStringAdapter = new SimpleStringAdapter(getActivity());
        mRecyclerView.setAdapter(mSimpleStringAdapter);

        mRestClient = new RestClient(getActivity());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).enableNavigationIcon();

        Observable<List<String>> observable = Observable.fromCallable(new Callable<List<String>>() {
            @Override
            public List<String> call() {
                return mRestClient.getCountryList();
            }
        });

        mSubscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<String> strings) {
                        displayData(strings);
                    }
                });

    }

    private void displayData(List<String> tvShows) {
        mSimpleStringAdapter.setStrings(tvShows);
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
