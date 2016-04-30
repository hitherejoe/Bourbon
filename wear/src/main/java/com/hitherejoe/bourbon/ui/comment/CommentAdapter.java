package com.hitherejoe.bourbon.ui.comment;


import android.content.Context;
import android.support.wearable.view.GridPagerAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hitherejoe.bourbon.R;
import com.hitherejoe.bourbon.common.data.model.Comment;

import java.util.Collections;
import java.util.List;

public class CommentAdapter extends GridPagerAdapter {

    private Context mContext;
    private List<Comment> mComments;

    public CommentAdapter(Context context) {
        mContext = context;
        mComments = Collections.emptyList();
    }

    public void setComments(List<Comment> comments) {
        mComments = comments;
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount(int i) {
        return mComments.size();
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int i, int i1) {
        Comment comment = mComments.get(i);

        ViewHolder viewHolder;
        View child = viewGroup.getChildAt(i1);
        //   if (child == null) {
        LinearLayout view = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.item_comment, viewGroup, false);
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
    public void destroyItem(ViewGroup viewGroup, int i, int i1, Object o) {
        viewGroup.removeView((View) o);
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view.equals(o);
    }

    class ViewHolder {
        TextView textView;
        ImageView imageView;
        TextView userText;

        LinearLayout frameLayout;

        public ViewHolder(LinearLayout frameLayout) {
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