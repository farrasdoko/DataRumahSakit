package com.gmail.farasabiyyu12.datarumahsakit.ApiRetrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by farasabiyyuhandoko on 06/04/2018.
 */

public class InstanceRetrofit {
    public static final String webURL = "http://192.168.20.10/crudrumahsakit/";

    public static Retrofit setInit(){
        return new Retrofit.Builder()
                .baseUrl(webURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getInstance(){
        return setInit().create(ApiService.class);
    }

}
