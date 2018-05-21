package com.yamschikov.dima.nationalbankuainfo;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.socks.library.KLog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    Unbinder unbinder;
    protected View mBaseView;

    @LayoutRes
    protected abstract int getMainContentLayout();
    protected abstract String getToolbarTitle();
    public abstract int getNavigationItemId();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mBaseView = inflater.inflate(getMainContentLayout(), container, false);

        ButterKnife.bind(this, mBaseView);
        unbinder = ButterKnife.bind(this, mBaseView);
        return mBaseView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mBaseView = view;

        KLog.e("BaseFragment", "");

    }

    public void onResume() {
        super.onResume();
        setToolbarTitle(getToolbarTitle());
        setCheckedItemId(getNavigationItemId());
    }

    public MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    private void setToolbarTitle(String title) {

        if (title != null && getMainActivity() != null) {
            getMainActivity().setToolbarTitle(title);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setCheckedItemId(@IdRes int id) {
        getMainActivity().setCheckedItem(id);
    }

    public void onScreen() {
        setToolbarTitle(getToolbarTitle());
    }

}
