package com.yamschikov.dima.nationalbankuainfo.rateforeigncurrencies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.socks.library.KLog;
import com.yamschikov.dima.nationalbankuainfo.BaseFragment;
import com.yamschikov.dima.nationalbankuainfo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RateForeignCurrenciesFragment extends BaseFragment {

    @BindView(R.id.rate_foreign_currency_rv) RecyclerView mRecyclerView;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    private RateForeignCurrenciesAdapter rateForeignCurrenciesAdapter;


    public RateForeignCurrenciesFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getMainContentLayout() {
        return R.layout.fragment_rate_foreign_currencies;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        KLog.e("CoursesCurrenciesFragment", "");

        mProgressBar.setVisibility(View.VISIBLE);

        RateForeignCurrenciesViewModel model = ViewModelProviders.of(this).get(RateForeignCurrenciesViewModel.class);
        LiveData<List<RateForeignCurrencies>> data = model.getData();

        data.observe(this, new Observer<List<RateForeignCurrencies>>() {
            @Override
            public void onChanged(@Nullable List<RateForeignCurrencies> rateForeignCurrencies) {

                rateForeignCurrenciesAdapter = new RateForeignCurrenciesAdapter(rateForeignCurrencies);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(rateForeignCurrenciesAdapter);

                mProgressBar.setVisibility(View.GONE);

            }
        });


    }
}
