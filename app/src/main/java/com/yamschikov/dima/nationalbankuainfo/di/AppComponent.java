package com.yamschikov.dima.nationalbankuainfo.di;

import android.content.SharedPreferences;

import com.yamschikov.dima.nationalbankuainfo.rateforeigncurrencies.RateForeignCurrenciesViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})

public interface AppComponent {

  void inject(RateForeignCurrenciesViewModel rateForeignCurrenciesViewModel);
}