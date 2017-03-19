package com.tutorialsbuzz.rxandroidsample.Examples;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tutorialsbuzz.rxandroidsample.Adapter.SimpleStringAdapter;
import com.tutorialsbuzz.rxandroidsample.MainActivity;
import com.tutorialsbuzz.rxandroidsample.Model.CombinedData;
import com.tutorialsbuzz.rxandroidsample.Model.SampleLocation;
import com.tutorialsbuzz.rxandroidsample.Network.RestClient;
import com.tutorialsbuzz.rxandroidsample.R;

import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by YA55 on 2/13/2017.
 */
public class FragmentExample11 extends Fragment {


    //private EditText user_et, password_et;
    private Button login_btn;

    private RestClient mRestClient;

    ProgressDialog progressDialog;

    private RecyclerView mRecyclerView;

    private SimpleStringAdapter mSimpleStringAdapter;

    private TextView textView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_example_11, container, false);
        
        mRestClient = new RestClient(getActivity());

        textView = (TextView) view.findViewById(R.id.locationTV);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recylerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSimpleStringAdapter = new SimpleStringAdapter(getActivity());
        mRecyclerView.setAdapter(mSimpleStringAdapter);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        login_btn = (Button) view.findViewById(R.id.login_btn);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).enableNavigationIcon();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();

                Observable<List<String>> observable1 = Observable
                        .fromCallable(new Callable<List<String>>() {
                            @Override
                            public List<String> call() {
                                return mRestClient.getCountryList();
                            }
                        }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());


                Observable<SampleLocation> observable2 = Observable
                        .fromCallable(new Callable<SampleLocation>() {
                            @Override
                            public SampleLocation call() {
                                return mRestClient.getUserLocation();
                            }
                        }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());


                Observable<CombinedData> combine_observable =
                        Observable.zip(observable1, observable2,
                                new Func2<List<String>, SampleLocation, CombinedData>() {
                                    @Override
                                    public CombinedData call(List<String> strings, SampleLocation sampleLocation) {
                                        CombinedData combinedData = new CombinedData();
                                        combinedData.setStringList(strings);
                                        combinedData.setSampleLocation(sampleLocation);

                                        return combinedData;
                                    }
                                }).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());


                combine_observable.subscribe(new Observer<CombinedData>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(CombinedData combinedData) {
                        displayData(combinedData);
                    }
                });

            }

        });


    }

    private void displayData(CombinedData combinedData) {
        progressDialog.dismiss();

        mSimpleStringAdapter.setStrings(combinedData.getStringList());
        mRecyclerView.setVisibility(View.VISIBLE);

        double latitude = combinedData.getSampleLocation().getLatitude();
        double longitude = combinedData.getSampleLocation().getLongitude();
        textView.setText("Latitude" + String.valueOf(latitude) + "\nLongitude" + String.valueOf(longitude));
        textView.setVisibility(View.VISIBLE);

    }


}
