package com.yamschikov.dima.nationalbankuainfo.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yamschikov.dima.nationalbankuainfo.api.ApiMethods;
import com.yamschikov.dima.nationalbankuainfo.api.WebService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Singleton
    @Provides
    public Builder provideHttpClient(){

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        Builder httpClient = new Builder();
        httpClient.addInterceptor(loggingInterceptor);
        return httpClient;
    }

    @Singleton
    @Provides
    public Retrofit retrofit(Builder httpClient){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder().baseUrl(ApiMethods.BASE_URL).addConverterFactory(GsonConverterFactory.create())
                //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
    }

    @Singleton
    @Provides
    public WebService provideRetrofitService(Retrofit retrofit){
        return (WebService) retrofit.create(WebService.class);
    }
}

