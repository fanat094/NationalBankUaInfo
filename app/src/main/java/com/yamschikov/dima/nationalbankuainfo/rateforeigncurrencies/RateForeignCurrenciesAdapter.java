package com.yamschikov.dima.nationalbankuainfo.rateforeigncurrencies;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yamschikov.dima.nationalbankuainfo.R;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RateForeignCurrenciesAdapter extends RecyclerView.Adapter<RateForeignCurrenciesAdapter.RateForeignCurrenciesHolder>{

    List<RateForeignCurrencies> rateForeignCurrenciesList;

    public RateForeignCurrenciesAdapter(List<RateForeignCurrencies> rateForeignCurrenciesList) {
        this.rateForeignCurrenciesList = rateForeignCurrenciesList;
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

        RateForeignCurrencies rateForeignCurrencies = rateForeignCurrenciesList.get(position);

        holder.mNameCcCurrency.setText(rateForeignCurrencies.getCc());
        holder.mNameCurrency.setText(rateForeignCurrencies.getTxt());
        holder.mRateCurrency.setText(Double.toString(rateForeignCurrencies.getRate()));
        holder.mDateCurrency.setText(rateForeignCurrencies.getExchangedate());

        Random rnd = new Random();
        int randomColor = Color.argb(255, rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));

        holder.mImageViewCurrency.setColorFilter(randomColor);

    }

    @Override
    public int getItemCount() {
        return rateForeignCurrenciesList.size();
    }

    public class RateForeignCurrenciesHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name_cc_currency) TextView mNameCcCurrency;
        @BindView(R.id.name_currency) TextView mNameCurrency;
        @BindView(R.id.rate_currency) TextView mRateCurrency;
        @BindView(R.id.date_currency) TextView mDateCurrency;
        @BindView(R.id.image_view_currency) ImageView mImageViewCurrency;


        public RateForeignCurrenciesHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }
    }
}
