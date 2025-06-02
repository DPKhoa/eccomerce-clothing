package com.app.projectstyleecommerce.util;

public class UrlUtil {

    public static final String API_PREFIX ="/api";
    public static final String API_VERSION ="/v1";
    public static final String BASE_URL = API_PREFIX +  API_VERSION ;
    public static String getUrl(String domainUrl, String apiPath, String resourcePath){
        return domainUrl + apiPath + resourcePath;
    }
    public static final String PRODUCT_URL = BASE_URL + "/products";
    public static final String FILE_URL = BASE_URL + "/files";
}
