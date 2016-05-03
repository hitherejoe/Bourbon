package com.hitherejoe.bourboncorecommon.ui.shot;

import com.hitherejoe.bourboncorecommon.data.model.Comment;
import com.hitherejoe.bourboncorecommon.ui.base.MvpView;

import java.util.List;

public interface ShotMvpView extends MvpView {

    void showProgress();

    void hideProgress();

    void showComments(List<Comment> comments);

    void showError();

    void showEmptyComments();

    void showCommentsTitle(boolean hasComments);
}
