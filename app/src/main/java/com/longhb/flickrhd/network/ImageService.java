package com.longhb.flickrhd.network;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ImageService {
    @GET("services/rest")
    Call<GetImage> seachImages(
            @Query("method") String method,
            @Query("api_key") String api_key,
            @Query("extras") String extras,
            @Query("per_page") String per_page,
            @Query("page") String page,
            @Query("text") String text,
            @Query("sort") String sort,//relevance kết quả giống trên web
            @Query("format") String format,
            @Query("nojsoncallback") String nojsoncallback
    );


    @GET("services/rest")
    Observable<GetComment> getAllComment(
            @Query("method") String method,
            @Query("api_key") String api_key,
            @Query("photo_id") String photo_id,
            @Query("format") String format,
            @Query("nojsoncallback") String nojsoncallback
    );


}
