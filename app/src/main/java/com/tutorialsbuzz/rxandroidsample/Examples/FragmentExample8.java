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
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by YA55 on 2/2/2017.
 */
public class FragmentExample8 extends Fragment {

    TextView textView;
    Subscription mSubscription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_example_8, container, false);
        textView = (TextView) view.findViewById(R.id.tv8);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).enableNavigationIcon();

        Observable<Integer> observable = Observable.from(getIntergerList())
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        if (integer % 2 == 0) {
                            return true;
                        }
                        return false;
                    }
                });


        mSubscription = observable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                textView.append(String.valueOf(integer) + "\n");
            }
        });

    }


    private Integer[] getIntergerList() {

        return new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
