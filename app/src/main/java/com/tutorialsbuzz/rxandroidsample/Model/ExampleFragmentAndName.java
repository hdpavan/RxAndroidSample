package com.tutorialsbuzz.rxandroidsample.Model;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * Created by YA55 on 2/2/2017.
 */
public class ExampleFragmentAndName<T extends Fragment> {


    public T mFragment;
    public String mExampleName;

    public ExampleFragmentAndName(T fragment, String name) {
        this.mFragment = fragment;
        this.mExampleName = name;
    }


}
