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

public class FragmentExample2 extends Fragment {

    TextView textView;

    Subscription subscription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_example_2, container, false);

        textView = (TextView) view.findViewById(R.id.tv2);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).enableNavigationIcon();

        Observable<Integer[]> observable = Observable.just(new Integer[]{1, 2, 3, 4, 5, 6});


        subscription = observable.subscribe(new Observer<Integer[]>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer[] integers) {

                for (int i = 0; i < integers.length; i++) {
                    textView.append(String.valueOf(integers[i]));
                }

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
