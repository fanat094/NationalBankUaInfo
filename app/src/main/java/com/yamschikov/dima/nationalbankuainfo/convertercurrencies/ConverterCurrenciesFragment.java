package com.yamschikov.dima.nationalbankuainfo.convertercurrencies;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    SharedPreferencesStorage sharedPreferencesStorage;

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
        //mTvToCurrency.setEnabled(false);

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

            if (mEditIn.getText().toString().equals("")) {

                KLog.e("karaul", "karaul");
                mEditOut.setText("");
                mTvToCurrency.setEnabled(false);
            } else {
                mTvToCurrency.setEnabled(true);
                int number = Integer.parseInt(s.toString());
                setNumber(number);
            }
            if (a == 9) {
                Toast.makeText(getActivity(), "Maximum Limit Reached", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void menuFilter() {

        sharedPreferencesStorage = new SharedPreferencesStorage();
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

                        double valueFromEditIn = Double.parseDouble(String.valueOf(mEditIn.getText()));

                        valueFromEditIn *= rateForeignCurrenciesListSafe.get(selectedPosition).getRate();
                        String resValueFromEditIn = String.valueOf(valueFromEditIn);

                        SharedPreferencesStorage sharedPreferences = new SharedPreferencesStorage();
                        String saveValueRate = String.valueOf(rateForeignCurrenciesListSafe.get(selectedPosition).getRate());
                        sharedPreferences.saveIndexConvert(getActivity(), saveValueRate);

                        String saveValueRateSelect = String.valueOf(rateForeignCurrenciesListSafe.get(selectedPosition).getCc());
                        sharedPreferences.saveIndexConvertSelect(getActivity(), saveValueRateSelect);

                        KLog.e("REST--->", saveValueRateSelect);

                        mEditOut.setText(resValueFromEditIn);
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

    private void setNumber(double intnumber) {

        sharedPreferencesStorage = new SharedPreferencesStorage();
        double indexConvert = sharedPreferencesStorage.getIndexConvert(getActivity());
        String indexConvertSelect = sharedPreferencesStorage.getIndexConvertSelect(getActivity());
        mTvToCurrency.setText(indexConvertSelect);
        KLog.e("getIndexConvert", indexConvertSelect);
        intnumber *= indexConvert;

        KLog.e("dddddddddddddddddddddd", intnumber);
        mEditOut.setText(String.valueOf(intnumber));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //String saveValueRate = String.valueOf(rateForeignCurrenciesListSafe.get(33).getRate());
        //sharedPreferencesStorage.saveIndexConvert(getActivity(), saveValueRate);
    }
}