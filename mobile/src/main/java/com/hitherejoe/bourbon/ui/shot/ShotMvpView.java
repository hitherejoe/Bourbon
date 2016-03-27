package com.hitherejoe.bourbon.ui.shot;

import com.hitherejoe.bourbon.common.data.model.Comment;
import com.hitherejoe.bourbon.ui.base.MvpView;

import java.util.List;

public interface ShotMvpView extends MvpView {

    void showProgress();

    void hideProgress();

    void showComments(List<Comment> comments);

    void showError();

    void showEmptyComments();

}
