package com.hitherejoe.bourboncommon.common.data.remote;

import com.hitherejoe.bourboncommon.common.data.model.Comment;
import com.hitherejoe.bourboncommon.common.data.model.Shot;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Single;

public interface BourbonService {

    /**
     * Retrieve a list of shots
     */
    @GET("shots")
    Single<List<Shot>> getShots(@Query("access_token") String accessToken,
                                @Query("per_page") int perPage,
                                @Query("page") int page);

    /**
     * Retrieve a list of comments for a given shot
     */
    @GET("shots/{shot_id}/comments")
    Single<List<Comment>> getComments(@Path("shot_id") int shotId,
                                      @Query("access_token") String accessToken,
                                      @Query("per_page") int perPage,
                                      @Query("page") int page);

}
