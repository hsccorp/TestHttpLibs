package com.aggarwalankur.testhttplibs;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ankur on 11-Jul-16.
 */

public class MovieResults {
    @SerializedName("results")
    private List<MovieDataItem> results;

    public List<MovieDataItem> getResults() {
        return results;
    }

    public void setResults(List<MovieDataItem> results) {
        this.results = results;
    }
}
