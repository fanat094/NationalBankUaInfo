package com.yamschikov.dima.nationalbankuainfo.rateforeigncurrencies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.socks.library.KLog;
import com.yamschikov.dima.nationalbankuainfo.BaseApplication;
import com.yamschikov.dima.nationalbankuainfo.api.WebService;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateForeignCurrenciesViewModel extends ViewModel {

    @Inject
    WebService webService;

    public RateForeignCurrenciesViewModel() {
        super();
        BaseApplication.getAppComponent().inject(this);
    }


    MutableLiveData<List<RateForeignCurrencies>> data;

    public LiveData<List<RateForeignCurrencies>> getData() {
        if (data == null) {
            data = new MutableLiveData<>();
            loadData();
            KLog.e("getData", data.getValue());
        }
        return data;
    }

    private void loadData() {

        webService.getRateForeignCurrencies().enqueue(new Callback<List<RateForeignCurrencies>>() {

            @Override
            public void onResponse(Call<List<RateForeignCurrencies>> call, Response<List<RateForeignCurrencies>> response) {

                KLog.e("data.setValue:", response.body());

                data.setValue(response.body());

            }

            @Override
            public void onFailure(Call<List<RateForeignCurrencies>> call, Throwable t) {

                data.setValue(null);

            }
        });

    }

    MutableLiveData<List<RateForeignCurrencies>> dataDate;

    public LiveData<List<RateForeignCurrencies>> getDataDate(String datadateparamtoload) {

            dataDate = new MutableLiveData<>();
            loadDateParam(datadateparamtoload);

        return dataDate;
    }

    private void loadDateParam(String datadateparam) {

        KLog.e("loadDateParam(String ssssss):", datadateparam);

        webService.getRateForeignCurrenciesParam(datadateparam).enqueue(new Callback<List<RateForeignCurrencies>>() {
            @Override
            public void onResponse(Call<List<RateForeignCurrencies>> call, Response<List<RateForeignCurrencies>> response) {

                if (response.body().isEmpty() != true) {

                    KLog.e("getRateForeignCurrenciesParam:", response.body());
                    dataDate.setValue(response.body());
                }else dataDate.setValue(null);
            }

            @Override
            public void onFailure(Call<List<RateForeignCurrencies>> call, Throwable t) {

                dataDate.setValue(null);
            }
        });

    }
}
