package com.yamschikov.dima.nationalbankuainfo.rateforeigncurrencies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.gturedi.views.StatefulLayout;
import com.socks.library.KLog;
import com.yamschikov.dima.nationalbankuainfo.BaseFragment;
import com.yamschikov.dima.nationalbankuainfo.R;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;

public class RateForeignCurrenciesFragment extends BaseFragment {

    @BindView(R.id.rate_foreign_currency_rv) RecyclerView mRecyclerView;
    @BindString(R.string.toolbar_title_rate_fragment) String mToolbarTitle;
    @BindView(R.id.stateful_view) StatefulLayout mStateFulView;

    private RateForeignCurrenciesAdapter rateForeignCurrenciesAdapter;


    public RateForeignCurrenciesFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getMainContentLayout() {
        return R.layout.fragment_rate_foreign_currencies;
    }

    @Override
    protected String getToolbarTitle() {
        return mToolbarTitle;
    }

    @Override
    public int getNavigationItemId() {
        return R.id.nav_rate_foreigencurrencies;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        subscribeToModel();
    }

    private void subscribeToModel() {

        KLog.e("CoursesCurrenciesFragment", "");
        mStateFulView.showLoading();

        RateForeignCurrenciesViewModel model = ViewModelProviders.of(this).get(RateForeignCurrenciesViewModel.class);
        LiveData<List<RateForeignCurrencies>> data = model.getData();

        data.observe(this, new Observer<List<RateForeignCurrencies>>() {
            @Override
            public void onChanged(@Nullable List<RateForeignCurrencies> rateForeignCurrencies) {

                if(rateForeignCurrencies != null && rateForeignCurrencies.size() != 0) {

                    rateForeignCurrenciesAdapter = new RateForeignCurrenciesAdapter(rateForeignCurrencies);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    DividerItemDecoration decor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
                    mRecyclerView.addItemDecoration(decor);
                    mRecyclerView.setAdapter(rateForeignCurrenciesAdapter);
                    mStateFulView.showContent();

                }

                else mStateFulView.showEmpty();
            }
        });

    }
}
