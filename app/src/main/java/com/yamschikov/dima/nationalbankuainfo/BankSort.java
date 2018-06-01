package com.yamschikov.dima.nationalbankuainfo;

import com.yamschikov.dima.nationalbankuainfo.rateforeigncurrencies.RateForeignCurrencies;

import java.util.Collections;
import java.util.Comparator;

public class BankSort {

    public static Comparator<RateForeignCurrencies> getAttributeABCComparator() {
        return new Comparator<RateForeignCurrencies>() {
            @Override
            public int compare(RateForeignCurrencies value1, RateForeignCurrencies value2) {
                return value1.getCc().compareTo(value2.getCc());
            }
        };
    }

    public static Comparator<RateForeignCurrencies> getAttributeDownToUpRateComparator() {
        return new Comparator<RateForeignCurrencies>() {
            @Override
            public int compare(RateForeignCurrencies value1, RateForeignCurrencies value2) {
                return value1.getRate().compareTo(value2.getRate());
            }
        };
    }

    public static Comparator<RateForeignCurrencies> getAttributeUpToDownRateComparator() {
        return new Comparator<RateForeignCurrencies>() {
            @Override
            public int compare(RateForeignCurrencies value1, RateForeignCurrencies value2) {
                return value2.getRate().compareTo(value1.getRate());
            }
        };
    }
}