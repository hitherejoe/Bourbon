package com.hitherejoe.bourbon.ui.shot.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hitherejoe.bourbon.R;
import com.hitherejoe.bourboncorecommon.data.model.Comment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CommentView extends FrameLayout {

    @Bind(R.id.image_avatar)
    ImageView mAvatarImage;

    @Bind(R.id.text_user)
    TextView mUserText;

    @Bind(R.id.text_comment)
    TextView mCommentText;

    private Comment mComment;

    public CommentView(Context context) {
        super(context);
        init();
    }

    public CommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CommentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setComment(Comment comment) {
        mComment = comment;
        setBackgroundColor(Color.parseColor("#FFDFDFDF"));
        Glide.with(getContext()).load(mComment.user.avatarUrl).into(mAvatarImage);
        mUserText.setText(mComment.user.username);
        mCommentText.setText(Html.fromHtml(mComment.body));
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_comment, this);
        ButterKnife.bind(this);
    }

}
