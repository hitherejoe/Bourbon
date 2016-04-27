package com.hitherejoe.bourboncommon.ui.shot;

import com.hitherejoe.bourboncommon.data.model.Comment;
import com.hitherejoe.bourboncommon.ui.base.MvpView;

import java.util.List;

public interface ShotMvpView extends MvpView {

    void showProgress();

    void hideProgress();

    void showComments(List<Comment> comments);

    void showError();

    void showEmptyComments();

    void showCommentsTitle(boolean hasComments);
}
