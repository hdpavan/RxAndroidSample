package com.tutorialsbuzz.rxandroidsample.Model;

import java.util.List;

/**
 * Created by YA55 on 2/13/2017.
 */
public class CombinedData {

    private List<String> stringList;

    private SampleLocation sampleLocation;

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public void setSampleLocation(SampleLocation sampleLocation) {
        this.sampleLocation = sampleLocation;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public SampleLocation getSampleLocation() {
        return sampleLocation;
    }
}
