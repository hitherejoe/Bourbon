package com.hitherejoe.bourbon.common.ui.browse;

import com.hitherejoe.bourbon.common.data.model.Shot;
import com.hitherejoe.bourbon.common.ui.base.MvpView;

import java.util.List;

public interface BrowseMvpView extends MvpView {

    void showProgress();

    void hideProgress();

    void showShots(List<Shot> shots);

    void setComplete();

    void showError();

    void showEmpty();

    void hideErrorView();

}
