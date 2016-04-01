package com.hitherejoe.bourbon.ui.browse;

import android.content.Context;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;

public class BrowseAdapter extends ArrayObjectAdapter {

    private Context mContext;
    private CardPresenter mPresenter;

    public BrowseAdapter(Context context) {
        mContext = context;
        mPresenter = new CardPresenter(context);
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