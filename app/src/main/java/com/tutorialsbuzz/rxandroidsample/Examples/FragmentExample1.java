package com.tutorialsbuzz.rxandroidsample.Examples;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tutorialsbuzz.rxandroidsample.MainActivity;
import com.tutorialsbuzz.rxandroidsample.R;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

public class FragmentExample1 extends Fragment {

    TextView textView;

    Subscription subscription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_example_1, container, false);

        textView = (TextView) view.findViewById(R.id.tv);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).enableNavigationIcon();

        Observable<String> observable = Observable.just("hello");
        
        subscription = observable.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                textView.setText(s);
            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
