package com.tutorialsbuzz.rxandroidsample.Network;

import android.content.Context;

import com.tutorialsbuzz.rxandroidsample.Model.SampleLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by YA55 on 2/2/2017.
 */
public class RestClient {
    private Context mContext;

    public RestClient(Context context) {
        mContext = context;
    }

    public List<String> getCountryList() {
        try {
            // "Simulate" the delay of network.
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //return createTvShowList();
        return createCountryList();
    }


    public SampleLocation getUserLocation() {

        SampleLocation location = new SampleLocation();
        try {
            // "Simulate" the delay of network.
            location.setLatitude(17.12);
            location.setLongitude(19.31);
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return location;
    }


//    public List<String> getFavoriteTvShowsWithException() {
//        try {
//            // "Simulate" the delay of network.
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        throw new RuntimeException("Failed to load");
//    }

//    private List<String> createTvShowList() {
//        List<String> tvShows = new ArrayList<>();
//        tvShows.add("The Joy of Painting");
//        tvShows.add("The Simpsons");
//        tvShows.add("Futurama");
//        tvShows.add("Rick & Morty");
//        tvShows.add("The X-Files");
//        tvShows.add("Star Trek: The Next Generation");
//        tvShows.add("Archer");
//        tvShows.add("30 Rock");
//        tvShows.add("Bob's Burgers");
//        tvShows.add("Breaking Bad");
//        tvShows.add("Parks and Recreation");
//        tvShows.add("House of Cards");
//        tvShows.add("Game of Thrones");
//        tvShows.add("Law And Order");
//        return tvShows;
//    }

    public List<String> searchForCity(String searchString) {
        try {
            // "Simulate" the delay of network.
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return getMatchingCities(searchString);
    }

    private List<String> getMatchingCities(String searchString) {
        if (searchString.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> toReturn = new ArrayList<>();
        for (String city : createCountryList()) {
            if (city.toLowerCase().startsWith(searchString.toLowerCase())) {
                toReturn.add(city);
            }
        }
        return toReturn;
    }


    private List<String> createCountryList() {

        ArrayList<String> countries = new ArrayList<>();
        String locales[] = Locale.getISOCountries();
        for (String locale : locales) {
            Locale obj = new Locale("", locale);
            countries.add(obj.getDisplayCountry());
        }
        return countries;
    }


}
