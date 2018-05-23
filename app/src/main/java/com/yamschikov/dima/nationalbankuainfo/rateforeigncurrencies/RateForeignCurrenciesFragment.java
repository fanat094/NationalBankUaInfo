package com.yamschikov.dima.nationalbankuainfo.rateforeigncurrencies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.gturedi.views.StatefulLayout;
import com.socks.library.KLog;
import com.yamschikov.dima.nationalbankuainfo.BaseFragment;
import com.yamschikov.dima.nationalbankuainfo.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;

import android.widget.DatePicker;

public class RateForeignCurrenciesFragment extends BaseFragment implements CustomDatePickerFragment.OnDateSetListener {

    @BindView(R.id.rate_foreign_currency_rv)
    RecyclerView mRecyclerView;
    @BindString(R.string.toolbar_title_rate_fragment)
    String mToolbarTitle;
    @BindView(R.id.stateful_view)
    StatefulLayout mStateFulView;
    LiveData<List<RateForeignCurrencies>> data;
    LiveData<List<RateForeignCurrencies>> dataDate;
    RateForeignCurrenciesViewModel model;

    private RateForeignCurrenciesAdapter rateForeignCurrenciesAdapter;

    List<RateForeignCurrencies> rateForeignCurrenciesList = new ArrayList<>();

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
        setHasOptionsMenu(true);
    }

    private void subscribeToModel() {

        KLog.e("CoursesCurrenciesFragment", "");
        mStateFulView.showLoading();

        model = ViewModelProviders.of(this).get(RateForeignCurrenciesViewModel.class);
        data = model.getData();

        data.observe(this, new Observer<List<RateForeignCurrencies>>() {
            @Override
            public void onChanged(@Nullable List<RateForeignCurrencies> rateForeignCurrencies) {

                if (rateForeignCurrencies != null && rateForeignCurrencies.size() != 0) {

                    rateForeignCurrenciesList = rateForeignCurrencies;

                    rateForeignCurrenciesAdapter = new RateForeignCurrenciesAdapter(rateForeignCurrencies);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    DividerItemDecoration decor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
                    mRecyclerView.addItemDecoration(decor);
                    mRecyclerView.setAdapter(rateForeignCurrenciesAdapter);
                    mStateFulView.showContent();
                    rateForeignCurrenciesAdapter.notifyDataSetChanged();

                } else mStateFulView.showEmpty();
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_filter:
                KLog.e("onOptionsItemSelected", "");
                menuFilter();
                return true;

            case R.id.action_date:
                KLog.e("onOptionsItemSelected action_date:", "");
                getDate();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void menuFilter() {


        String[] filter_items = getResources().getStringArray(R.array.filter_items);

        new AlertDialog.Builder(getContext())
                .setSingleChoiceItems(filter_items, 0, null)
                .setPositiveButton(R.string.action_filter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();

                        switch (selectedPosition) {
                            case 0:
                                listSortAll(0);
                                break;
                            case 1:
                                listSortAll(1);
                                break;
                            case 2:
                                listSortAll(2);
                        }
                    }
                })
                .show();
    }

    private void listSortAll(int index) {

        switch (index) {

            case 0:
                Collections.sort(rateForeignCurrenciesList, new Comparator<RateForeignCurrencies>() {
                    @Override
                    public int compare(RateForeignCurrencies value1, RateForeignCurrencies value2) {
                        return value1.getCc().compareTo(value2.getCc());
                    }
                });
                break;
            case 1:
                Collections.sort(rateForeignCurrenciesList, new Comparator<RateForeignCurrencies>() {
                    @Override
                    public int compare(RateForeignCurrencies value1, RateForeignCurrencies value2) {
                        return value1.getRate().compareTo(value2.getRate());
                    }
                });
                break;
            case 2:
                Collections.sort(rateForeignCurrenciesList, new Comparator<RateForeignCurrencies>() {
                    @Override
                    public int compare(RateForeignCurrencies value1, RateForeignCurrencies value2) {
                        return value2.getRate().compareTo(value1.getRate());
                    }
                });
                break;
        }

        rateForeignCurrenciesAdapter.notifyDataSetChanged();
    }

    private void getDate() {

        CustomDatePickerFragment customDatePickerFragment = new CustomDatePickerFragment();
        customDatePickerFragment.show(getFragmentManager(), "DatePicker");
        customDatePickerFragment.setOnDateSetListener(this);
    }

    @Override
    public void onDateSet(DatePicker viewDatePicker, int year, int monthOfYear, int dayOfMonth) {

        int month = monthOfYear + 1;
        String selectedDateM = "" + month;
        String selectedDateD = "" + dayOfMonth;
        if (month < 10) {
            selectedDateM = "0" + month;
        }
        if (dayOfMonth < 10) {
            selectedDateD = "0" + dayOfMonth;
        }
        String dateParam = year + selectedDateM + selectedDateD;
        KLog.e("date------->", "" + dateParam);

        ///

        setOnDateSetObserver(dateParam);
    }

    private void setOnDateSetObserver(String dateParam) {

        model = ViewModelProviders.of(this).get(RateForeignCurrenciesViewModel.class);
        data = model.getDataDate(dateParam);

        data.observe(this, new Observer<List<RateForeignCurrencies>>() {
            @Override
            public void onChanged(@Nullable List<RateForeignCurrencies> rateForeignCurrenciesD) {

                if (rateForeignCurrenciesD != null && rateForeignCurrenciesD.size() != 0) {

                    rateForeignCurrenciesList = rateForeignCurrenciesD;

                    rateForeignCurrenciesAdapter = new RateForeignCurrenciesAdapter(rateForeignCurrenciesList);
                    mRecyclerView.setAdapter(rateForeignCurrenciesAdapter);
                    mStateFulView.showContent();
                    rateForeignCurrenciesAdapter.notifyDataSetChanged();
                } else mStateFulView.showEmpty();
            }
        });
    }
}