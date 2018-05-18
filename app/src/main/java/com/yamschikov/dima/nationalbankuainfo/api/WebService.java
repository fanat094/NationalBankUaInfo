package com.yamschikov.dima.nationalbankuainfo.api;


import com.yamschikov.dima.nationalbankuainfo.rateforeigncurrencies.RateForeignCurrencies;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WebService {

    @GET("NBUStatService/v1/statdirectory/exchange?json")
    Call<List<RateForeignCurrencies>> getRateForeignCurrencies();
}
