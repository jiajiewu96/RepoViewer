package com.example.repoviewer.utils;

public class Consts {
    public static final String USER_NAME_KEY = "com.example.repoviewer.USER_NAME";
    public static final String USER_INFO_SHAREDPREF_KEY = "com.example.repoviewer.USER_INFO_SHAREDPREF_KEY";

    public static final String ACCESS_TOKEN_KEY = "ACCESS_TOKEN";
    public static final String ACCESS_TOKEN_SHAREDPREF_KEY = "com.example.repoviewer.ACCESS_TOKEN_SHAREDPREF_KEY";
    public static final String ACCESS_TOKEN_NULL = "ACCESS_TOKEN_NULL";

    public static final String USER_NAME_NULL = "USER_NAME_NULL";
    public static final String USER_NOT_AVAILABLE = "User Not Available";

    public static final String PARAM_SORT_KEY = "com.example.repoviewer.BUNDLE_PARAM_SORT_KEY";
    public static final String PARAM_ORDER_KEY = "com.example.repoviwer.BUNDLE_PARAM_ORDER_KEY";

    public static final String PARAM_SORT_CREATED = "created";
    public static final String PARAM_SORT_UPDATED = "updated";
    public static final String PARAM_SORT_PUSHED = "pushed";
    public static final String PARAM_SORT_FULL_NAME = "full_name";
    public static final String PARAM_SORT_DEFAULT = PARAM_SORT_FULL_NAME;

    public static final String PARAM_DIRECTION_ASC = "asc";
    public static final String PARAM_DIRECTION_DESC = "desc";

    public static String getDefaultDirectionParam(String sortParam){
        String directionParam = PARAM_DIRECTION_DESC;
        if(sortParam.equals(PARAM_SORT_DEFAULT)){
            directionParam = PARAM_DIRECTION_ASC;
            return directionParam;
        }
        return directionParam;
    }


}
