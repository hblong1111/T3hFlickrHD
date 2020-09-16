package com.longhb.flickrhd.network;

import retrofit2.Call;
import retrofit2.http.Query;

public interface ImageService {

    Call<GetImagesFavourite> getAllImageFavourite(
            @Query("method") String method,
            @Query("api_key") String api_key,
            @Query("user_id") String user_id,
            @Query("extras") String extras,
            @Query("per_page") String per_page,
            @Query("page") String page,
            @Query("format") String format,
            @Query("nojsoncallback") String nojsoncallback
    );
}
