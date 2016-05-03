package com.hitherejoe.bourboncommon.common.ui.browse;

import com.hitherejoe.bourboncommon.common.data.model.Shot;
import com.hitherejoe.bourboncommon.common.ui.base.MvpView;

import java.util.List;

public interface BrowseMvpView extends MvpView {

    void showProgress();

    void hideProgress();

    void showShots(List<Shot> shots);

    void showError();

    void showEmpty();

    void showMessageLayout(boolean show);

}
