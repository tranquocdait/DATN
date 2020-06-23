package com.tranquocdai.freshmarket.config;

import java.time.LocalDateTime;
import java.time.Month;

public class Constants {
    public static final String URL_AVATAR_DEFAULT = "https://res.cloudinary.com/dnccnn3nx/image/upload/v1585798094/avatar-default.png";
    public static final String URL_POST_DEFAULT = "https://res.cloudinary.com/dnccnn3nx/image/upload/v1585139659/post-default.png";
    public static final Long ID_IMAGE_DEFAULT = 1L;
    public static final Long ID_ROLE_ADMIN = 1L;
    public static final Long ID_USER_DEFAULT = 2L;
    public static final Long ID_STATUS_DEFAULT = 1L;
    public static final Long ID_STATUS_DELETE = 2L;
    public static final LocalDateTime LOCAL_DATE_TIME_START = LocalDateTime.of(2020, Month.APRIL,26,0,0);
    private Constants() {
    }
}
