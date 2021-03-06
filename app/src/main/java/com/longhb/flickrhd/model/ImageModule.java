package com.longhb.flickrhd.model;

import com.longhb.flickrhd.network.ImageService;
import com.longhb.flickrhd.util.Const;

public class ImageModule {
    private static ImageService INSTANCE;

    private ImageModule() {
    }

    public static ImageService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = ImageClient.getInstance(Const.BASE_URL).create(ImageService.class);
        }
        return INSTANCE;
    }
}
