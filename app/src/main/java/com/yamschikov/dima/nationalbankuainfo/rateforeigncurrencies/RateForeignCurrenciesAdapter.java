package com.yamschikov.dima.nationalbankuainfo.rateforeigncurrencies;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.socks.library.KLog;
import com.yamschikov.dima.nationalbankuainfo.BankSort;
import com.yamschikov.dima.nationalbankuainfo.R;
import com.yamschikov.dima.nationalbankuainfo.SharedPreferencesStorage;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RateForeignCurrenciesAdapter extends RecyclerView.Adapter<RateForeignCurrenciesAdapter.RateForeignCurrenciesHolder> {

    List<RateForeignCurrencies> rateForeignCurrenciesList;
    Context context;
    List<RateForeignCurrencies> rateForeignCurrenciesListOld;

    public RateForeignCurrenciesAdapter(Context context, List<RateForeignCurrencies> rateForeignCurrenciesList) {
        this.rateForeignCurrenciesList = rateForeignCurrenciesList;
        this.context = context;
    }

    public void setItems(List<RateForeignCurrencies> items) {
        this.rateForeignCurrenciesList = items;
    }

    @NonNull
    @Override
    public RateForeignCurrenciesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rate_foreign_currencies_item, parent, false);

        return new RateForeignCurrenciesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RateForeignCurrenciesHolder holder, int position) {

        SharedPreferencesStorage sharedPreferencesStorage = new SharedPreferencesStorage();
        rateForeignCurrenciesListOld = sharedPreferencesStorage.getCurrenciesRate(context);

        int isort = sharedPreferencesStorage.getIndexSort(context);

        switch (isort) {

            case 0:
                Collections.sort(rateForeignCurrenciesList, BankSort.getAttributeABCComparator());
                break;
            case 1:
                Collections.sort(rateForeignCurrenciesList, BankSort.getAttributeDownToUpRateComparator());
                break;
            case 2:
                Collections.sort(rateForeignCurrenciesList, BankSort.getAttributeUpToDownRateComparator());
                break;

        }

        RateForeignCurrencies rateForeignCurrencies = rateForeignCurrenciesList.get(position);
        RateForeignCurrencies rateForeignCurrenciesOld = rateForeignCurrenciesListOld.get(position);

        holder.mNameCcCurrency.setText(rateForeignCurrencies.getCc());
        holder.mNameCurrency.setText(rateForeignCurrencies.getTxt());

        String pattern = "##0.000";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        String formatRate = decimalFormat.format(rateForeignCurrencies.getRate());

        holder.mRateCurrency.setText(formatRate);
        holder.mDateCurrency.setText(rateForeignCurrencies.getExchangedate());

        Random rnd = new Random();
        int randomColor = Color.argb(255, rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));

        holder.mImageViewCurrency.setColorFilter(randomColor);

        //double res2 = (res*100)/rateForeignCurrenciesOld.getRate();

       /* holder.mdifferenceCurrency.setText(percentValue(rateForeignCurrencies, rateForeignCurrenciesOld));
        double resminus = rateForeignCurrencies.getRate() - rateForeignCurrenciesOld.getRate();
        KLog.e("resminus1----->", "" + resminus);
        if (resminus < 0) {

            holder.mdifferenceCurrency.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        } else
            holder.mdifferenceCurrency.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));

        KLog.e("rateForeignCurrencies.getRate()--->", "" + rateForeignCurrencies.getRate());
        KLog.e("rateForeignCurrencies/From Shared)--->", "" + rateForeignCurrenciesOld.getRate());*/
        //KLog.e("RESSSSSS", res);
    }

    /*public String percentValue(RateForeignCurrencies rateForeignCurrencies, RateForeignCurrencies rateForeignCurrenciesOld) {

        double res;
        String pattern;
        DecimalFormat decimalFormat;
        String format;

        double minus = rateForeignCurrencies.getRate() - rateForeignCurrenciesOld.getRate();
        KLog.e("resminus2----->", "" + minus);

        if (minus > 0) {

            res = ((rateForeignCurrenciesOld.getRate() - rateForeignCurrencies.getRate()) / rateForeignCurrencies.getRate()) * 100;
            pattern = "##0.00";
            decimalFormat = new DecimalFormat(pattern);
            format = decimalFormat.format(res);

        } else
            res = ((rateForeignCurrencies.getRate() - rateForeignCurrenciesOld.getRate()) / rateForeignCurrencies.getRate()) * 100;
        pattern = "##0.00";
        decimalFormat = new DecimalFormat(pattern);
        format = decimalFormat.format(res);

        KLog.e("format----->", format);


        return format + "%";
    }*/

    @Override
    public int getItemCount() {
        return rateForeignCurrenciesList.size();
    }

    public class RateForeignCurrenciesHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name_cc_currency)
        TextView mNameCcCurrency;
        @BindView(R.id.name_currency)
        TextView mNameCurrency;
        @BindView(R.id.rate_currency)
        TextView mRateCurrency;
        @BindView(R.id.date_currency)
        TextView mDateCurrency;
        @BindView(R.id.image_view_currency)
        ImageView mImageViewCurrency;
        /*@BindView(R.id.difference_currency)
        TextView mdifferenceCurrency;*/


        public RateForeignCurrenciesHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }

    }
}