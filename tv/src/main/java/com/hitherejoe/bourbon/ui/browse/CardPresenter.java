package com.hitherejoe.bourbon.ui.browse;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.hitherejoe.bourbon.R;
import com.hitherejoe.bourbon.common.data.model.Shot;

import javax.inject.Inject;

public class CardPresenter extends Presenter {

    private static final int CARD_WIDTH = 300;
    private static final int CARD_HEIGHT = 300;
    private static int sSelectedBackgroundColor;
    private static int sDefaultBackgroundColor;
    private Drawable mDefaultCardImage;

    @Inject
    public CardPresenter() { }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        final Context context = parent.getContext();
        sDefaultBackgroundColor = ContextCompat.getColor(context,
                com.hitherejoe.bourboncommon.R.color.colorPrimary);
        sSelectedBackgroundColor = ContextCompat.getColor(context,
                com.hitherejoe.bourboncommon.R.color.colorPrimaryDark);
        mDefaultCardImage = ContextCompat.getDrawable(context, android.R.drawable.alert_dark_frame);

        final ImageCardView cardView = new ImageCardView(parent.getContext()) {
            @Override
            public void setSelected(boolean selected) {
                updateCardBackgroundColor(this, selected);
                super.setSelected(selected);
            }
        };

        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        updateCardBackgroundColor(cardView, false);
        return new ViewHolder(cardView);
    }

    private static void updateCardBackgroundColor(ImageCardView view, boolean selected) {
        int color = selected ? sSelectedBackgroundColor : sDefaultBackgroundColor;
        // Both background colors should be set because the view's background is temporarily visible
        // during animations.
        view.setBackgroundColor(color);
        view.findViewById(R.id.info_field).setBackgroundColor(color);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        if (item instanceof Shot) {
            Shot shot = (Shot) item;

            final ImageCardView cardView = (ImageCardView) viewHolder.view;
            cardView.setTitleText(shot.title);
            if (shot.description != null) {
                cardView.setContentText(Html.fromHtml(shot.description));
            }
            cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);

            Glide.with(cardView.getContext())
                    .load(shot.images.getImage())
                    .centerCrop()
                    .error(mDefaultCardImage)
                    .into(cardView.getMainImageView());
        }
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        if (viewHolder.view instanceof ImageCardView) {
            ImageCardView cardView = (ImageCardView) viewHolder.view;
            // Remove references to images so that the garbage collector can free up memory
            cardView.setBadgeImage(null);
            cardView.setMainImage(null);
        }
    }
}