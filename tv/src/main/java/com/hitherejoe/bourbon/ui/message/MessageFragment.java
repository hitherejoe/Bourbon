package com.hitherejoe.bourbon.ui.message;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hitherejoe.bourbon.R;
import com.hitherejoe.bourbon.ui.browse.BrowseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageFragment extends Fragment {

    @Bind(R.id.button_message) Button mMessageButton;
    @Bind(R.id.image_message) ImageView mMessageImage;
    @Bind(R.id.text_title) TextView mTitleText;
    @Bind(R.id.text_message) TextView mMessageText;

    public static final String ARGUMENT_TYPE = "ARGUMENT_TYPE";
    public static final int TYPE_EMPTY = 0;
    public static final int TYPE_ERROR = 1;

    private int mType;

    public static MessageFragment newInstance(int type) {
        MessageFragment browseFragment = new MessageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARGUMENT_TYPE, type);
        browseFragment.setArguments(bundle);
        return browseFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        mType = bundle.getInt(ARGUMENT_TYPE, -1);

        if (mType < 0) {
            throw new IllegalArgumentException("MessageFragment requires a type");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this, root);
        showMessage();
        return root;
    }

    @OnClick(R.id.button_message)
    public void onMessageButtonClick() {
        Activity activity = getActivity();
        if (activity instanceof BrowseActivity) {
            ((BrowseActivity) activity).showBrowseFragment();
        }
    }

    public void showMessage() {
        mTitleText.setText(mType == TYPE_EMPTY ?
                getString(R.string.title_no_shots) : getString(R.string.title_oops));
        mMessageText.setText(mType == TYPE_EMPTY ?
                getString(R.string.text_no_shots) : getString(R.string.text_error_loading_shots));
        mMessageButton.setText(mType == TYPE_EMPTY ?
                getString(R.string.text_check_again) : getString(R.string.text_reload));
        mMessageImage.setImageResource(mType == TYPE_EMPTY ? R.drawable.ic_empty_glass_120dp :
                R.drawable.ic_sentiment_very_dissatisfied_black_120dp);
    }
}
