package com.example.moviemania;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MoviesModel {

    @SerializedName("page")
    String page;
    @SerializedName("total_results")
    String total_results;
    @SerializedName("total_pages")
    String total_pages;
    @SerializedName("results")
    Results[] results;

    public MoviesModel() {
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTotal_results() {
        return total_results;
    }

    public void setTotal_results(String total_results) {
        this.total_results = total_results;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }

    public Results[] getResults() {
        return results;
    }

    public void setResults(Results[] results) {
        this.results = results;
    }
}
