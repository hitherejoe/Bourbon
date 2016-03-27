package com.hitherejoe.bourbon.data.remote;

import com.hitherejoe.bourbon.common.data.model.Comment;
import com.hitherejoe.bourbon.common.data.model.Shot;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Single;

public interface BourbonService {

    @GET("shots")
    Single<List<Shot>> getShots(@Query("access_token") String accessToken,
                                @Query("per_page") int perPage,
                                @Query("page") int page);

    @GET("shots/{shot_id}/comments")
    Single<List<Comment>> getComments(@Path("shot_id") int shotId,
                                      @Query("access_token") String accessToken);

}
