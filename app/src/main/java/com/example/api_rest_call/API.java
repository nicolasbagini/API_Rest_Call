package com.example.api_rest_call;

import retrofit2.Retrofit;

public class API {
    private API() {
    };

    public static final String URL = "https://us-central1-be-tp3-a.cloudfunctions.net/";

    public static AutoService getAutoService() {
        return Retrofit2.getClient(URL).create(AutoService.class);
    }
}