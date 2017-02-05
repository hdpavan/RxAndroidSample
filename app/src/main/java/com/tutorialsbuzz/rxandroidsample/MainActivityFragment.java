package com.tutorialsbuzz.rxandroidsample;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tutorialsbuzz.rxandroidsample.Adapter.ExampleAdapter;
import com.tutorialsbuzz.rxandroidsample.Examples.FragmentExample1;
import com.tutorialsbuzz.rxandroidsample.Examples.FragmentExample10;
import com.tutorialsbuzz.rxandroidsample.Examples.FragmentExample2;
import com.tutorialsbuzz.rxandroidsample.Examples.FragmentExample3;
import com.tutorialsbuzz.rxandroidsample.Examples.FragmentExample4;
import com.tutorialsbuzz.rxandroidsample.Examples.FragmentExample5;
import com.tutorialsbuzz.rxandroidsample.Examples.FragmentExample6;
import com.tutorialsbuzz.rxandroidsample.Examples.FragmentExample7;
import com.tutorialsbuzz.rxandroidsample.Examples.FragmentExample8;
import com.tutorialsbuzz.rxandroidsample.Examples.FragmentExample9;
import com.tutorialsbuzz.rxandroidsample.Model.ExampleFragmentAndName;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private RecyclerView mRecyclerView = null;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.examplelist);
        setupExampleList();
        return view;
    }


    private void setupExampleList() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new ExampleAdapter(getActivity(), getExamples()));
    }


    private static List<ExampleFragmentAndName> getExamples() {

        List<ExampleFragmentAndName> exampleFragmentAndNameList = new ArrayList<>();

        exampleFragmentAndNameList.add(new ExampleFragmentAndName(new FragmentExample1(), "Just (Emit Single Value)"));
        exampleFragmentAndNameList.add(new ExampleFragmentAndName(new FragmentExample2(), "Just (Emit Complete List)"));
        exampleFragmentAndNameList.add(new ExampleFragmentAndName(new FragmentExample3(), "From (Emit each items from list) "));
        exampleFragmentAndNameList.add(new ExampleFragmentAndName(new FragmentExample4(), "fromCallable"));
        exampleFragmentAndNameList.add(new ExampleFragmentAndName(new FragmentExample5(), "create"));
        exampleFragmentAndNameList.add(new ExampleFragmentAndName(new FragmentExample6(), "Single"));
        exampleFragmentAndNameList.add(new ExampleFragmentAndName(new FragmentExample7(), "Map"));
        exampleFragmentAndNameList.add(new ExampleFragmentAndName(new FragmentExample8(), "Filter"));
        exampleFragmentAndNameList.add(new ExampleFragmentAndName(new FragmentExample9(), "PublishSubject"));
        exampleFragmentAndNameList.add(new ExampleFragmentAndName(new FragmentExample10(), "Filter List"));
//        exampleFragmentAndNameList.add(new ExampleFragmentAndName(new FragmentExample10(), "Filter List"));


        return exampleFragmentAndNameList;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).disableNavigationIcon();
    }
}
