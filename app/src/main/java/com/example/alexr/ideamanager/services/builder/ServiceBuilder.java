package com.example.alexr.ideamanager.services.builder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceBuilder {

    private static final String BASE_URL = "http://10.0.2.2:9000/";

    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor);

    private static final Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientBuilder.build());

    private static Retrofit retrofit = retrofitBuilder.build();

    public static <S> S buildService(Class<S> serviceType) {
        return retrofit.create(serviceType);
    }
}
