package com.hitherejoe.bourboncommon.common.ui.shot;

import com.hitherejoe.bourboncommon.common.data.model.Comment;
import com.hitherejoe.bourboncommon.common.ui.base.MvpView;

import java.util.List;

public interface ShotMvpView extends MvpView {

    void showProgress();

    void hideProgress();

    void showComments(List<Comment> comments);

    void showError();

    void showEmptyComments();

    void showCommentsTitle(boolean hasComments);
}
