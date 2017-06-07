package com.vrgsoft.timelinesample;

import com.vrgsoft.yearview.YearModel;


public class SomeModel implements YearModel {
    private int start_date;
    private int end_date;
    private String image;
    private String title;

    public SomeModel(int start_date, int end_date, String image, String title) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.image = image;
        this.title = title;
    }

    public void setStart_date(int start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(int end_date) {
        this.end_date = end_date;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public int start_date() {
        return start_date;
    }

    @Override
    public int end_date() {
        return end_date;
    }

    @Override
    public String image() {
        return image;
    }

    @Override
    public String description() {
        return title;
    }
}
