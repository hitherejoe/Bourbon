package com.hitherejoe.bourboncommon.data;

import com.hitherejoe.bourboncommon.BuildConfig;
import com.hitherejoe.bourboncommon.data.model.Comment;
import com.hitherejoe.bourboncommon.data.model.Shot;
import com.hitherejoe.bourboncommon.data.remote.BourbonService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Single;

@Singleton
public class DataManager {

    private final BourbonService mBourbonService;

    @Inject
    public DataManager(BourbonService bourbonService) {
        mBourbonService = bourbonService;
    }

    public Single<List<Shot>> getShots(int perPage, int page) {
        return mBourbonService.getShots(BuildConfig.DRIBBBLE_ACCESS_TOKEN,
                perPage,
                page);
    }

    public Single<List<Comment>> getComments(int id, int perPage, int page) {
        return mBourbonService.getComments(id, BuildConfig.DRIBBBLE_ACCESS_TOKEN,
                perPage,
                page);
    }
}