package com.example.alexr.ideamanager.services.builder;

import android.os.Build;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceBuilder {

    private static final String BASE_URL = "http://10.0.2.2:9000/";

    private static Interceptor customHttpHeadersInterceptor = new Interceptor() {
        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request request = chain.request();
            request = request.newBuilder()
                    .addHeader("Accept-Language", Locale.getDefault().getLanguage())
                    .addHeader("x-device-type", Build.DEVICE)
                    .build();

            return chain.proceed(request);
        }
    };
    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(customHttpHeadersInterceptor)
            .addInterceptor(loggingInterceptor);

    private static final Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientBuilder.build());

    private static Retrofit retrofit = retrofitBuilder.build();

    public static <S> S buildService(Class<S> serviceType) {
        return retrofit.create(serviceType);
    }
}
