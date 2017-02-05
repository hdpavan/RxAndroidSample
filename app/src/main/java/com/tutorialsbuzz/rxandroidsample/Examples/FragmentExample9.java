package com.tutorialsbuzz.rxandroidsample.Examples;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tutorialsbuzz.rxandroidsample.MainActivity;
import com.tutorialsbuzz.rxandroidsample.R;

import rx.Observer;
import rx.Subscription;
import rx.subjects.PublishSubject;


/**
 * Created by YA55 on 2/2/2017.
 */
public class FragmentExample9 extends Fragment {

    private TextView textView;
    private Button button;
    private PublishSubject<Integer> mCounterEmitter;
    private Subscription mSubscription;

    private int mCounter = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_example_9, container, false);
        textView = (TextView) view.findViewById(R.id.tv9);
        button = (Button) view.findViewById(R.id.btn9);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCounter++;
                mCounterEmitter.onNext(mCounter);
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).enableNavigationIcon();

        mCounterEmitter = PublishSubject.create();

        mSubscription = mCounterEmitter.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                textView.setText(String.valueOf(integer));
            }
        });


    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }

    }
}
