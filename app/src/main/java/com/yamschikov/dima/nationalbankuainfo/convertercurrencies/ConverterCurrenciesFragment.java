package com.yamschikov.dima.nationalbankuainfo.convertercurrencies;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yamschikov.dima.nationalbankuainfo.BaseFragment;
import com.yamschikov.dima.nationalbankuainfo.R;
import com.yamschikov.dima.nationalbankuainfo.SharedPreferencesStorage;
import com.yamschikov.dima.nationalbankuainfo.rateforeigncurrencies.RateForeignCurrencies;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConverterCurrenciesFragment extends BaseFragment {

    @BindString(R.string.toolbar_title_converter_currencies_fragment)
    String mToolbarTitle;
    @BindView(R.id.editIn)
    EditText mEditIn;
    @BindView(R.id.editOut)
    EditText mEditOut;
    @BindView(R.id.tvToCurrency)
    TextView mTvToCurrency;
    @BindView(R.id.tvEndCurrency)
    TextView mTvEndCurrency;

    List<RateForeignCurrencies> rateForeignCurrenciesListSafe;

    public ConverterCurrenciesFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getMainContentLayout() {
        return R.layout.fragment_converter_currencies;
    }

    @Override
    protected String getToolbarTitle() {
        return mToolbarTitle;
    }

    @Override
    public int getNavigationItemId() {
        return R.id.nav_converter_currencies;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditIn.addTextChangedListener(watch);

        mTvToCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuFilter();
            }
        });
    }

    TextWatcher watch = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int a, int b, int c) {
            // TODO Auto-generated method stub

            int number = Integer.parseInt(s.toString());
            /*number *= 26;

            mEditOut.setText(String.valueOf(number));*/
            ddddd(number);
            if (a == 9) {
                Toast.makeText(getActivity(), "Maximum Limit Reached", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void menuFilter() {

        SharedPreferencesStorage sharedPreferencesStorage = new SharedPreferencesStorage();
        rateForeignCurrenciesListSafe = sharedPreferencesStorage.getCurrenciesRate(getActivity());
        getListCurrencies(rateForeignCurrenciesListSafe);

        //String[] filter_items = getResources().getStringArray(R.array.filter_items);

        new AlertDialog.Builder(getContext())
                .setSingleChoiceItems(getListCurrencies(rateForeignCurrenciesListSafe), 0, null)
                .setPositiveButton(R.string.action_filter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        KLog.e("selectedPosition----->", selectedPosition);
                        mTvToCurrency.setText(rateForeignCurrenciesListSafe.get(selectedPosition).getCc());

                        String f = String.valueOf(mEditIn.getText());
                        int foo = Integer.parseInt(f);

                        foo*=rateForeignCurrenciesListSafe.get(selectedPosition).getRate();
                        String res = String.valueOf(foo);


                        mEditOut.setText(res);
                        /*switch (selectedPosition) {
                            case 0:
                                //listSortAll(0);
                                break;
                        }*/
                    }
                })
                .show();
    }

    private String[] getListCurrencies(List<RateForeignCurrencies> currenciesList) {

        ArrayList<String> currencies = new ArrayList<>();
        String[] currenciesStringList = new String[0];

        for (int i = 0; i < currenciesList.size(); i++) {

            currencies.add(rateForeignCurrenciesListSafe.get(i).getTxt());
            KLog.e("ITEMS", currencies.get(i));

            currenciesStringList = currencies.toArray(new String[currencies.size()]);
        }
        return currenciesStringList;
    }

    private void ddddd(int gggggg) {

        gggggg *= 26;

        KLog.e("dddddddddddddddddddddd", mTvToCurrency.getText());
        mEditOut.setText(String.valueOf(gggggg));
    }
}