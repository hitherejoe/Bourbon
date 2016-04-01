package com.hitherejoe.bourbon.common.ui.browse;

import com.hitherejoe.bourbon.common.data.DataManager;
import com.hitherejoe.bourbon.common.data.model.Shot;
import com.hitherejoe.bourbon.common.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BrowsePresenter extends BasePresenter<BrowseMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public BrowsePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void getShots(int perPage, int page) {
        checkViewAttached();
        getMvpView().showProgress();

        mSubscription = mDataManager.getShots(perPage, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleSubscriber<List<Shot>>() {
                    @Override
                    public void onSuccess(List<Shot> shots) {
                        getMvpView().hideProgress();
                        if (!shots.isEmpty()) {
                            getMvpView().showShots(shots);
                        } else {
                            getMvpView().showEmpty();
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        getMvpView().hideProgress();
                        getMvpView().showError();
                    }
                });
    }

}