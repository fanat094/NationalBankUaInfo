package com.yamschikov.dima.nationalbankuainfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;
import com.yamschikov.dima.nationalbankuainfo.rateforeigncurrencies.RateForeignCurrencies;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SharedPreferencesStorage {

    final String SAVE_PREF_RATE = "SAVE_PREF_RATE";
    final String INDEX_SORT = "INDEX_SORT";
    final String INDEX_CONVERT = "INDEX_CONVERT";
    final String INDEX_CONVERT_SELECT = "INDEX_CONVERT_SELECT";

    SharedPreferences sPreferences;
    SharedPreferences.Editor prefsEditor;
    Gson gson;
    String json;

    public void saveIndexSort(Context context, int index_sort) {

        sPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        prefsEditor = sPreferences.edit();
        prefsEditor.putInt(INDEX_SORT, index_sort);
        prefsEditor.commit();
        KLog.e("SAVE_PREF_RATE", "done!" + " " + index_sort);

    }

    public int getIndexSort(Context context) {

        sPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int isort;
        isort = sPreferences.getInt(INDEX_SORT, 0);

        return isort;
    }

    public void saveCurrenciesRate(Context context, List<RateForeignCurrencies> prefRateForeignCurrenciesList) {

        sPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        prefsEditor = sPreferences.edit();
        gson = new Gson();
        json = gson.toJson(prefRateForeignCurrenciesList);
        prefsEditor.putString(SAVE_PREF_RATE, json);
        prefsEditor.commit();
        KLog.e("SAVE_PREF_RATE", "done!");

    }

    public List<RateForeignCurrencies> getCurrenciesRate(Context context) {

        sPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        gson = new Gson();
        json = sPreferences.getString(SAVE_PREF_RATE, "");
        Type type = new TypeToken<List<RateForeignCurrencies>>() {
        }.getType();
        List<RateForeignCurrencies> rateForeignCurrencies = gson.fromJson(json, type);

        return rateForeignCurrencies;
    }

    public void saveCurrenciesRateSort(Context context, List<RateForeignCurrencies> prefRateForeignCurrenciesList, int index) {

        switch (index) {

            case 0:
                Collections.sort(prefRateForeignCurrenciesList, new Comparator<RateForeignCurrencies>() {
                    @Override
                    public int compare(RateForeignCurrencies value1, RateForeignCurrencies value2) {
                        return value1.getCc().compareTo(value2.getCc());
                    }
                });
                break;
            case 1:
                Collections.sort(prefRateForeignCurrenciesList, new Comparator<RateForeignCurrencies>() {
                    @Override
                    public int compare(RateForeignCurrencies value1, RateForeignCurrencies value2) {
                        return value1.getRate().compareTo(value2.getRate());
                    }
                });
                break;
            case 2:
                Collections.sort(prefRateForeignCurrenciesList, new Comparator<RateForeignCurrencies>() {
                    @Override
                    public int compare(RateForeignCurrencies value1, RateForeignCurrencies value2) {
                        return value2.getRate().compareTo(value1.getRate());
                    }
                });
                break;
        }

        sPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        prefsEditor = sPreferences.edit();
        gson = new Gson();
        json = gson.toJson(prefRateForeignCurrenciesList);
        prefsEditor.putString(SAVE_PREF_RATE, json);
        prefsEditor.commit();
        KLog.e("SAVE_PREF_RATE_SORT", "done!");
    }

    public void saveIndexConvert(Context context, String index_convert){

        sPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        prefsEditor = sPreferences.edit();
        prefsEditor.putString(INDEX_CONVERT, index_convert);
        prefsEditor.commit();
    }

    public double getIndexConvert(Context context) {

        sPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String indexConvertRate = sPreferences.getString(INDEX_CONVERT, "");
        double indexConvertRateDouble;
        indexConvertRateDouble = Double.parseDouble(indexConvertRate);
        return indexConvertRateDouble;
    }

    //
    public void saveIndexConvertSelect(Context context, String index_convert_select){

        sPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        prefsEditor = sPreferences.edit();
        prefsEditor.putString(INDEX_CONVERT_SELECT, index_convert_select);
        prefsEditor.commit();
    }

    public String getIndexConvertSelect(Context context) {

        String indexConvertRate;
        sPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        indexConvertRate = sPreferences.getString(INDEX_CONVERT_SELECT, "");
        return indexConvertRate;
    }
}
