package com.yamschikov.dima.nationalbankuainfo;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.socks.library.KLog;

import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    Unbinder unbinder;
    protected View view;

    @LayoutRes
    protected abstract int getMainContentLayout();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // ButterKnife.bind(this, view);
       // unbinder = ButterKnife.bind(this, view);
        return inflater.inflate(getMainContentLayout(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        KLog.e("BaseFragment", "");


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

       // unbinder.unbind();
    }

    public void onResume() {
        super.onResume();
    }
}
