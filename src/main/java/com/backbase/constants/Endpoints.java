package com.backbase.constants;

public class Endpoints {

    public static final String BASE_URI="https://qa-task.backbasecloud.com";
    public static final String LOGIN_USER="/api/users/login";
    public static final String GET_USER="/api/user";
    public static final String CREATE_GET_ARTICLE="/api/articles";
    public static final String GET_ARTICLE="/api/articles";
    public static final String ADD_COMMENT="/api/articles/{slug}/comments";
    public static final String GET_UPDATE_ARTICLE_BY_SLUG="api/articles/{slug}";
    public static final String DELETE_COMMENT="/api/articles/{slug}/comments/{id}";
    public static final String FAVORITE_UNFAVORITE="/api/articles/{slug}/favorite";
    public static final String DELETE_ARTICLE="/api/articles/{slug}";


}
