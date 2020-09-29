package com.longhb.flickrhd.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageClient {
    private static Retrofit INSTANCE;

    private ImageClient() {

    }

    public static Retrofit getInstance(String baseUrl) {
        if (INSTANCE == null) {
            INSTANCE = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return INSTANCE;
    }
}
