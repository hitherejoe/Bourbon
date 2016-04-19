package com.hitherejoe.bourbon.ui.browse;

import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;

public class BrowseAdapter extends ArrayObjectAdapter {

    private CardPresenter mPresenter;

    public BrowseAdapter() {
        mPresenter = new CardPresenter();
        setPresenterSelector();
    }

    public void setPresenterSelector() {
        setPresenterSelector(new PresenterSelector() {
            @Override
            public Presenter getPresenter(Object item) {
                return mPresenter;
            }
        });
    }

}