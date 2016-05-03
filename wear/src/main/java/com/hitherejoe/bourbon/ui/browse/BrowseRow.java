package com.hitherejoe.bourbon.ui.browse;


import com.hitherejoe.bourboncorecommon.data.model.Shot;

import java.util.ArrayList;

public class BrowseRow {

    ArrayList<Shot> mPagesRow = new ArrayList<>();

    public void addPages(Shot shot) {
        mPagesRow.add(shot);
    }

    public Shot getPages(int index) {
        return mPagesRow.get(index);
    }

    public int size(){
        return mPagesRow.size();
    }
}