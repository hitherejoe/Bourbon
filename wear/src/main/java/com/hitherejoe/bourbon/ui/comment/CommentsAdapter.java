package com.hitherejoe.bourbon.ui.comment;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hitherejoe.bourbon.R;
import com.hitherejoe.bourbon.common.data.model.Comment;

import java.util.Collections;
import java.util.List;

public class CommentsAdapter extends PagerAdapter {

    private Context mContext;
    private List<Comment> mComments;

    public CommentsAdapter(Context context) {
        mContext = context;
        mComments = Collections.emptyList();
    }

    public void setComments(List<Comment> comments) {
        mComments = comments;
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        Comment comment = mComments.get(i);

        ViewHolder viewHolder;
        View child = viewGroup.getChildAt(i);
        //   if (child == null) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_comment, viewGroup, false);
        viewHolder = new ViewHolder(view);
        viewHolder.bind(comment);
        view.setTag(viewHolder);
        viewGroup.addView(view);
        //  } else {
        //      viewHolder = (ViewHolder) child.getTag();
        //       viewHolder.bind(comment);
        //   }

        return viewHolder.frameLayout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }


    @Override
    public int getCount() {
        return mComments.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view.equals(o);
    }

    class ViewHolder {
        TextView textView;
        ImageView imageView;
        TextView userText;

        View frameLayout;

        public ViewHolder(View frameLayout) {
            this.frameLayout = frameLayout;
            textView = (TextView) frameLayout.findViewById(R.id.text_comment);
            userText = (TextView) frameLayout.findViewById(R.id.text_user);
            imageView = (ImageView) frameLayout.findViewById(R.id.image_avatar);
        }

        void bind(Comment comment) {
            textView.setText(Html.fromHtml(comment.body));
            userText.setText(comment.user.username);
            Glide.with(mContext).load(comment.user.avatarUrl).into(imageView);
        }
    }
}