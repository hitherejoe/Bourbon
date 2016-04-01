package com.hitherejoe.bourbon.common.ui.shot;

import com.hitherejoe.bourbon.common.data.DataManager;
import com.hitherejoe.bourbon.common.data.model.Comment;
import com.hitherejoe.bourbon.common.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShotPresenter extends BasePresenter<ShotMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public ShotPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void getComments(int id) {
        checkViewAttached();
        getMvpView().showProgress();

        mSubscription = mDataManager.getComments(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleSubscriber<List<Comment>>() {
                    @Override
                    public void onSuccess(List<Comment> comments) {
                        getMvpView().hideProgress();
                        if (comments.isEmpty()) {
                            getMvpView().showEmptyComments();
                        } else {
                            getMvpView().showComments(comments);
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