package com.hitherejoe.bourboncorecommon.ui.browse;

import com.hitherejoe.bourboncorecommon.data.model.Shot;
import com.hitherejoe.bourboncorecommon.ui.base.MvpView;

import java.util.List;

public interface BrowseMvpView extends MvpView {

    void showProgress();

    void hideProgress();

    void showShots(List<Shot> shots);

    void showError();

    void showEmpty();

    void showMessageLayout(boolean show);

}
