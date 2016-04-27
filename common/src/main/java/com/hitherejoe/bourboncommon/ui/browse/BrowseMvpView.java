package com.hitherejoe.bourboncommon.ui.browse;

import com.hitherejoe.bourboncommon.data.model.Shot;
import com.hitherejoe.bourboncommon.ui.base.MvpView;

import java.util.List;

public interface BrowseMvpView extends MvpView {

    void showProgress();

    void hideProgress();

    void showShots(List<Shot> shots);

    void showError();

    void showEmpty();

    void showMessageLayout(boolean show);

}
