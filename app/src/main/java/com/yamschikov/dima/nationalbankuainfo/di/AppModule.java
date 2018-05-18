package com.yamschikov.dima.nationalbankuainfo.di;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context context;

    public AppModule(Application application) {
        this.context = application;
    }

    @Singleton
    @Provides
    public Context provideContext(){
        return this.context;
    }


    @Singleton
    @Provides
    public Resources provideResources(Context context){
        return context.getResources();
    }
}
