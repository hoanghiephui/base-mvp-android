package com.ducnd.demorealmmvp.remote.interact.source;

import com.ducnd.demorealmmvp.remote.model.ItemSong;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ducnd on 8/10/17.
 */

public interface ResApi {
    @GET("/jOut.ashx")
    Observable<List<ItemSong>> getListSong(@Query("k") String name, @Query("h") String web, @Query("code") String code);
}
