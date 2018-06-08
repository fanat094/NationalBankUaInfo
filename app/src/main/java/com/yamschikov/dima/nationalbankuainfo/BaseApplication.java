package com.yamschikov.dima.nationalbankuainfo;

import android.app.Application;

import com.yamschikov.dima.nationalbankuainfo.di.AppComponent;
import com.yamschikov.dima.nationalbankuainfo.di.AppModule;
import com.yamschikov.dima.nationalbankuainfo.di.DaggerAppComponent;


public class BaseApplication extends Application{

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}